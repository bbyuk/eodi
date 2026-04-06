"use client";

import Field from "@/app/field-notes/new/_components/field/Field";
import FloorLevelSelectInput from "@/app/field-notes/new/_components/input/FloorLevelSelectInput";
import NumberFieldInput from "@/app/field-notes/new/_components/input/NumberFieldInput";

const FLOOR_OPTIONS = [
  { label: "저층", value: "LOW", highlightedFloors: [1] },
  { label: "중층", value: "MID", highlightedFloors: [2] },
  { label: "고층", value: "HIGH", highlightedFloors: [3] },
  { label: "탑층", value: "TOP", highlightedFloors: [4] },
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
  const cardOptions = FLOOR_OPTIONS.filter((option) => option.value !== "DIRECT");
  const directOption = FLOOR_OPTIONS.find((option) => option.value === "DIRECT");

  return (
    <Field title={title}>
      <FloorLevelSelectInput
        value={floorType}
        options={cardOptions}
        directOption={directOption}
        onChange={onChangeFloorType}
      />
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
