"use client";

import OptionalFilters from "@/app/search/step3/OptionalFilters";
import { motion } from "framer-motion";
import { useSearchStore } from "@/app/search/store/searchStore";
import { animation } from "@/app/search/_const/animation";
import { direction } from "@/app/search/_const/direction";

export default function Step3Page() {
  const { currentDirection } = useSearchStore();
  return (
    <motion.div {...animation[direction[currentDirection].animation]}>
      <OptionalFilters />{" "}
    </motion.div>
  );
}
