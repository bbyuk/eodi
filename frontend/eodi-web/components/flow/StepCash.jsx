/**
 * Step 1 - 보유현금 입력
 */
"use client";

export default function StepCash({ cash, onChangeCash, onNext }) {
  return (
    <section className="max-w-5xl mx-auto px-6 pt-[1vh] pb-[5vh]">
      {/* Header */}
      <header className="mb-14">
        <h1 className="text-3xl md:text-4xl font-semibold text-text-primary mb-3 leading-tight">
          예산을 입력해주세요
        </h1>
        <p className="text-base text-text-secondary leading-relaxed">
          입력한 금액으로 매수, 전·월세가 가능한 지역을 바로 찾아드릴게요.
        </p>
      </header>

      {/* Input Section */}
      <section>
        <label className="block text-sm font-medium text-text-secondary mb-2">
          보유 예산 (만원 단위)
        </label>

        <div className="flex items-center gap-3">
          <div className="relative flex-1">
            <input
              type="text"
              inputMode="numeric"
              value={cash}
              onChange={(e) => onChangeCash(e.target.value.replace(/[^0-9]/g, ""))}
              placeholder="예: 50000"
              className="w-full px-4 py-3 border border-border rounded-lg text-right pr-12 text-text-primary
                         placeholder:text-text-secondary focus:ring-2 focus:ring-primary
                         focus:border-primary focus:outline-none transition"
            />
            <span className="absolute right-4 top-1/2 -translate-y-1/2 text-text-secondary text-sm">
              만원
            </span>
          </div>

          <button
            onClick={onNext}
            disabled={!cash}
            className={`whitespace-nowrap px-6 py-3 rounded-lg font-medium text-white shadow-sm transition-all duration-200 ${
              cash
                ? "bg-primary hover:bg-primary-hover hover:translate-y-[1px]"
                : "bg-border cursor-not-allowed text-text-secondary"
            }`}
          >
            찾아보기
          </button>
        </div>
      </section>
    </section>
  );
}
