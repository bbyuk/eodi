"use client";
import { useEffect, useState } from "react";
import { AnimatePresence, motion } from "framer-motion";
import StepCash from "./step1/StepCash";
import RegionsGrid from "./step2/RegionsGrid";
import OptionalFilters from "./step3/OptionalFilters";
import StepIndicator from "@/app/search/StepIndicator";

export default function Wizard() {
  const [step, setStep] = useState(1);
  const [prevStep, setPrevStep] = useState(1);

  const goToStep = (next) => {
    setPrevStep(step);
    setStep(next);
  };

  const [cash, setCash] = useState("");
  const [selectedSellRegion, setSelectedSellRegion] = useState(null);
  const [selectedLeaseRegion, setSelectedLeaseRegion] = useState(null);

  const isForward = step > prevStep;

  const slideVariants = {
    enter: (direction) => ({ x: direction ? 80 : -80, opacity: 0 }),
    center: { x: 0, opacity: 1 },
    exit: (direction) => ({ x: direction ? -80 : 80, opacity: 0 }),
  };

  const renderStep = () => {
    switch (step) {
      case 1:
        return <StepCash cash={cash} onChangeCash={setCash} />;
      case 2:
        return (
          <RegionsGrid
            cash={cash}
            onSelect={(sellRegions, leaseRegions) => {
              setSelectedSellRegion(sellRegions);
              setSelectedLeaseRegion(leaseRegions);
            }}
          />
        );
      case 3:
        return (
          <OptionalFilters sellRegions={selectedSellRegion} leaseRegions={selectedLeaseRegion} />
        );
      default:
        return null;
    }
  };

  const handleNext = () => {
    if (step === 1 && !cash) return;
    if (step === 2 && !selectedSellRegion?.size && !selectedLeaseRegion?.size) return;
    if (step < 3) goToStep(step + 1);
    else alert("필터 적용 완료!");
  };

  const handlePrev = () => {
    if (step > 1) goToStep(step - 1);
  };

  useEffect(() => {
    window.scrollTo({ top: 0, behavior: "instant" });
  }, [step]);

  return (
    <>
      <div className="flex justify-center mb-10">
        <StepIndicator step={step} prevStep={prevStep} />
      </div>
      <section className="relative max-w-6xl mx-auto px-6 pt-[8vh] pb-[16vh] min-h-[60vh] overflow-hidden">
        {/* 콘텐츠 영역 */}
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
              style={{ gridArea: "1 / 1 / 2 / 2" }}
            >
              {renderStep()}
            </motion.div>
          </AnimatePresence>
        </div>

        {/* ▼ 하단 고정 버튼 바 */}
        <div className="fixed bottom-0 left-0 w-full bg-white/80 backdrop-blur-md border-t border-border py-4 px-6 z-50">
          <div className="max-w-6xl mx-auto flex justify-between items-center">
            {step > 1 ? (
              <button
                onClick={handlePrev}
                className="px-6 py-3 rounded-xl border text-sm font-medium text-text-secondary hover:bg-primary-bg transition-all"
              >
                이전으로
              </button>
            ) : (
              <div /> // 첫 스텝에는 이전 버튼 없음
            )}

            <button
              onClick={handleNext}
              disabled={
                (step === 1 && !cash) ||
                (step === 2 && !selectedSellRegion?.size && !selectedLeaseRegion?.size)
              }
              className={`px-6 py-3 rounded-xl text-sm font-semibold transition-all shadow-sm ${
                (step === 1 && !cash) ||
                (step === 2 && !selectedSellRegion?.size && !selectedLeaseRegion?.size)
                  ? "bg-border text-text-secondary cursor-not-allowed"
                  : "bg-primary text-white hover:bg-primary-hover"
              }`}
            >
              {step === 3 ? "찾아보기" : "다음으로"}
            </button>
          </div>
        </div>
      </section>
    </>
  );
}
