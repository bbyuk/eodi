"use client";

export default function RegionInfo({ label = "지역", value }) {
  return (
    <section className="rounded-[1.5rem] border border-slate-200 bg-slate-50 p-4">
      <p className="text-xs font-semibold tracking-wide text-slate-500">{label}</p>
      <p className="mt-2 text-base font-semibold text-slate-900">{value}</p>
    </section>
  );
}

