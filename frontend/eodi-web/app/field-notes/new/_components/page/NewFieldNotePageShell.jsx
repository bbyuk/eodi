"use client";

import { useMemo } from "react";
import { usePathname, useRouter } from "next/navigation";
import FormHeader from "@/components/layout/FormHeader";
import Select from "@/components/ui/Select";
import FieldNoteForm from "@/app/field-notes/new/_components/form/FieldNoteForm";
import { RECORD_TYPE_OPTIONS } from "@/app/field-notes/new/_data/fieldNoteOptions";

export default function NewFieldNotePageShell({ children }) {
  const pathname = usePathname();
  const router = useRouter();

  const recordType = pathname?.startsWith("/field-notes/new/region") ? "region" : "complex";

  const currentRecordType = useMemo(
    () =>
      RECORD_TYPE_OPTIONS.find((option) => option.value === recordType) ?? RECORD_TYPE_OPTIONS[0],
    [recordType]
  );

  const handleChangeRecordType = (value) => {
    const nextOption = RECORD_TYPE_OPTIONS.find((option) => option.value === value);

    if (!nextOption || nextOption.value === recordType) {
      return;
    }

    router.push(nextOption.href);
  };

  return (
    <div className="mx-auto flex w-full max-w-3xl flex-col gap-8 px-4 pb-16 pt-24 sm:px-6 sm:pt-28 lg:px-8 lg:pt-32">
      <FormHeader value="무엇을 기록할까요?" />

      <FieldNoteForm>
        <div className="space-y-2">
          <div className="flex items-center justify-between gap-3">
            <p className="text-sm font-semibold text-slate-900">기록 유형</p>
            <p className="text-xs font-medium text-slate-500">{currentRecordType.description}</p>
          </div>

          <Select
            options={RECORD_TYPE_OPTIONS}
            value={recordType}
            onChange={handleChangeRecordType}
            width="w-full"
          />
        </div>

        {children}
      </FieldNoteForm>
    </div>
  );
}
