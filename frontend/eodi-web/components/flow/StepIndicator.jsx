"use client";

/**
 * StepIndicator
 * @param {number} step - 현재 단계 (1, 2, 3)
 *
 * 한국형 단계 인디케이터 + 진행선 애니메이션
 */
export default function StepIndicator({ step }) {
  return (
    <div className="flex items-center justify-center mb-12">
      <div className="flex items-center w-full max-w-3xl">
        {/* STEP 1 */}
        <div className="flex flex-col items-center flex-1">
          <div
            className={`w-8 h-8 flex items-center justify-center rounded-full border-2 transition-all duration-300 ${
              step === 1
                ? "bg-blue-600 border-blue-600 text-white font-semibold"
                : step > 1
                  ? "bg-blue-100 border-blue-600 text-blue-600"
                  : "bg-slate-100 border-slate-300 text-slate-400"
            }`}
          >
            1
          </div>
          <span
            className={`mt-2 text-xs transition-colors duration-300 ${
              step >= 1 ? "text-blue-600 font-medium" : "text-slate-400"
            }`}
          >
            현금입력
          </span>
        </div>

        {/* 선 1→2 */}
        <div
          className={`flex-1 h-[2px] mx-2 rounded-full overflow-hidden bg-slate-200 transition-all duration-500 ease-in-out ${
            step > 1 ? "animate-progress-fill" : ""
          }`}
        ></div>

        {/* STEP 2 */}
        <div className="flex flex-col items-center flex-1">
          <div
            className={`w-8 h-8 flex items-center justify-center rounded-full border-2 transition-all duration-300 ${
              step === 2
                ? "bg-blue-600 border-blue-600 text-white font-semibold"
                : step > 2
                  ? "bg-blue-100 border-blue-600 text-blue-600"
                  : "bg-slate-100 border-slate-300 text-slate-400"
            }`}
          >
            2
          </div>
          <span
            className={`mt-2 text-xs transition-colors duration-300 ${
              step >= 2 ? "text-blue-600 font-medium" : "text-slate-400"
            }`}
          >
            지역선택
          </span>
        </div>

        {/* 선 2→3 */}
        <div
          className={`flex-1 h-[2px] mx-2 rounded-full overflow-hidden bg-slate-200 transition-all duration-500 ease-in-out ${
            step > 2 ? "animate-progress-fill" : ""
          }`}
        ></div>

        {/* STEP 3 */}
        <div className="flex flex-col items-center flex-1">
          <div
            className={`w-8 h-8 flex items-center justify-center rounded-full border-2 transition-all duration-300 ${
              step === 3
                ? "bg-blue-600 border-blue-600 text-white font-semibold"
                : "bg-slate-100 border-slate-300 text-slate-400"
            }`}
          >
            3
          </div>
          <span
            className={`mt-2 text-xs transition-colors duration-300 ${
              step >= 3 ? "text-blue-600 font-medium" : "text-slate-400"
            }`}
          >
            조건추가
          </span>
        </div>
      </div>
    </div>
  );
}
