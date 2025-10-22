"use client";

import RegionsGrid from "@/app/search/step2/RegionsGrid";
import { motion } from "framer-motion";
import { useSearchStore } from "@/app/search/store/searchStore";
import { animation } from "@/app/search/_const/animation";

export default function Step2Page() {
  const { currentAnimation } = useSearchStore();
  return (
    <motion.div {...animation[currentAnimation]} className="min-h-[50vh]">
      <RegionsGrid />
    </motion.div>
  );
}
