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
      className="w-full px-8 pt-[12vh] md:pt-[18vh] pb-[8vh] overflow-x-hidden"
    >
      <CapitalInputPage />
    </motion.div>
  );
}
