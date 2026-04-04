"use client";

import DetailStatusFields from "./DetailStatusFields";

export default function DetailRecordFields({ form, onChangeField, children }) {
  return (
    <section className="rounded-[1.5rem] border border-slate-200 bg-white p-4 shadow-[0_18px_40px_rgba(15,23,42,0.04)]">
      <div className="space-y-1">
        <p className="text-sm font-semibold text-slate-900">상세 기록</p>
        <p className="text-xs font-medium text-slate-500">
          더 꼼꼼하게 비교할 수 있도록 추가 정보를 남겨보세요
        </p>
      </div>

      <div className="mt-5 space-y-5">
        <section className="space-y-3">
          <DetailStatusFields form={form} onChangeField={onChangeField} />
          <label className="text-sm font-semibold text-slate-900">부동산명</label>
          <input
            type="text"
            value={form.agencyName}
            onChange={(event) => onChangeField("agencyName", event.target.value)}
            placeholder="공인중개사"
            className="min-h-14 w-full rounded-[1.25rem] border border-slate-200 bg-white px-4 text-sm text-slate-900 placeholder:text-slate-400 focus:outline-none"
          />
        </section>

        {children}
      </div>
    </section>
  );
}
