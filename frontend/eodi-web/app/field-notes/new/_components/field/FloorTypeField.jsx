"use client";

import NumberInput from "@/components/ui/NumberInput";
import FieldTitle from "@/app/field-notes/new/_components/field/FieldTitle";

const FLOOR_OPTIONS = [
  { label: "저층", value: "LOW" },
  { label: "중층", value: "MID" },
  { label: "고층", value: "HIGH" },
  { label: "탑층", value: "TOP" },
  { label: "직접 입력", value: "DIRECT" },
];

export default function FloorTypeField({
  floorType,
  floorValue,
  onChangeFloorType,
  onChangeFloorValue,
  errorMessage,
  title,
}) {
  const isDirect = floorType === "DIRECT";

  return (
    <section className="space-y-3">
      <FieldTitle main={title.main} sub={title.sub} />

      <div className="grid grid-cols-2 gap-2 sm:grid-cols-3">
        {FLOOR_OPTIONS.map((option) => {
          const active = floorType === option.value;

          return (
            <button
              key={option.value}
              type="button"
              onClick={() => onChangeFloorType(option.value)}
              className={`rounded-full border px-4 py-3 text-sm font-semibold transition ${
                active
                  ? "border-slate-950 bg-slate-950 text-white"
                  : "border-slate-200 bg-white text-slate-700 hover:border-slate-300 hover:bg-slate-50"
              }`}
            >
              {option.label}
            </button>
          );
        })}
      </div>

      {isDirect ? (
        <div className="rounded-[1.25rem] border border-slate-200 bg-white p-4">
          <NumberInput
            label="직접 입력 층수"
            value={floorValue}
            onChange={onChangeFloorValue}
            placeholder="12"
            maxValue={999}
          />
          {errorMessage ? (
            <p className="mt-2 text-xs font-medium text-red-600">{errorMessage}</p>
          ) : null}
        </div>
      ) : null}
    </section>
  );
}
