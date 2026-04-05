"use client";

import Field from "@/app/field-notes/new/_components/field/Field";
import ButtonSelectInput from "@/app/field-notes/new/_components/input/ButtonSelectInput";

export default function ButtonInputField({ title, value, placeholder, onClick }) {
  return (
    <Field title={title}>
      <ButtonSelectInput value={value} placeholder={placeholder} onClick={onClick} />
    </Field>
  );
}
