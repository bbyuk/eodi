"use client";

import Field from "@/app/field-notes/new/_components/field/Field";
import OptionGridInput from "@/app/field-notes/new/_components/input/OptionGridInput";

export default function OptionField({ value, options, onChange, title }) {
  return (
    <Field title={title}>
      <OptionGridInput value={value} options={options} onChange={onChange} />
    </Field>
  );
}
