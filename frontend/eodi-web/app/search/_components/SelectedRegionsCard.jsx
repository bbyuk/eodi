"use client";
import { motion } from "framer-motion";

export default function SelectedRegionsCard() {
  return (
    <motion.aside
      initial={{ x: 20, opacity: 0 }}
      animate={{ x: 0, opacity: 1 }}
      transition={{ duration: 0.25, ease: "easeOut" }}
      className="fixed right-6 top-1/3
                 w-[230px] max-h-[60vh]
                 flex flex-col
                 rounded-2xl border border-border/50
                 bg-white/80 backdrop-blur-md shadow-lg
                 z-30 overflow-hidden
                 md:right-6 sm:right-3"
    >
      {/* Header */}
      <div className="flex items-center justify-between px-3 py-2 border-b border-border/40">
        <div className="flex items-center gap-1">
          <span className="text-lg">📋</span>
          <h3 className="text-sm font-semibold text-text-primary">선택 지역</h3>
        </div>
        <button className="text-sm text-text-secondary hover:text-text-primary">×</button>
      </div>

      {/* Body */}
      <div className="flex-1 overflow-y-auto px-3 py-2 space-y-1.5">
        {["노원구", "강남구", "서초구"].map((region) => (
          <div
            key={region}
            className="flex items-center justify-between px-2.5 py-1.5 rounded-md border border-border/50
                       bg-primary-bg/40 hover:bg-primary-bg/60 transition text-sm"
          >
            <span>{region}</span>
            <button className="text-primary text-xs hover:underline">삭제</button>
          </div>
        ))}
      </div>

      {/* Footer */}
      <div className="p-3 border-t border-border/40">
        <button className="w-full py-2 text-sm rounded-md bg-primary text-white hover:bg-primary/90 transition">
          다음 단계로
        </button>
      </div>
    </motion.aside>
  );
}
