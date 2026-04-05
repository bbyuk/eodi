"use client";

import { ChevronRight, Search } from "lucide-react";
import FieldTitle from "@/app/field-notes/new/_components/field/FieldTitle";

export default function ButtonInputField({ title, value, placeholder, onClick }) {
  return (
    <section className="space-y-3">
      <FieldTitle main={title.main} sub={title.sub} />

      <button
        type="button"
        onClick={onClick}
        className="flex min-h-14 w-full items-center gap-3 rounded-[1.25rem] border border-slate-200 bg-white px-4 text-left transition hover:border-slate-300 hover:bg-slate-50"
      >
        <Search className="h-4 w-4 shrink-0 text-slate-400" />
        <span
          className={`min-w-0 flex-1 truncate text-sm font-medium ${value ? "text-slate-900" : "text-slate-400"}`}
        >
          {value || placeholder}
        </span>
        <ChevronRight className="h-4 w-4 shrink-0 text-slate-400" />
      </button>
    </section>
  );
}
