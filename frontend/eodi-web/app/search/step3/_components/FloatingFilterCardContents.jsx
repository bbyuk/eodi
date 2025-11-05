"use client";

export default function FloatingFilterCardContents({ close }) {
  return (
    <div className="p-4 space-y-4 max-h-[60vh] overflow-y-auto scrollbar-thin scrollbar-thumb-gray-300 scrollbar-track-transparent">
      <FilterInput label="최소 금액" placeholder="예: 5억" />
      <FilterInput label="최대 금액" placeholder="예: 10억" />
      <FilterInput label="면적 (㎡)" placeholder="예: 84" />
      <FilterInput label="층수" placeholder="예: 10층 이상" />

      <button
        onClick={close}
        className="w-full mt-4 py-2 rounded-md bg-primary text-white font-medium text-sm hover:bg-primary/90 transition"
      >
        적용하기
      </button>
    </div>
  );
}

function FilterInput({ label, placeholder }) {
  return (
    <div>
      <label className="text-xs font-medium text-gray-600">{label}</label>
      <input
        type="text"
        placeholder={placeholder}
        className="w-full mt-1.5 px-3 py-2 border border-gray-300 rounded-md text-sm
                   focus:ring-2 focus:ring-primary/30 focus:border-primary outline-none transition"
      />
    </div>
  );
}
