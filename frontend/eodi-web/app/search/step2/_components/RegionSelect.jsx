"use client";

import { ChevronDown } from "lucide-react";
import { useState } from "react";

const MOCK_REGIONS = {
  44: {
    name: "충청남도",
    sigungu: {
      441: "천안시 서북구",
      442: "천안시 동남구",
    },
  },
  45: {
    name: "전북특별자치도",
    sigungu: {
      451: "전주시 덕진구",
      452: "전주시 완산구",
    },
  },
};

export default function RegionSelect({ value, onChange }) {
  const [open, setOpen] = useState(false);

  const sido = value?.sidoCode;
  const sigungu = value?.sigunguCode;

  const label = sigungu
    ? `${MOCK_REGIONS[sido].name} · ${MOCK_REGIONS[sido].sigungu[sigungu]}`
    : sido
      ? MOCK_REGIONS[sido].name
      : "지역 전체";

  return (
    <div className="relative">
      <button
        onClick={() => setOpen((v) => !v)}
        className="flex items-center gap-2 rounded-full border px-4 py-2 text-sm hover:bg-gray-50"
      >
        <span>{label}</span>
        <ChevronDown size={16} />
      </button>

      {open && (
        <div className="absolute left-0 top-full z-20 mt-2 w-[260px] rounded-xl border bg-white shadow-lg p-3 space-y-3">
          {/* 시도 */}
          <div className="space-y-1">
            <div className="text-xs text-muted-foreground">시도</div>
            {Object.entries(MOCK_REGIONS).map(([code, r]) => (
              <button
                key={code}
                onClick={() => onChange({ sidoCode: code, sigunguCode: null })}
                className={`w-full text-left rounded-md px-2 py-1 text-sm
                  ${sido === code ? "bg-primary/10 text-primary" : "hover:bg-gray-100"}`}
              >
                {r.name}
              </button>
            ))}
          </div>

          {/* 시군구 */}
          {sido && (
            <div className="space-y-1 pt-2 border-t">
              <div className="text-xs text-muted-foreground">시군구</div>
              {Object.entries(MOCK_REGIONS[sido].sigungu).map(([code, name]) => (
                <button
                  key={code}
                  onClick={() => onChange({ sidoCode: sido, sigunguCode: code })}
                  className={`w-full text-left rounded-md px-2 py-1 text-sm
                      ${sigungu === code ? "bg-primary/10 text-primary" : "hover:bg-gray-100"}`}
                >
                  {name}
                </button>
              ))}
            </div>
          )}
        </div>
      )}
    </div>
  );
}
