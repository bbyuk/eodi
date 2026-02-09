"use client";

import RegionsGrid from "@/app/search/region-select-step/RegionsGrid";
import { motion } from "framer-motion";
import { useSearchStore } from "@/app/search/store/searchStore";
import { animation } from "@/app/search/_const/animation";
import { direction } from "@/app/search/_const/direction";
import ToastProvider from "@/components/ui/container/ToastProvider";

export default function Step2Page() {
  const { currentDirection } = useSearchStore();
  return (
    <motion.div
      {...animation[direction[currentDirection].animation]}
      className="w-full px-8 pt-[12vh] md:pt-[18vh] pb-[8vh] overflow-x-hidden"
    >
      <ToastProvider>
        <RegionsGrid />
      </ToastProvider>
    </motion.div>
  );
}
