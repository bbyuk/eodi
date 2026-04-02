"use client";

export default function SelectedComplexCard({ complexName }) {
  return (
    <section className="rounded-[1.5rem] border border-slate-200 bg-slate-50 px-5 py-4 shadow-[0_18px_40px_rgba(15,23,42,0.04)]">
      <p className="text-xs font-semibold tracking-wide text-slate-500">선택한 단지</p>
      <h2 className="mt-2 text-lg font-semibold tracking-tight text-slate-950">{complexName}</h2>
    </section>
  );
}
