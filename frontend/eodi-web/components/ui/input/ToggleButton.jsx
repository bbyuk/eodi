// components/ui/input/ToggleButton.jsx
export default function ToggleButton({
  label,
  isActive = false,
  size = "sm",
  className = "",
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
        border transition text-center
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
      {label}
    </button>
  );
}
