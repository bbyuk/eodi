"use client";

export default function MemoField({ memo, onChangeMemo, maxLength = 500 }) {
  return (
    <section className="space-y-3">
      <div className="flex items-end justify-between gap-3">
        <label className="text-sm font-semibold text-slate-900">메모</label>
        <span className="text-xs font-medium text-slate-400">{memo.length}/{maxLength}</span>
      </div>
      <textarea
        value={memo}
        onChange={(event) => onChangeMemo(event.target.value.slice(0, maxLength))}
        placeholder="기억해둘 내용을 적어보세요"
        className="min-h-32 w-full rounded-[1.25rem] border border-slate-200 bg-white px-4 py-4 text-sm text-slate-900 placeholder:text-slate-400 focus:outline-none"
      />
    </section>
  );
}

