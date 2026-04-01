"use client";

export default function RadioGroup({
  name,
  value,
  onChange,
  options = [],
  direction = "row",
  size = "md",
  className = "",
}) {
  const sizeClassMap = {
    sm: {
      wrapper: "gap-2",
      item: "h-9 px-4 text-sm rounded-full",
    },
    md: {
      wrapper: "gap-2.5",
      item: "h-11 px-5 text-base rounded-full",
    },
  };

  const directionClassMap = {
    row: "flex flex-wrap items-center",
    col: "flex flex-col",
  };

  const sizeClass = sizeClassMap[size] ?? sizeClassMap.md;
  const directionClass = directionClassMap[direction] ?? directionClassMap.row;

  return (
    <div role="radiogroup" className={`${directionClass} ${sizeClass.wrapper} ${className}`}>
      {options.map((option) => {
        const checked = value === option.value;
        const id = `${name}-${String(option.value)}`;

        return (
          <label
            key={option.value}
            htmlFor={id}
            className={[
              "relative inline-flex cursor-pointer items-center justify-center",
              "border transition-all duration-150 select-none",
              "font-medium",
              sizeClass.item,
              checked
                ? "border-blue-600 bg-blue-50 text-blue-600"
                : "border-slate-200 bg-white text-slate-700 hover:border-slate-300 hover:bg-slate-50",
              option.disabled ? "cursor-not-allowed opacity-50" : "",
            ].join(" ")}
          >
            <input
              id={id}
              type="radio"
              name={name}
              value={option.value}
              checked={checked}
              disabled={option.disabled}
              onChange={() => onChange?.(option.value)}
              className="sr-only"
            />

            <span>{option.label}</span>
          </label>
        );
      })}
    </div>
  );
}
