"use client";

import TextInputField from "@/app/field-notes/new/_components/field/TextInputField";
import OptionField from "@/app/field-notes/new/_components/field/OptionField";

export default function DetailRecordFields({ form, onChangeField, children }) {
  const STATUS_OPTIONS = [
    { label: "좋음", value: "GOOD" },
    { label: "보통", value: "NORMAL" },
    { label: "아쉬움", value: "BAD" },
  ];

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
          <OptionField
            title={{ main: "주차" }}
            value={form.parkingStatus}
            options={STATUS_OPTIONS}
            onChange={(value) => onChangeField("parkingStatus", value)}
          />
          <OptionField
            title={{ main: "채광" }}
            value={form.sunlightStatus}
            options={STATUS_OPTIONS}
            onChange={(value) => onChangeField("sunlightStatus", value)}
          />
          <OptionField
            title={{ main: "상권" }}
            value={form.commercialAreaStatus}
            options={STATUS_OPTIONS}
            onChange={(value) => onChangeField("commercialAreaStatus", value)}
          />
          <TextInputField
            title={{ main: "부동산명" }}
            value={form.agencyName}
            onChange={(event) => onChangeField("agencyName", event.target.value)}
            placeholder={"공인중개사"}
          />
        </section>

        {children}
      </div>
    </section>
  );
}
