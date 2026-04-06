"use client";

import Field from "@/app/field-notes/new/_components/field/Field";
import CompassDirectionInput from "@/app/field-notes/new/_components/input/CompassDirectionInput";

export default function FacingField({ title, value, options, onChange }) {
  return (
    <Field title={title}>
      <CompassDirectionInput value={value} options={options} onChange={onChange} />
    </Field>
  );
}
