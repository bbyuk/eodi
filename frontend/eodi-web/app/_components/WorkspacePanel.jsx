import Link from "next/link";

export default function WorkspacePanel({
  title,
  description,
  actionLabel,
  actionHref,
  children,
}) {
  return (
    <section className="rounded-[1.75rem] border border-slate-200 bg-white p-5 shadow-[0_18px_50px_rgba(15,23,42,0.05)] sm:p-6">
      <div className="flex items-start justify-between gap-4">
        <div className="space-y-1">
          <h2 className="text-xl font-semibold text-slate-950">{title}</h2>
          {description ? <p className="text-sm leading-6 text-slate-600">{description}</p> : null}
        </div>
        {actionLabel && actionHref ? (
          <Link
            href={actionHref}
            className="shrink-0 rounded-full border border-slate-300 bg-white px-3 py-2 text-xs font-semibold text-slate-700 transition hover:border-slate-400 hover:bg-slate-50"
          >
            {actionLabel}
          </Link>
        ) : null}
      </div>

      <div className="mt-6">{children}</div>
    </section>
  );
}

