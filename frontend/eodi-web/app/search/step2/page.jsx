"use client";

import RegionsGrid from "@/app/search/step2/RegionsGrid";
import { motion } from "framer-motion";

export default function Step2Page() {
  return (
    <motion.div
      initial={{ opacity: 0, x: 60 }}
      animate={{ opacity: 1, x: 0 }}
      transition={{ duration: 0.35, ease: "easeOut" }}
      className="min-h-[60vh]"
    >
      <RegionsGrid />
    </motion.div>
  );
}
