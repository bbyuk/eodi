"use client";

import NumberInput from "@/components/ui/input/NumberInput";
import { formatWon } from "@/app/search/_util/util";

export default function AskingPriceField({ askingPrice, onChangeAskingPrice }) {
  return (
    <section className="space-y-3">
      <NumberInput
        label="호가"
        value={askingPrice}
        onChange={onChangeAskingPrice}
        placeholder="예: 85000"
        formatter={(value) => (value ? formatWon(Number(value)) : "")}
        maxValue={999999999}
      />
    </section>
  );
}

