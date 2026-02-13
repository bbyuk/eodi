"use client";

import { createContext, useContext, useState, useRef, useEffect } from "react";
import { createPortal } from "react-dom";
import { motion, AnimatePresence } from "framer-motion";
import { AlertTriangle, CheckCircle2, XCircle, Info } from "lucide-react";

const ToastContext = createContext({ showToast: () => {} });

export function useToast() {
  return useContext(ToastContext);
}

export default function ToastProvider({ children }) {
  const [toast, setToast] = useState({
    id: 0,
    text: "",
    type: "info",
    visible: false,
  });

  const timeoutRef = useRef(null);

  const showToast = ({ text, type = "info" }) => {
    if (timeoutRef.current) {
      clearTimeout(timeoutRef.current);
      timeoutRef.current = null;
    }

    setToast((prev) => ({
      id: prev.id + 1,
      text,
      type,
      visible: true,
    }));

    timeoutRef.current = setTimeout(() => {
      setToast((t) => ({ ...t, visible: false }));
      timeoutRef.current = null;
    }, 1500);
  };

  useEffect(() => {
    if (!toast.visible) return;

    const hide = () => {
      setToast((t) => ({ ...t, visible: false }));
      if (timeoutRef.current) {
        clearTimeout(timeoutRef.current);
        timeoutRef.current = null;
      }
    };

    const events = ["scroll", "click", "keydown", "touchstart", "wheel"];

    const timer = setTimeout(() => {
      events.forEach((evt) => window.addEventListener(evt, hide, { once: true }));
    }, 0);

    return () => {
      clearTimeout(timer);
      events.forEach((evt) => window.removeEventListener(evt, hide));
    };
  }, [toast.visible]);

  const variantStyles = {
    info: {
      bg: "bg-blue-50/90 dark:bg-blue-900/70",
      text: "text-blue-700 dark:text-blue-100",
      border: "border-blue-300/50",
      icon: <Info size={14} className="text-blue-500" />,
    },
    success: {
      bg: "bg-green-50/90 dark:bg-green-900/70",
      text: "text-green-700 dark:text-green-100",
      border: "border-green-300/50",
      icon: <CheckCircle2 size={14} className="text-green-500" />,
    },
    warning: {
      bg: "bg-amber-50/90 dark:bg-amber-900/70",
      text: "text-amber-800 dark:text-amber-100",
      border: "border-amber-300/50",
      icon: <AlertTriangle size={14} className="text-amber-500" />,
    },
    error: {
      bg: "bg-red-100/90 dark:bg-red-900/70",
      text: "text-red-700 dark:text-red-100",
      border: "border-red-300/50",
      icon: <XCircle size={14} className="text-red-500" />,
    },
  };

  const current = variantStyles[toast.type] || variantStyles.info;

  return (
    <ToastContext.Provider value={{ showToast }}>
      {children}

      {typeof window !== "undefined" &&
        createPortal(
          <AnimatePresence>
            {toast.visible && (
              <motion.div
                key={toast.id}
                initial={{ opacity: 0, y: -10 }}
                animate={{ opacity: 1, y: 0 }}
                exit={{ opacity: 0, y: -10 }}
                transition={{ duration: 0.2 }}
                className="fixed top-6 inset-x-0 z-[99999] flex justify-center pointer-events-none"
              >
                <div
                  className={`
              pointer-events-auto
              px-4 py-2 rounded-lg text-sm font-medium
              backdrop-blur-md shadow-lg border
              ${current.bg} ${current.text} ${current.border}
            `}
                >
                  <div className="flex items-center gap-2">
                    {current.icon}
                    <span>{toast.text}</span>
                  </div>
                </div>
              </motion.div>
            )}
          </AnimatePresence>,
          document.body
        )}
    </ToastContext.Provider>
  );
}
