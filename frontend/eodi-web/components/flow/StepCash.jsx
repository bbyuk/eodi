/**
 * 보유현금 입력(정수 / 만원)
 */
"use client";

export default function StepCash({ cash, onChangeCash, onNext }) {
  return (
    <div className="max-w-xl mx-auto text-center">
      <h1 className="text-4xl font-bold tracking-tight mb-4">Enter your budget</h1>
      <p className="text-slate-500 mb-10">
        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
      </p>

      <div className="text-left mb-8">
        <label className="block text-sm font-medium text-slate-600 mb-2">
          Cash (in 10,000 KRW)
        </label>
        <div className="relative">
          <input
            type="text"
            inputMode="numeric"
            value={cash}
            onChange={(e) => onChangeCash(e.target.value.replace(/[^0-9]/g, ""))}
            placeholder="e.g. 50000"
            className="w-full px-4 py-3 border border-slate-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:outline-none text-right pr-12"
          />
          <span className="absolute right-4 bottom-[13px] text-slate-500 text-sm">만원</span>
        </div>
      </div>

      <div className="flex items-center justify-center gap-3">
        <button
          onClick={onNext}
          disabled={!cash}
          className={`px-6 py-3 rounded-lg text-white font-medium transition ${
            cash ? "bg-blue-600 hover:bg-blue-700" : "bg-slate-300 cursor-not-allowed"
          }`}
        >
          Continue
        </button>
      </div>
    </div>
  );
}
