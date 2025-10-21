import { useState } from "react";
import { ChevronDown } from "lucide-react";
import { AnimatePresence, motion } from "framer-motion";

export default function FilterGroup({ title, subtitle, regions, children }) {
  const [open, setOpen] = useState(false);

  return (
    <section className="border-t border-border pt-8">
      <div className="mb-6">
        <h2 className="text-xl font-semibold text-text-primary">{title}</h2>
        <p className="text-sm text-text-secondary mt-1">{subtitle}</p>
        <p className="text-sm text-text-secondary mt-1">
          선택 지역:{" "}
          <span className="font-medium text-text-primary">{[...regions].join(", ")}</span>
        </p>
      </div>

      <div>
        <button
          onClick={() => setOpen((v) => !v)}
          className="flex items-center gap-2 text-sm font-medium text-primary hover:text-primary-hover transition"
        >
          <ChevronDown size={18} className={`transition-transform ${open ? "rotate-180" : ""}`} />
          {open ? "추가 조건 닫기" : "추가 조건 펼치기"}
        </button>

        <AnimatePresence initial={false}>
          {open && (
            <motion.div
              initial={{ opacity: 0, height: 0 }}
              animate={{ opacity: 1, height: "auto" }}
              exit={{ opacity: 0, height: 0 }}
              transition={{ duration: 0.25 }}
              className="mt-6 space-y-8"
            >
              {children}
            </motion.div>
          )}
        </AnimatePresence>
      </div>
    </section>
  );
}
