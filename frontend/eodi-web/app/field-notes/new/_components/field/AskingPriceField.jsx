"use client";

import NumberInput from "@/components/ui/NumberInput";
import { formatWon } from "@/app/search/_util/util";
import FieldTitle from "@/app/field-notes/new/_components/field/FieldTitle";

export default function AskingPriceField({ askingPrice, onChangeAskingPrice, title }) {
  const hasValue = askingPrice !== "";

  return (
    <section className="space-y-3">
      <FieldTitle main={title.main} sub={title.sub} />
      <NumberInput
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
