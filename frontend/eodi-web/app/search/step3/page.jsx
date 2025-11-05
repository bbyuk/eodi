"use client";

import { motion } from "framer-motion";
import { useSearchStore } from "@/app/search/store/searchStore";
import { animation } from "@/app/search/_const/animation";
import { direction } from "@/app/search/_const/direction";
import DealListPage from "@/app/search/step3/DealListPage";

export default function Step3Page() {
  const { currentDirection } = useSearchStore();
  return (
    <motion.div
      {...animation[direction[currentDirection].animation]}
      className="w-full px-8 pt-[12vh] md:pt-[18vh] pb-[8vh] overflow-x-hidden"
    >
      <DealListPage />
    </motion.div>
  );
}
