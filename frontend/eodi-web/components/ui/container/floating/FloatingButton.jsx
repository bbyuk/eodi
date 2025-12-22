import { motion } from "framer-motion";
import { ChevronDown } from "lucide-react";
import React from "react";

export default function FloatingButton({
  open = () => {},
  label = "",
  icon = React.ReactNode,
  activeCount = 0,
}) {
  const isFiltered = activeCount > 0;
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
      {icon}
      <span>
        {label}
        {isFiltered && <span className="text-xs opacity-80"> · {activeCount}</span>}
      </span>
      <ChevronDown size={14} className="opacity-80" />
    </motion.button>
  );
}
