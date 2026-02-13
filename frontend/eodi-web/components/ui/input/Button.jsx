export default function Button({
  children,
  onClick,
  type = "button",
  variant = "primary",
  size = "md",
  fullWidth = false,
  disabled = false,
  className = "",
}) {
  const base =
    "inline-flex items-center justify-center rounded-md font-medium transition focus:outline-none";

  const variants = {
    primary: "bg-primary text-white hover:bg-primary/90",
    secondary: "bg-gray-100 text-gray-800 hover:bg-gray-200",
    ghost: "bg-transparent hover:bg-gray-100 text-gray-800",
    danger: "bg-red-600 text-white hover:bg-red-700",
  };

  const sizes = {
    sm: "px-3 py-1.5 text-sm",
    md: "px-4 py-2 text-sm",
    lg: "px-5 py-2.5 text-base",
  };

  return (
    <button
      type={type}
      onClick={onClick}
      disabled={disabled}
      className={`
        ${base}
        ${variants[variant]}
        ${sizes[size]}
        ${fullWidth ? "w-full" : ""}
        ${disabled ? "opacity-50 cursor-not-allowed pointer-events-none" : ""}
        ${className}
      `}
    >
      {children}
    </button>
  );
}
