"use client";

import Field from "@/app/field-notes/new/_components/field/Field";
import TextAreaInput from "@/app/field-notes/new/_components/input/TextAreaInput";

export default function TextAreaField({
  title = { main: "메모" },
  value,
  onChange,
  maxLength = 500,
  placeholder = "기억해둘 내용을 적어보세요",
  showCount = true,
}) {
  return (
    <Field
      title={title}
      preserveSubSpace={false}
      titleAside={
        showCount ? (
          <span className="text-xs font-medium text-slate-400">
            {value.length}/{maxLength}
          </span>
        ) : null
      }
    >
      <TextAreaInput
        value={value}
        onChange={(event) => onChange(event.target.value.slice(0, maxLength))}
        placeholder={placeholder}
      />
    </Field>
  );
}
