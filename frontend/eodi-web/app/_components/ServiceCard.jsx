import Link from "next/link";
import { ArrowUpRight, Smartphone, Monitor } from "lucide-react";

export default function ServiceCard({ item }) {
  return (
    <article className="group flex h-full flex-col rounded-[1.75rem] border border-slate-200 bg-white p-5 shadow-[0_18px_50px_rgba(15,23,42,0.05)] transition duration-200 hover:-translate-y-1 hover:shadow-[0_24px_60px_rgba(15,23,42,0.08)] sm:p-6">
      <div
        className={`mb-5 h-2 w-24 rounded-full bg-gradient-to-r ${item.accent ?? "from-slate-800 to-slate-500"}`}
      />

      <div className="flex items-start justify-between gap-4">
        <div>
          <h3 className="text-xl font-semibold text-slate-950">{item.name}</h3>
          <p className="mt-3 text-sm leading-6 text-slate-600">{item.summary}</p>
        </div>
        <span
          className={`shrink-0 rounded-full px-3 py-1 text-xs font-semibold ${
            item.available ? "bg-blue-50 text-blue-700" : "bg-slate-100 text-slate-600"
          }`}
        >
          {item.status}
        </span>
      </div>

      <p className="mt-4 text-sm leading-6 text-slate-600">{item.description}</p>

      <div className="mt-5 flex flex-wrap gap-2">
        {item.platforms?.includes("웹") ? (
          <span className="inline-flex items-center gap-1 rounded-full border border-slate-200 bg-slate-50 px-3 py-1 text-xs font-medium text-slate-600">
            <Monitor className="h-3.5 w-3.5" />
            웹
          </span>
        ) : null}
        {item.platforms?.includes("모바일") ? (
          <span className="inline-flex items-center gap-1 rounded-full border border-slate-200 bg-slate-50 px-3 py-1 text-xs font-medium text-slate-600">
            <Smartphone className="h-3.5 w-3.5" />
            모바일
          </span>
        ) : null}
      </div>

      <div className="mt-6 space-y-2 rounded-2xl bg-slate-50 px-4 py-4">
        {item.points?.map((point) => (
          <div key={point} className="flex items-center gap-3 text-sm text-slate-700">
            <span className="h-2 w-2 rounded-full bg-slate-400" />
            <span>{point}</span>
          </div>
        ))}
      </div>

      <div className="mt-6 pt-2">
        <Link
          href={item.href}
          className="inline-flex items-center gap-2 text-sm font-semibold text-slate-950 transition group-hover:text-blue-700"
        >
          {item.ctaLabel}
          <ArrowUpRight className="h-4 w-4" />
        </Link>
      </div>
    </article>
  );
}
