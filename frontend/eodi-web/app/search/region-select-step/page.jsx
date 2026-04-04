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
      className="w-full overflow-x-hidden px-0 pb-4 pt-24 sm:pt-28 lg:pt-32"
    >
      <ToastProvider>
        <RegionsGrid />
      </ToastProvider>
    </motion.div>
  );
}
