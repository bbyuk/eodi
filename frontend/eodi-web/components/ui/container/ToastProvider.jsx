"use client";

import { createContext, useContext, useState } from "react";
import { motion, AnimatePresence } from "framer-motion";

const ToastContext = createContext({
  showToast: () => {},
});

export function useToast() {
  return useContext(ToastContext);
}

/**
 * react context 기반 Tooltip Provider
 * @param children
 * @returns {JSX.Element}
 * @constructor
 */
export default function ToastProvider({ children }) {
  const [toast, setToast] = useState({ text: "", x: 0, y: 0, visible: false });

  const showToast = (text, event) => {
    const rect = event.currentTarget.getBoundingClientRect();
    console.log(rect);
    setToast({
      text,
      x: rect.left + rect.width / 2,
      y: rect.top,
      visible: true,
    });
    setTimeout(() => setToast((t) => ({ ...t, visible: false })), 1500);
  };

  return (
    <ToastContext.Provider value={{ showToast }}>
      {children}

      <AnimatePresence>
        {toast.visible && (
          <motion.div
            initial={{ opacity: 0, y: 5 }}
            animate={{ opacity: 1, y: 0 }}
            exit={{ opacity: 0, y: 5 }}
            transition={{ duration: 0.2 }}
            style={{
              position: "fixed",
              top: toast.y - 10,
              left: toast.x,
              transform: "translate(-50%, -100%)",
              zIndex: 9999,
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
