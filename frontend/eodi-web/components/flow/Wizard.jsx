"use client";
import { useState } from "react";
import StepCash from "./StepCash";
import RegionsGrid from "./RegionsGrid";
import OptionalFilters from "./OptionalFilters";
import StepIndicator from "./StepIndicator"; // ✅ 추가

export default function Wizard() {
  const [step, setStep] = useState(1);
  const [cash, setCash] = useState(""); // 만원 단위
  const [selectedRegion, setSelectedRegion] = useState(null);

  return (
    <section className="max-w-6xl mx-auto px-6 py-16">
      {/* ✅ 공통 단계 인디케이터 */}
      <StepIndicator step={step} />

      {/* 단계별 섹션 */}
      {step === 1 && (
        <StepCash cash={cash} onChangeCash={setCash} onNext={() => (cash ? setStep(2) : null)} />
      )}

      {step === 2 && (
        <RegionsGrid
          cash={cash}
          onBack={() => setStep(1)}
          onSelectRegion={(region) => {
            setSelectedRegion(region);
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
