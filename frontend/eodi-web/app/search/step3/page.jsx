"use client";

import OptionalFilters from "@/app/search/step3/OptionalFilters";
import { motion } from "framer-motion";

export default function Step3Page() {
  return (
    <motion.div
      initial={{ opacity: 0, x: 60 }}
      animate={{ opacity: 1, x: 0 }}
      transition={{ duration: 0.35, ease: "easeOut" }}
      className="min-h-[60vh]"
    >
      <OptionalFilters />{" "}
    </motion.div>
  );
}
