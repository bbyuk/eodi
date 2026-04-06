"use client";

import Field from "@/app/field-notes/new/_components/field/Field";
import StarRatingInput from "@/app/field-notes/new/_components/input/StarRatingInput";

export default function StarRatingField({
  title,
  value,
  onChange,
  max,
  minLabel,
  maxLabel,
  scoreLabels,
}) {
  return (
    <Field title={title}>
      <StarRatingInput
        value={value}
        onChange={onChange}
        max={max}
        minLabel={minLabel}
        maxLabel={maxLabel}
        scoreLabels={scoreLabels}
      />
    </Field>
  );
}
