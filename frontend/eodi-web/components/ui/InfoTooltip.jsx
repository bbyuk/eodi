"use client";

import { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { Info } from "lucide-react";

/**
 * 정보 툴팁 아이콘 (독립형)
 * @param {string | ReactNode} content - 툴팁 내부 내용
 * @param {string} [position="top"] - 툴팁 위치 ("top" | "bottom" | "left" | "right")
 * @param {string} [className] - 추가 Tailwind 클래스
 */
export default function InfoTooltip({ content, position = "top", className = "" }) {
  const [visible, setVisible] = useState(false);

  const positionClass = {
    top: "bottom-full mb-2 left-1/2 -translate-x-1/2",
    bottom: "top-full mt-2 left-1/2 -translate-x-1/2",
    left: "right-full mr-2 top-1/2 -translate-y-1/2",
    right: "left-full ml-2 top-1/2 -translate-y-1/2",
  }[position];

  return (
    <div
      className={`relative inline-flex items-center ${className}`}
      onMouseEnter={() => setVisible(true)}
      onMouseLeave={() => setVisible(false)}
      onFocus={() => setVisible(true)}
      onBlur={() => setVisible(false)}
    >
      {/* i 아이콘 */}
      <Info
        size={16}
        className="text-gray-400 hover:text-gray-600 cursor-pointer transition-colors"
      />

      {/* Tooltip 내용 */}
      <AnimatePresence>
        {visible && (
          <motion.div
            initial={{ opacity: 0, y: position === "top" ? -4 : 4 }}
            animate={{ opacity: 1, y: 0 }}
            exit={{ opacity: 0, y: position === "top" ? -4 : 4 }}
            transition={{ duration: 0.15 }}
            className={`absolute ${positionClass} z-50 w-max max-w-xs text-sm text-gray-700 bg-white/90 backdrop-blur-md shadow-md rounded-md px-3 py-2 whitespace-pre-line`}
          >
            {content}
          </motion.div>
        )}
      </AnimatePresence>
    </div>
  );
}
