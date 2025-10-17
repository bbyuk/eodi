"use client";

export default function RegionsGrid({ cash, onBack, onSelectRegion }) {
  // 퍼블용 더미 라벨들 (실제론 API에서 받은 "법정동" 사용)
  const sellRegions = [
    "Lorem-dong A",
    "Lorem-dong B",
    "Lorem-dong C",
    "Lorem-dong D",
    "Lorem-dong E",
    "Lorem-dong F",
  ];
  const rentRegions = [
    "Ipsum-dong A",
    "Ipsum-dong B",
    "Ipsum-dong C",
    "Ipsum-dong D",
    "Ipsum-dong E",
    "Ipsum-dong F",
  ];

  return (
    <div className="space-y-14">
      <div className="flex items-center justify-between">
        <div>
          <h2 className="text-3xl font-semibold">Select a region</h2>
          <p className="text-slate-500">
            Budget: {cash ? `${Number(cash).toLocaleString()} 만원` : "-"}
          </p>
        </div>
        <button
          onClick={onBack}
          className="px-4 py-2 rounded-lg border border-slate-300 hover:bg-slate-50 text-sm"
        >
          Back
        </button>
      </div>

      {/* 매수 가능 법정동 */}
      <section>
        <h3 className="text-xl font-semibold mb-4">For Purchase</h3>
        <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-3">
          {sellRegions.map((name) => (
            <button
              key={name}
              type="button"
              onClick={() => onSelectRegion(name)}
              className="w-full px-4 py-3 rounded-lg border border-slate-300 hover:bg-slate-50 text-left"
            >
              {name}
            </button>
          ))}
        </div>
      </section>

      {/* 전월세 가능 법정동 */}
      <section>
        <h3 className="text-xl font-semibold mb-4">For Rent</h3>
        <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-3">
          {rentRegions.map((name) => (
            <button
              key={name}
              type="button"
              onClick={() => onSelectRegion(name)}
              className="w-full px-4 py-3 rounded-lg border border-slate-300 hover:bg-slate-50 text-left"
            >
              {name}
            </button>
          ))}
        </div>
      </section>
    </div>
  );
}
