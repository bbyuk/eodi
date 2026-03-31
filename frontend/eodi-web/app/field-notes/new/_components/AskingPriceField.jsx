"use client";

import NumberInput from "@/components/ui/input/NumberInput";
import { formatWon } from "@/app/search/_util/util";

export default function AskingPriceField({ askingPrice, onChangeAskingPrice }) {
  const hasValue = askingPrice !== "";

  return (
    <section className="space-y-3">
      <NumberInput
        label="호가"
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
    </section>
  );
}
