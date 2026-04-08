"use client";

import { ChevronDown, ChevronUp } from "lucide-react";
import FormTitle from "@/app/field-notes/new/_components/field/FormTitle";
import FieldNoteSection from "@/app/field-notes/new/_components/section/FieldNoteSection";

export default function CollapsibleFormSection({
  title,
  headerContent = null,
  isOpen = false,
  onToggle,
  toggleOnHeader = true,
  headerAction = null,
  headerActionPlacement = "afterToggle",
  children,
  className = "bg-white shadow-[0_18px_40px_rgba(15,23,42,0.04)]",
}) {
  const actionElement = headerAction ? <div className="shrink-0">{headerAction}</div> : null;
  const chevronIcon = isOpen ? (
    <ChevronUp className="h-4 w-4" />
  ) : (
    <ChevronDown className="h-4 w-4" />
  );
  const chevronClassName =
    "inline-flex h-8 w-8 shrink-0 items-center justify-center rounded-full border border-slate-200 bg-slate-50 text-slate-500 transition";
  const resolvedHeaderContent = headerContent ?? (
    <FormTitle main={title} preserveSubSpace={false} />
  );

  return (
    <FieldNoteSection className={className}>
      <div className="flex items-center gap-2">
        {headerActionPlacement === "beforeToggle" ? (
          <>
            {toggleOnHeader ? (
              <button
                type="button"
                onClick={onToggle}
                aria-expanded={isOpen}
                className="min-w-0 flex-1 text-left"
              >
                {resolvedHeaderContent}
              </button>
            ) : (
              <div className="min-w-0 flex-1">{resolvedHeaderContent}</div>
            )}
            {actionElement}
            <button
              type="button"
              onClick={onToggle}
              aria-expanded={isOpen}
              aria-label={`${title} 펼치기`}
              className={chevronClassName}
            >
              {chevronIcon}
            </button>
          </>
        ) : (
          <>
            <button
              type="button"
              onClick={onToggle}
              aria-expanded={isOpen}
              className="flex min-w-0 flex-1 items-center justify-between gap-4 text-left"
            >
              {resolvedHeaderContent}
              <span className={chevronClassName}>{chevronIcon}</span>
            </button>
            {actionElement}
          </>
        )}
      </div>

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
