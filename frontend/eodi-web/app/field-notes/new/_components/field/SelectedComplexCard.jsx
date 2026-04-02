"use client";

import { CheckCircle2, MapPin } from "lucide-react";

export default function SelectedComplexCard({
  complexName,
  address,
  regionLabel,
  helperText,
  statusLabel = "선택 완료",
  regionTagLabel = "자동 입력",
  actionLabel = "단지 다시 선택",
  regionActionLabel = "지역 변경",
  onAction,
  onChangeRegion,
}) {
  return (
    <section className="rounded-[1.5rem] border border-emerald-200 bg-[linear-gradient(135deg,#ecfdf5_0%,#f8fafc_100%)] p-4 shadow-[0_16px_40px_rgba(16,185,129,0.08)]">
      <div className="flex items-start justify-between gap-3">
        <div className="flex items-start gap-2">
          <CheckCircle2 className="mt-0.5 h-5 w-5 shrink-0 text-emerald-600" />
          <div>
            <p className="text-xs font-semibold tracking-wide text-emerald-700">{statusLabel}</p>
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
              주소
            </p>
            <p className="text-sm font-medium leading-6 text-slate-700">{address}</p>
          </div>
        ) : null}

        {regionLabel ? (
          <div className="flex items-start justify-between gap-3 rounded-[1rem] border border-white/80 bg-white/80 px-3 py-3">
            <div className="flex items-start gap-2 text-sm text-slate-700">
              <MapPin className="mt-0.5 h-4 w-4 shrink-0 text-slate-400" />
              <div className="space-y-1">
                <p className="text-[11px] font-semibold uppercase tracking-[0.16em] text-slate-500">
                  지역
                </p>
                <div className="flex items-center gap-2">
                  <p className="font-medium">{regionLabel}</p>
                  <span className="rounded-full bg-emerald-100 px-2 py-0.5 text-[11px] font-semibold text-emerald-700">
                    {regionTagLabel}
                  </span>
                </div>
              </div>
            </div>

            {onChangeRegion ? (
              <button
                type="button"
                onClick={onChangeRegion}
                className="rounded-full border border-slate-200 bg-white px-3 py-1.5 text-xs font-semibold text-slate-700 transition hover:border-slate-300 hover:bg-slate-50"
              >
                {regionActionLabel}
              </button>
            ) : null}
          </div>
        ) : null}
      </div>

      {helperText ? (
        <p className="mt-3 text-xs font-medium text-emerald-700/80">{helperText}</p>
      ) : null}
    </section>
  );
}
