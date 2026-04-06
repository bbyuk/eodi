"use client";

import { ChevronDown, ChevronUp } from "lucide-react";
import FormTitle from "@/app/field-notes/new/_components/field/FormTitle";
import FieldNoteSection from "@/app/field-notes/new/_components/section/FieldNoteSection";

export default function CollapsibleFormSection({
  title,
  description = "",
  isOpen = false,
  onToggle,
  children,
  className = "bg-white shadow-[0_18px_40px_rgba(15,23,42,0.04)]",
}) {
  return (
    <FieldNoteSection className={className}>
      <button
        type="button"
        onClick={onToggle}
        aria-expanded={isOpen}
        className="flex w-full items-start justify-between gap-4 text-left"
      >
        <FormTitle main={title} sub={description} preserveSubSpace={Boolean(description)} />
        <span className="mt-0.5 inline-flex h-8 w-8 shrink-0 items-center justify-center rounded-full border border-slate-200 bg-slate-50 text-slate-500 transition">
          {isOpen ? <ChevronUp className="h-4 w-4" /> : <ChevronDown className="h-4 w-4" />}
        </span>
      </button>

      <div
        className={`grid transition-[grid-template-rows,opacity,margin] duration-200 ${
          isOpen ? "mt-5 grid-rows-[1fr] opacity-100" : "mt-0 grid-rows-[0fr] opacity-0"
        }`}
      >
        <div className="min-h-0 overflow-hidden">
          <div className="space-y-5">{children}</div>
        </div>
      </div>
    </FieldNoteSection>
  );
}
