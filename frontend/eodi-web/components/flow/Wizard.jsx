"use client";
import { useState } from "react";
import StepCash from "@/components/flow/StepCash";
import RegionsGrid from "@/components/flow/RegionsGrid";
import OptionalFilters from "@/components/flow/OptionalFilters";
import StepIndicator from "@/components/flow/StepIndicator";

export default function Wizard() {
  const [step, setStep] = useState(1);
  const [cash, setCash] = useState("");
  const [selectedRegion, setSelectedRegion] = useState(null);

  return (
    <section className="max-w-6xl mx-auto px-6 py-16 space-y-10 transition-all duration-300">
      {/* ✅ 단계 인디케이터 */}
      <div className="flex justify-center mb-10">
        <StepIndicator step={step} />
      </div>

      {/* ✅ 단계별 섹션 렌더링 */}
      {step === 1 && (
        <StepCash cash={cash} onChangeCash={setCash} onNext={() => (cash ? setStep(2) : null)} />
      )}

      {step === 2 && (
        <RegionsGrid
          cash={cash}
          onBack={() => setStep(1)}
          onNext={(regions) => {
            setSelectedRegion(regions);
            setStep(3);
          }}
        />
      )}

      {step === 3 && (
        <OptionalFilters region={selectedRegion} onBack={() => setStep(2)} onApply={() => {}} />
      )}
    </section>
  );
}
