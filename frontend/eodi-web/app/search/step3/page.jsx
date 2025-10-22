"use client";

import OptionalFilters from "@/app/search/step3/OptionalFilters";
import { motion } from "framer-motion";
import { useSearchStore } from "@/app/search/store/searchStore";
import { animation } from "@/app/search/_const/animation";

export default function Step3Page() {
  const { currentAnimation } = useSearchStore();

  return (
    <motion.div {...animation[currentAnimation]} className="min-h-[50vh]">
      <OptionalFilters />{" "}
    </motion.div>
  );
}
