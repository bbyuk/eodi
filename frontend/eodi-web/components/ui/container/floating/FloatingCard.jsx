import { motion } from "framer-motion";
import { X } from "lucide-react";

export default function FloatingCard({ close, label, icon, children }) {
  return (
    <motion.aside
      key="opened"
      initial={{ y: 20, opacity: 0 }}
      animate={{ y: 0, opacity: 1 }}
      exit={{ y: 20, opacity: 0 }}
      transition={{ duration: 0.25, ease: "easeOut" }}
      className="w-[300px] rounded-2xl border border-gray-200
                 bg-white/90 backdrop-blur-md shadow-lg overflow-hidden"
    >
      {/* Header */}
      <div className="flex items-center justify-between px-4 py-2 border-b border-gray-200 bg-white/70">
        <div className="flex items-center gap-1.5">
          {icon ? icon : null}
          <h3 className="text-sm font-semibold text-gray-800">{label}</h3>
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
        {children}
      </div>
    </motion.aside>
  );
}
