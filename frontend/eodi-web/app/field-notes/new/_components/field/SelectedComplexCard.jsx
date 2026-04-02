"use client";

import { CheckCircle2, MapPin } from "lucide-react";

export default function SelectedComplexCard({
  complexName,
  address,
  regionLabel,
  helperText,
  actionLabel = "다시 선택",
  onAction,
}) {
  return (
    <section className="rounded-[1.5rem] border border-emerald-200 bg-[linear-gradient(135deg,#ecfdf5_0%,#f8fafc_100%)] p-4 shadow-[0_16px_40px_rgba(16,185,129,0.08)]">
      <div className="flex items-start justify-between gap-3">
        <div className="flex items-start gap-2">
          <CheckCircle2 className="mt-0.5 h-5 w-5 shrink-0 text-emerald-600" />
          <div>
            <p className="text-xs font-semibold tracking-wide text-emerald-700">선택된 단지</p>
            <h2 className="mt-1 text-lg font-semibold tracking-tight text-slate-950">{complexName}</h2>
          </div>
        </div>

        {onAction ? (
          <button
            type="button"
            onClick={onAction}
            className="rounded-full border border-emerald-200 bg-white/80 px-3 py-1.5 text-xs font-semibold text-emerald-700 transition hover:bg-white"
          >
            {actionLabel}
          </button>
        ) : null}
      </div>

      <div className="mt-4 space-y-3 rounded-[1.25rem] bg-white/70 p-3">
        {address ? (
          <div className="space-y-1">
            <p className="text-[11px] font-semibold uppercase tracking-[0.16em] text-slate-500">
              Address
            </p>
            <p className="text-sm font-medium leading-6 text-slate-700">{address}</p>
          </div>
        ) : null}

        {regionLabel ? (
          <div className="flex items-start gap-2 text-sm text-slate-700">
            <MapPin className="mt-0.5 h-4 w-4 shrink-0 text-slate-400" />
            <div className="space-y-1">
              <p className="text-[11px] font-semibold uppercase tracking-[0.16em] text-slate-500">
                Region
              </p>
              <p className="font-medium">{regionLabel}</p>
            </div>
          </div>
        ) : null}
      </div>

      {helperText ? (
        <p className="mt-3 text-xs font-medium text-emerald-700/80">{helperText}</p>
      ) : null}
    </section>
  );
}
