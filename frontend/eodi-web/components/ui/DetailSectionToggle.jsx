"use client";

import { ChevronDown, ChevronUp } from "lucide-react";

export default function DetailSectionToggle({ isOpen, onToggle }) {
  return (
    <button
      type="button"
      onClick={onToggle}
      className="flex w-full items-center justify-between rounded-[1.25rem] border border-slate-200 bg-white px-4 py-4 text-left text-sm font-semibold text-slate-700 transition hover:border-slate-300 hover:bg-slate-50"
    >
      <span>{isOpen ? "상세 기록 닫기" : "상세 기록 더 입력하기"}</span>
      {isOpen ? <ChevronUp className="h-4 w-4" /> : <ChevronDown className="h-4 w-4" />}
    </button>
  );
}

