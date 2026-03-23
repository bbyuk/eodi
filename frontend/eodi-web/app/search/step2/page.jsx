"use client";

import { motion } from "framer-motion";
import { useSearchStore } from "@/app/search/store/searchStore";
import { animation } from "@/app/search/_const/animation";
import { direction } from "@/app/search/_const/direction";
import DealListPage from "@/app/search/step2/DealListPage";

export default function Step3Page() {
  const { currentDirection } = useSearchStore();
  return (
    <motion.div
      {...animation[direction[currentDirection].animation]}
      className="w-full overflow-x-hidden px-0 pb-4 pt-24 sm:pt-28 lg:pt-32"
    >
      <DealListPage />
    </motion.div>
  );
}
