/**
 * 보유현금 입력 (정수 / 만원)
 */
"use client";

export default function StepCash({ cash, onChangeCash, onNext }) {
  return (
    <div className="max-w-xl mx-auto text-center">
      {/* 타이틀 */}
      <h1 className="text-4xl font-bold tracking-tight mb-4 text-text-primary">
        Enter your budget
      </h1>
      <p className="text-text-secondary mb-10">
        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
      </p>

      {/* 입력 섹션 */}
      <div className="text-left mb-12">
        <label className="block text-sm font-medium text-text-secondary mb-2">
          Cash (in 10,000 KRW)
        </label>

        {/* 인풋 + 버튼 한 줄 배치 */}
        <div className="flex items-center gap-3">
          <div className="relative flex-1">
            <input
              type="text"
              inputMode="numeric"
              value={cash}
              onChange={(e) => onChangeCash(e.target.value.replace(/[^0-9]/g, ""))}
              placeholder="e.g. 50000"
              className="w-full px-4 py-3 border border-border rounded-lg text-right pr-12 text-text-primary
                         focus:ring-2 focus:ring-primary focus:border-primary focus:outline-none transition"
            />
            <span className="absolute right-4 bottom-[13px] text-text-secondary text-sm">만원</span>
          </div>

          {/* Continue 버튼 */}
          <button
            onClick={onNext}
            disabled={!cash}
            className={`whitespace-nowrap px-6 py-3 rounded-lg text-white font-medium shadow-sm transition-all duration-200
              ${
                cash
                  ? "bg-primary hover:bg-primary-hover hover:translate-y-[1px]"
                  : "bg-border cursor-not-allowed text-text-secondary"
              }`}
          >
            Continue
          </button>
        </div>
      </div>
    </div>
  );
}
