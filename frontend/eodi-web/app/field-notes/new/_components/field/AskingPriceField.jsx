"use client";

import { formatWon } from "@/app/search/_util/util";
import Field from "@/app/field-notes/new/_components/field/Field";
import NumberFieldInput from "@/app/field-notes/new/_components/input/NumberFieldInput";

export default function AskingPriceField({
  askingPrice,
  onChangeAskingPrice,
  title,
  placeholder = "50000",
}) {
  const hasValue = askingPrice !== "";

  return (
    <Field title={title}>
      <NumberFieldInput
        value={askingPrice}
        onChange={onChangeAskingPrice}
        placeholder={placeholder}
        unit="만원"
        minValue={1}
        maxValue={999999999}
      />
      <p
        className={`min-h-4 text-right text-xs font-medium leading-4 ${hasValue ? "text-slate-600" : "text-slate-400"}`}
      >
        {hasValue ? formatWon(Number(askingPrice)) : "\u00a0"}
      </p>
    </Field>
  );
}
