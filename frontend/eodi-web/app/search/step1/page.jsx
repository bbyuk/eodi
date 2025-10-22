"use client";

import StepCash from "@/app/search/step1/StepCash";
import { motion } from "framer-motion";

export default function Step1Page() {
  return (
    <motion.div
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.35, ease: "easeOut" }}
      className="min-h-[60vh]"
    >
      <StepCash />
    </motion.div>
  );
}
