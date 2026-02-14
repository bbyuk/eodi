export default function Button({
  as = "button", // 🔥 기본은 button
  children,
  onClick,
  href,
  type = "button",
  variant = "primary",
  size = "md",
  fullWidth = false,
  disabled = false,
  className = "",
  ...props
}) {
  const Component = as;

  const base =
    "inline-flex items-center justify-center rounded-md font-medium transition-colors focus:outline-none";

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

  const disabledStyle = "opacity-50 cursor-not-allowed pointer-events-none";

  const computedClassName = `
    ${base}
    ${variants[variant]}
    ${sizes[size]}
    ${fullWidth ? "w-full" : ""}
    ${disabled ? disabledStyle : ""}
    ${className}
  `;

  // 🔥 a 태그일 때 disabled 클릭 방지 처리
  const handleClick = (e) => {
    if (disabled) {
      e.preventDefault();
      return;
    }
    onClick?.(e);
  };

  return (
    <Component
      type={as === "button" ? type : undefined}
      href={as === "a" ? href : undefined}
      onClick={handleClick}
      disabled={as === "button" ? disabled : undefined}
      className={computedClassName}
      {...props}
    >
      {children}
    </Component>
  );
}
