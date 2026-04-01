import Link from "next/link";

export default function TextActionLink({ href, className = "", children, ...props }) {
  const computedClassName = [
    "inline-flex items-center gap-2 text-sm font-semibold text-slate-700 transition hover:text-slate-950",
    className,
  ]
    .filter(Boolean)
    .join(" ");

  return (
    <Link href={href} className={computedClassName} {...props}>
      {children}
    </Link>
  );
}

