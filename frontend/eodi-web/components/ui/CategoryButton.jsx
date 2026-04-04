import { cloneElement, useState } from "react";
import Badge from "@/components/etc/Badge";

export default function CategoryButton({
  isActive,
  label,
  icon,
  useBadge = false,
  count = 0,
  iconClassName,
  ...props
}) {
  return (
    <button
      type="button"
      className={`relative px-4 py-2 rounded-full border text-sm font-medium transition whitespace-nowrap ${
        isActive
          ? "bg-blue-50 text-blue-700 border-blue-300"
          : "bg-white text-slate-700 border-slate-300 hover:border-blue-300 hover:text-blue-600"
      }`}
      {...props}
    >
      {icon && (
        <span className="inline-flex items-center justify-center align-middle translate-y-[-5px] translate-x-[-7px]">
          <span className="inline-block w-[15px] h-[15px] text-current">
            {icon &&
              cloneElement(icon, {
                className: `${icon.props.className || ""} ${iconClassName || ""}`,
              })}
          </span>
        </span>
      )}
      <span className="leading-none">{label}</span>
      {useBadge && count > 0 && <Badge count={count} />}
    </button>
  );
}
