"use client";

import RegionsGrid from "@/app/search/step2/RegionsGrid";
import { motion } from "framer-motion";
import { useSearchStore } from "@/app/search/store/searchStore";
import { animation } from "@/app/search/_const/animation";
import { direction } from "@/app/search/_const/direction";

export default function Step2Page() {
  const { currentDirection } = useSearchStore();
  return (
    <motion.div {...animation[direction[currentDirection].animation]}>
      <RegionsGrid />
    </motion.div>
  );
}
