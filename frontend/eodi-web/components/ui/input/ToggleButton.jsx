// components/ui/input/ToggleButton.jsx
export default function ToggleButton({
  label,
  isActive = false,
  size = "sm",
  className = "",
  useBadge,
  badgeCount = 0,
  ...props
}) {
  const sizeClasses = {
    sm: "px-2 py-2 text-sm rounded-lg",
    md: "px-4 py-3 text-base rounded-lg",
    lg: "px-6 py-4 text-lg rounded-xl",
  };

  return (
    <button
      type="button"
      className={`
        relative border transition text-center flex items-center justify-center
        ${sizeClasses[size] ?? sizeClasses.sm}
        ${
          isActive
            ? "border-primary bg-primary text-white"
            : "border-border hover:bg-primary-bg hover:border-primary-light hover:text-primary"
        }
        ${className}
      `}
      {...props}
    >
      <span>{label}</span>
      {useBadge && badgeCount >= 20 && (
        <span
          className={`absolute top-1.5 right-2 text-[11px] ${isActive ? "text-white/80" : "text-primary"}`}
        >
          {badgeCount}건
        </span>
      )}
    </button>
  );
}
