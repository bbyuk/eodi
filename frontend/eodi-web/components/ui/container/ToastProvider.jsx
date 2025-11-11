"use client";

import { createContext, useContext, useState, useRef, useEffect } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { AlertTriangle, CheckCircle2, XCircle, Info } from "lucide-react";

const ToastContext = createContext({ showToast: (event, text, type) => {} });

export function useToast() {
  return useContext(ToastContext);
}

export default function ToastProvider({ children }) {
  const [toast, setToast] = useState({
    id: 0,
    text: "",
    x: 0,
    y: 0,
    visible: false,
  });
  const timeoutRef = useRef(null);

  const showToast = (event, text, type) => {
    // 기존 타이머 제거
    if (timeoutRef.current) {
      clearTimeout(timeoutRef.current);
      timeoutRef.current = null;
    }

    // 위치 계산 (event 없으면 화면 중앙 근처)
    let x = window.innerWidth / 2;
    let y = window.innerHeight / 2;

    if (event?.currentTarget) {
      const rect = event.currentTarget.getBoundingClientRect();
      x = rect.left + rect.width / 2;
      y = rect.top;
    }

    // 토스트 상태 갱신 + id 증가
    setToast((prev) => ({
      id: prev.id + 1, // key로 쓸 값
      text,
      type,
      x,
      y,
      visible: true,
    }));

    // 새 타이머 등록
    timeoutRef.current = setTimeout(() => {
      setToast((t) => ({ ...t, visible: false }));
      timeoutRef.current = null;
    }, 1500);
  };

  // 유저 이벤트 발생 시 토스트 닫기
  useEffect(() => {
    if (!toast.visible) return;

    const hideOnUserEvent = () => {
      setToast((t) => ({ ...t, visible: false }));
      if (timeoutRef.current) {
        clearTimeout(timeoutRef.current);
        timeoutRef.current = null;
      }
    };

    const events = ["scroll", "click", "keydown", "touchstart", "wheel"];

    // 등록을 한 프레임 지연시켜 현재 클릭은 무시
    const timer = setTimeout(() => {
      events.forEach((evt) => window.addEventListener(evt, hideOnUserEvent, { once: true }));
    }, 0);

    // cleanup
    return () => {
      clearTimeout(timer);
      events.forEach((evt) => window.removeEventListener(evt, hideOnUserEvent));
    };
  }, [toast.visible]);

  const variantStyles = {
    info: {
      bg: "bg-blue-50/80 dark:bg-blue-900/60",
      text: "text-blue-700 dark:text-blue-100",
      border: "border-blue-300/50",
      icon: <Info size={14} className="text-blue-500" />,
    },
    success: {
      bg: "bg-green-50/80 dark:bg-green-900/60",
      text: "text-green-700 dark:text-green-100",
      border: "border-green-300/50",
      icon: <CheckCircle2 size={14} className="text-green-500" />,
    },
    warning: {
      bg: "bg-amber-50/80 dark:bg-amber-900/60",
      text: "text-amber-800 dark:text-amber-100",
      border: "border-amber-300/50",
      icon: <AlertTriangle size={14} className="text-amber-500" />,
    },
    error: {
      bg: "bg-red-100/80 dark:bg-red-900/60",
      text: "text-red-700 dark:text-red-100",
      border: "border-red-300/50",
      icon: <XCircle size={14} className="text-red-500" />,
    },
  };

  const current = variantStyles[toast.type] || variantStyles.info;

  return (
    <ToastContext.Provider value={{ showToast }}>
      {children}

      <AnimatePresence mode="wait">
        {toast.visible && (
          <motion.div
            key={toast.id} // 클릭마다 바뀌어서 매번 enter 애니메이션
            initial={{ opacity: 0, y: 5 }}
            animate={{ opacity: 1, y: 0 }}
            exit={{ opacity: 0, y: 5 }}
            transition={{ duration: 0.18 }}
            style={{
              position: "fixed",
              top: toast.y - 10,
              left: toast.x,
              transform: "translate(-50%, -100%)",
              zIndex: 99999,
            }}
            className={`
              px-3 py-1.5 rounded-md text-sm font-medium
              backdrop-blur-md shadow-lg border
              ${current.bg} ${current.text} ${current.border}
            `}
          >
            <div className="flex items-center gap-1">
              {current.icon}
              <span>{toast.text}</span>
            </div>
          </motion.div>
        )}
      </AnimatePresence>
    </ToastContext.Provider>
  );
}
