"use client";

import { motion, AnimatePresence } from "framer-motion";
import { SlidersHorizontal, X } from "lucide-react";
import { useState } from "react";

export default function FloatingFilterCard() {
  const [isOpen, setIsOpen] = useState(true);

  const handleOpen = () => setIsOpen(true);
  const handleClose = () => setIsOpen(false);

  return (
    <div className="fixed right-6 top-[calc(4rem + 12vh)] z-40 md:right-6 sm:right-3">
      <AnimatePresence mode="wait">
        {isOpen ? <OpenedFilterCard close={handleClose} /> : <FilterOpenButton open={handleOpen} />}
      </AnimatePresence>
    </div>
  );
}

function FilterOpenButton({ open }) {
  return (
    <motion.button
      key="collapsed"
      initial={{ scale: 0.8, opacity: 0 }}
      animate={{ scale: 1, opacity: 1 }}
      exit={{ scale: 0.8, opacity: 0 }}
      transition={{ duration: 0.2 }}
      onClick={open}
      className="flex items-center gap-2 px-4 py-2 rounded-full
                 bg-primary text-white shadow-lg hover:bg-primary/90
                 transition text-sm font-medium"
    >
      <SlidersHorizontal size={16} />
      <span>필터</span>
    </motion.button>
  );
}

function OpenedFilterCard({ close }) {
  return (
    <motion.aside
      key="opened"
      initial={{ y: 20, opacity: 0 }}
      animate={{ y: 0, opacity: 1 }}
      exit={{ y: 20, opacity: 0 }}
      transition={{ duration: 0.25, ease: "easeOut" }}
      className="w-[260px] rounded-2xl border border-gray-200
                 bg-white/90 backdrop-blur-md shadow-lg overflow-hidden"
    >
      {/* Header */}
      <div className="flex items-center justify-between px-4 py-2 border-b border-gray-200 bg-white/70">
        <div className="flex items-center gap-1.5">
          <SlidersHorizontal size={16} className="text-primary" />
          <h3 className="text-sm font-semibold text-gray-800">추가 조건</h3>
        </div>
        <button
          onClick={close}
          className="p-1.5 rounded-md hover:bg-gray-100 text-gray-500 hover:text-gray-700 transition"
        >
          <X size={16} strokeWidth={2} />
        </button>
      </div>

      {/* Body */}
      <div className="p-4 space-y-4 max-h-[60vh] overflow-y-auto scrollbar-thin scrollbar-thumb-gray-300 scrollbar-track-transparent">
        <FilterInput label="최소 금액" placeholder="예: 5억" />
        <FilterInput label="최대 금액" placeholder="예: 10억" />
        <FilterInput label="면적 (㎡)" placeholder="예: 84" />
        <FilterInput label="층수" placeholder="예: 10층 이상" />

        <button
          onClick={close}
          className="w-full mt-4 py-2 rounded-md bg-primary text-white font-medium text-sm hover:bg-primary/90 transition"
        >
          적용하기
        </button>
      </div>
    </motion.aside>
  );
}

function FilterInput({ label, placeholder }) {
  return (
    <div>
      <label className="text-xs font-medium text-gray-600">{label}</label>
      <input
        type="text"
        placeholder={placeholder}
        className="w-full mt-1.5 px-3 py-2 border border-gray-300 rounded-md text-sm
                   focus:ring-2 focus:ring-primary/30 focus:border-primary outline-none transition"
      />
    </div>
  );
}
