"use client";

export default function SelectedComplexCard({ complexName, regionLabel }) {
  return (
    <section className="rounded-[1.5rem] border border-emerald-200 bg-[linear-gradient(135deg,#ecfdf5_0%,#f8fafc_100%)] p-4 shadow-[0_16px_40px_rgba(16,185,129,0.08)]">
      <p className="text-xs font-semibold tracking-wide text-emerald-700">선택된 단지</p>
      <h2 className="mt-2 text-xl font-semibold tracking-tight text-slate-950">{complexName}</h2>
      <p className="mt-2 text-sm leading-6 text-slate-600">{regionLabel}</p>
    </section>
  );
}

