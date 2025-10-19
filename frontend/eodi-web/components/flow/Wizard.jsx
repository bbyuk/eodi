"use client";
import { useState } from "react";
import { AnimatePresence, motion } from "framer-motion";
import StepCash from "./StepCash";
import RegionsGrid from "./RegionsGrid";
import OptionalFilters from "./OptionalFilters";
import StepIndicator from "./StepIndicator";

export default function Wizard({ step, prevStep, goToStep }) {
  const [cash, setCash] = useState("");
  const [selectedRegion, setSelectedRegion] = useState(null);

  const isForward = step > prevStep;

  // absolute 제거, x/opacity만 사용
  const slideVariants = {
    enter: (direction) => ({ x: direction ? 80 : -80, opacity: 0 }),
    center: { x: 0, opacity: 1 },
    exit: (direction) => ({ x: direction ? -80 : 80, opacity: 0 }),
  };

  return (
    <section className="relative max-w-6xl mx-auto px-6 pt-[8vh] pb-[12vh] min-h-[70vh] overflow-hidden">
      {/* ▼ 전환되는 콘텐츠 영역만 grid 겹침 */}
      <div className="grid min-h-[40vh]" style={{ gridTemplate: "1fr / 1fr" }}>
        <AnimatePresence custom={isForward} mode="wait">
          <motion.div
            key={step}
            custom={isForward}
            variants={slideVariants}
            initial="enter"
            animate="center"
            exit="exit"
            transition={{
              x: { type: "spring", stiffness: 200, damping: 25 },
              opacity: { duration: 0.25 },
            }}
            className="w-full"
            style={{ gridArea: "1 / 1 / 2 / 2" }} // 같은 셀에 겹치기
          >
            {step === 1 && (
              <StepCash cash={cash} onChangeCash={setCash} onNext={() => cash && goToStep(2)} />
            )}

            {step === 2 && (
              <RegionsGrid
                cash={cash}
                onBack={() => goToStep(1)}
                onNext={(regions) => {
                  setSelectedRegion(regions);
                  goToStep(3);
                }}
              />
            )}

            {step === 3 && (
              <OptionalFilters
                region={selectedRegion}
                onBack={() => goToStep(2)}
                onApply={() => {}}
              />
            )}
          </motion.div>
        </AnimatePresence>
      </div>
    </section>
  );
}
