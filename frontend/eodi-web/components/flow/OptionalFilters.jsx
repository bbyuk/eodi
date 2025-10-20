/**
 * Step 3 - 맞춤 조건 설정
 */
"use client";

import { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { ChevronDown } from "lucide-react";

export default function OptionalFilters({ sellRegions, leaseRegions, onBack, onApply }) {
  const [sellFilters, setSellFilters] = useState({ minArea: "", maxArea: "" });
  const [leaseFilters, setLeaseFilters] = useState({
    minArea: "",
    maxArea: "",
    rentMin: "",
    rentMax: "",
  });

  const handleChange = (setFn) => (key, value) => setFn((prev) => ({ ...prev, [key]: value }));

  const hasSell = sellRegions?.size > 0;
  const hasLease = leaseRegions?.size > 0;

  return (
    <motion.section
      key="optional-filters"
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      exit={{ opacity: 0, y: -20 }}
      transition={{ duration: 0.25 }}
      className="max-w-5xl mx-auto px-6 pt-[1vh] pb-[5vh]"
    >
      {/* Header */}
      <header className="mb-14">
        <h1 className="text-3xl md:text-4xl font-semibold text-text-primary mb-3 leading-tight">
          맞춤 조건 설정
        </h1>
        <p className="text-base text-text-secondary leading-relaxed">
          매매와 전·월세 각각에 적용할 조건을 설정할 수 있어요.
          <br className="hidden sm:block" />
          면적, 월세 등 항목은 모두 선택사항이에요.
        </p>
      </header>

      <div className="space-y-14">
        {hasSell && (
          <FilterGroup
            title="매매 조건"
            subtitle="매매 가능한 지역"
            regions={sellRegions}
            filters={sellFilters}
            onChange={handleChange(setSellFilters)}
            optional={[
              {
                title: "면적 선택 (㎡)",
                type: "area",
                fields: [
                  { key: "minArea", label: "최소" },
                  { key: "maxArea", label: "최대" },
                ],
              },
            ]}
          />
        )}

        {hasLease && (
          <FilterGroup
            title="전·월세 조건"
            subtitle="전·월세 가능한 지역"
            regions={leaseRegions}
            filters={leaseFilters}
            onChange={handleChange(setLeaseFilters)}
            optional={[
              {
                title: "면적 선택 (㎡)",
                type: "area",
                fields: [
                  { key: "minArea", label: "최소" },
                  { key: "maxArea", label: "최대" },
                ],
              },
              {
                title: "월세 (만원)",
                type: "number",
                fields: [
                  { key: "rentMin", label: "최소" },
                  { key: "rentMax", label: "최대" },
                ],
              },
            ]}
          />
        )}
      </div>
    </motion.section>
  );
}

function FilterGroup({ title, subtitle, regions, filters, onChange, optional }) {
  const [open, setOpen] = useState(false);

  return (
    <section className="border-t border-border pt-8">
      <div className="mb-6">
        <h2 className="text-xl font-semibold text-text-primary">{title}</h2>
        <p className="text-sm text-text-secondary mt-1">{subtitle}</p>
        <p className="text-sm text-text-secondary mt-1">
          선택 지역:{" "}
          <span className="font-medium text-text-primary">{[...regions].join(", ")}</span>
        </p>
      </div>

      <div>
        <button
          onClick={() => setOpen((v) => !v)}
          className="flex items-center gap-2 text-sm font-medium text-primary hover:text-primary-hover transition"
        >
          <ChevronDown size={18} className={`transition-transform ${open ? "rotate-180" : ""}`} />
          {open ? "추가 조건 닫기" : "추가 조건 펼치기"}
        </button>

        <AnimatePresence initial={false}>
          {open && (
            <motion.div
              initial={{ opacity: 0, height: 0 }}
              animate={{ opacity: 1, height: "auto" }}
              exit={{ opacity: 0, height: 0 }}
              transition={{ duration: 0.25 }}
              className="mt-6 space-y-8"
            >
              {optional.map((group) => (
                <FilterBox key={group.title} group={group} filters={filters} onChange={onChange} />
              ))}
            </motion.div>
          )}
        </AnimatePresence>
      </div>
    </section>
  );
}

function FilterBox({ group, filters, onChange }) {
  return (
    <div className="p-5 rounded-xl border border-border bg-primary-bg/40">
      <h4 className="text-sm font-medium text-text-primary mb-3">{group.title}</h4>
      <div className="grid sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-3">
        {group.fields.map((f) =>
          group.type === "area" ? (
            <AreaSelector
              key={f.key}
              label={f.label}
              value={filters[f.key]}
              onChange={(val) => onChange(f.key, val)}
            />
          ) : (
            <NumberInput
              key={f.key}
              label={f.label}
              value={filters[f.key]}
              onChange={(e) => onChange(f.key, e.target.value)}
            />
          )
        )}
      </div>
    </div>
  );
}

function AreaSelector({ label, value, onChange }) {
  const options = ["33", "59", "74", "84", "99", "120"];
  return (
    <div className="flex flex-col gap-1">
      <label className="text-xs text-text-secondary">{label}</label>
      <div className="grid grid-cols-3 gap-2">
        {options.map((opt) => {
          const isActive = value === opt;
          return (
            <button
              key={opt}
              onClick={() => onChange(isActive ? "" : opt)}
              className={`rounded-lg border px-2 py-2 text-sm transition ${
                isActive
                  ? "border-primary bg-primary text-white"
                  : "border-border hover:bg-primary-bg"
              }`}
            >
              {opt}㎡
            </button>
          );
        })}
      </div>
    </div>
  );
}

function NumberInput({ label, value, onChange }) {
  return (
    <div className="flex flex-col gap-1">
      <label className="text-xs text-text-secondary">{label}</label>
      <input
        type="number"
        value={value || ""}
        onChange={onChange}
        placeholder="입력"
        className="rounded-lg border border-border px-3 py-2 text-sm focus:ring-2 focus:ring-primary-light focus:outline-none"
      />
    </div>
  );
}
