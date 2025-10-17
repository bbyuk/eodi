/**
 * 입력 파라미터 컨테이너
 */
"use client";
import { useState } from "react";
import StepCash from "./StepCash";
import RegionsGrid from "./RegionsGrid";
import OptionalFilters from "./OptionalFilters";

export default function Wizard() {
  const [step, setStep] = useState(1);
  const [cash, setCash] = useState(""); // 만원 단위
  const [selectedRegion, setSelectedRegion] = useState(null);

  return (
    <section className="max-w-6xl mx-auto px-6 py-16">
      {/* 상단 단계 인디케이터 */}
      <div className="flex items-center gap-2 mb-10 text-sm text-slate-600">
        <span
          className={`px-2.5 py-1 rounded-full ${step >= 1 ? "bg-blue-600 text-white" : "bg-slate-200"}`}
        >
          1
        </span>
        <span>Cash</span>
        <span className="opacity-50">/</span>
        <span
          className={`px-2.5 py-1 rounded-full ${step >= 2 ? "bg-blue-600 text-white" : "bg-slate-200"}`}
        >
          2
        </span>
        <span>Regions</span>
        <span className="opacity-50">/</span>
        <span
          className={`px-2.5 py-1 rounded-full ${step >= 3 ? "bg-blue-600 text-white" : "bg-slate-200"}`}
        >
          3
        </span>
        <span>Optional</span>
      </div>

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
