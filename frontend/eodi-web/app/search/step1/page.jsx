"use client";

import StepCash from "@/app/search/step1/StepCash";
import { motion } from "framer-motion";
import { animation } from "@/app/search/_const/animation";
import { useSearchStore } from "@/app/search/store/searchStore";
import { direction } from "@/app/search/_const/direction";

export default function Step1Page() {
  const { currentDirection } = useSearchStore();
  return (
    <motion.div {...animation[direction[currentDirection].animation]}>
      <StepCash />
    </motion.div>
  );
}
