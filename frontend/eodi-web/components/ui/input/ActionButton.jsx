import Link from "next/link";

export default function ActionButton({
  href,
  type = "button",
  variant = "dark",
  size = "md",
  rounded = "full",
  block = false,
  className = "",
  children,
  ...props
}) {
  const Component = href ? Link : "button";

  const base = "inline-flex items-center justify-center gap-2 transition";

  const variants = {
    dark: "bg-slate-950 text-white hover:bg-slate-800",
    frosted: "border border-slate-300 bg-white/80 text-slate-700 hover:border-slate-400 hover:bg-white",
    outline: "border border-slate-300 bg-white text-slate-700 hover:border-slate-400 hover:bg-slate-50",
  };

  const sizes = {
    md: "px-5 py-3 text-sm font-semibold",
    sm: "px-4 py-2 text-sm font-semibold",
    xs: "px-3 py-2 text-xs font-semibold",
  };

  const radii = {
    full: "rounded-full",
    xl: "rounded-2xl",
  };

  const computedClassName = [
    base,
    variants[variant] ?? variants.dark,
    sizes[size] ?? sizes.md,
    radii[rounded] ?? radii.full,
    block ? "w-full" : "",
    className,
  ]
    .filter(Boolean)
    .join(" ");

  return (
    <Component
      href={href}
      type={href ? undefined : type}
      className={computedClassName}
      {...props}
    >
      {children}
    </Component>
  );
}

