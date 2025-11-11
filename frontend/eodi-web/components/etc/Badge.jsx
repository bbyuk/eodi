"use client";

export default function Badge({ count = 0, className = "" }) {
  if (!count || count <= 0) return null;

  return (
    <span
      className={`absolute -top-1 -right-1 
        flex items-center justify-center 
        text-[10px] font-semibold text-white bg-blue-600 
        rounded-full min-w-[16px] h-[16px] px-[4px] 
        shadow-sm ${className}`}
    >
      {count}
    </span>
  );
}
