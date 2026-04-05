"use client";

import OptionButton from "@/components/ui/OptionButton";

export default function OptionGridInput({ value, options, onChange }) {
  return (
    <div className="grid grid-cols-3 gap-2">
      {options.map((option) => {
        const active = value === option.value;

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
  );
}
