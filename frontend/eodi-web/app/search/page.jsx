"use client";
import Wizard from "@/components/flow/Wizard";
import StepIndicator from "@/components/flow/StepIndicator";
import { useState } from "react";

export default function MainSearchPage() {
  const [step, setStep] = useState(1);
  const [prevStep, setPrevStep] = useState(1);

  const goToStep = (next) => {
    setPrevStep(step);
    setStep(next);
  };

  return (
    <div className="pt-24 w-full min-h-[100vh]">
      <div className="flex justify-center mb-10">
        <StepIndicator step={step} prevStep={prevStep} />
      </div>
      <Wizard step={step} prevStep={prevStep} goToStep={goToStep} />
    </div>
  );
}
