"use client";

import CapitalInputPage from "@/app/search/step1/CapitalInputPage";
import { motion } from "framer-motion";
import { animation } from "@/app/search/_const/animation";
import { useSearchStore } from "@/app/search/store/searchStore";
import { direction } from "@/app/search/_const/direction";

export default function Step1Page() {
  const { currentDirection } = useSearchStore();
  return (
    <motion.div
      {...animation[direction[currentDirection].animation]}
      className="w-full overflow-x-hidden px-0 pb-4 pt-24 sm:pt-28 lg:pt-32"
    >
      <CapitalInputPage />
    </motion.div>
  );
}
