"use client";
import { useState } from "react";
import { AnimatePresence, motion } from "framer-motion";
import StepCash from "./StepCash";
import RegionsGrid from "./RegionsGrid";
import OptionalFilters from "./OptionalFilters";
import StepIndicator from "./StepIndicator";

export default function Wizard() {
  const [step, setStep] = useState(1);
  const [prevStep, setPrevStep] = useState(1); // 이전 단계 기억
  const [cash, setCash] = useState("");
  const [selectedRegion, setSelectedRegion] = useState(null);

  const goToStep = (next) => {
    setPrevStep(step);
    setStep(next);
  };

  // 방향 판별 (true면 앞으로, false면 뒤로)
  const isForward = step > prevStep;

  const slideVariants = {
    enter: (direction) => ({
      x: direction ? 80 : -80,
      opacity: 0,
      position: "absolute",
      width: "100%",
    }),
    center: {
      x: 0,
      opacity: 1,
      position: "relative",
    },
    exit: (direction) => ({
      x: direction ? -80 : 80,
      opacity: 0,
      position: "absolute",
      width: "100%",
    }),
  };

  return (
    <section className="max-w-6xl mx-auto px-6 py-16 overflow-hidden relative min-h-[60vh]">
      <div className="flex justify-center mb-10">
        <StepIndicator step={step} prevStep={prevStep} />
      </div>

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
            opacity: { duration: 0.2 },
          }}
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
    </section>
  );
}
