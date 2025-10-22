"use client";

import StepCash from "@/app/search/step1/StepCash";
import { motion } from "framer-motion";
import { animation } from "@/app/search/animation";
import { useSearchStore } from "@/app/search/store/searchStore";

export default function Step1Page() {
  const { currentAnimation } = useSearchStore();

  return (
    <motion.div {...animation[currentAnimation]} className="min-h-[50vh]">
      <StepCash />
    </motion.div>
  );
}
