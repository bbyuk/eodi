"use client";

import { formatWon } from "@/app/search/_util/util";
import Field from "@/app/field-notes/new/_components/field/Field";
import NumberFieldInput from "@/app/field-notes/new/_components/input/NumberFieldInput";

export default function AskingPriceField({ askingPrice, onChangeAskingPrice, title }) {
  const hasValue = askingPrice !== "";

  return (
    <Field title={title}>
      <NumberFieldInput
        value={askingPrice}
        onChange={onChangeAskingPrice}
        placeholder="50000"
        unit="만원"
        minValue={1}
        maxValue={999999999}
      />
      <p
        className={`text-right text-xs font-medium ${hasValue ? "text-slate-600" : "text-slate-400"}`}
      >
        {hasValue ? formatWon(Number(askingPrice)) : "숫자만 입력"}
      </p>
    </Field>
  );
}
