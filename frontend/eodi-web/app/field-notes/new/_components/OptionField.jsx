"use client";

import FieldTitle from "@/app/field-notes/new/_components/FieldTitle";
import OptionButton from "@/app/field-notes/new/_components/OptionButton";

export default function OptionField({ value, options, onChange, title }) {
  return (
    <section className="space-y-3">
      <FieldTitle main={title.main} sub={title.sub} />
      <div className="grid grid-cols-3 gap-2">
        {options.map((option) => {
          const active = value === option.value;
          s;
          return (
            <OptionButton
              key={option.value}
              onClick={() => onChange(option.value)}
              active={active}
              label={option.label}
            />
          );
        })}
      </div>
    </section>
  );
}
