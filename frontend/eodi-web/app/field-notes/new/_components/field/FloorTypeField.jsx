"use client";

import Field from "@/app/field-notes/new/_components/field/Field";
import ChoiceChipInput from "@/app/field-notes/new/_components/input/ChoiceChipInput";
import NumberFieldInput from "@/app/field-notes/new/_components/input/NumberFieldInput";

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
    <Field title={title}>
      <ChoiceChipInput value={floorType} options={FLOOR_OPTIONS} onChange={onChangeFloorType} />
      {isDirect ? (
        <div className="rounded-[1.25rem] border border-slate-200 bg-white p-4">
          <NumberFieldInput
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
    </Field>
  );
}
