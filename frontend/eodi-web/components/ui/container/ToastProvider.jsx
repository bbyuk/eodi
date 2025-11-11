"use client";

import { createContext, useContext, useState, useRef } from "react";
import { motion, AnimatePresence } from "framer-motion";

const ToastContext = createContext({ showToast: (text, event) => {} });

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

  const showToast = (text, event) => {
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
            className="px-2 py-1 text-xs bg-gray-800 text-white rounded shadow-lg whitespace-nowrap pointer-events-none"
          >
            {toast.text}
          </motion.div>
        )}
      </AnimatePresence>
    </ToastContext.Provider>
  );
}
