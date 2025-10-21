"use client";

import { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { ChevronDown } from "lucide-react";
import PageHeader from "@/components/ui/PageHeader";
import NumberInput from "@/components/ui/input/NumberInput";
import ToggleButton from "@/components/ui/input/ToggleButton";

export default function OptionalFilters({ sellRegions, leaseRegions, onBack, onApply }) {
  const title = "맞춤 조건을 설정해주세요";
  const description = [
    "매매와 전·월세 각각에 적용할 조건을 설정할 수 있어요.",
    "면적, 월세 등 항목은 모두 선택사항이에요.",
  ];
  const areaOptions = ["33", "59", "74", "84", "99", "120"];

  const [sellFilters, setSellFilters] = useState({ minArea: "", maxArea: "" });
  const [leaseFilters, setLeaseFilters] = useState({
    minArea: "",
    maxArea: "",
    rentMin: "",
    rentMax: "",
  });

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
      <PageHeader title={title} description={description} />

      <div className="space-y-14">
        {hasSell && (
          <FilterGroup title="매매 조건" regions={sellRegions} filters={sellFilters}>
            <FilterBox title={"면적 선택 (㎡)"}>
              <AreaSelector
                label={"최소"}
                options={areaOptions}
                onChange={(value) => setSellFilters((prev) => ({ ...prev, minArea: value }))}
                value={sellFilters.minArea}
              />
              <AreaSelector
                label={"최대"}
                options={areaOptions}
                onChange={(value) => setSellFilters((prev) => ({ ...prev, maxArea: value }))}
                value={sellFilters.maxArea}
              />
            </FilterBox>
          </FilterGroup>
        )}

        {hasLease && (
          <FilterGroup title="전·월세 조건" regions={leaseRegions} filters={leaseFilters}>
            <FilterBox title={"면적 선택 (㎡)"}>
              <AreaSelector
                label={"최소"}
                options={areaOptions}
                onChange={(value) => setLeaseFilters((prev) => ({ ...prev, minArea: value }))}
                value={leaseFilters.minArea}
              />
              <AreaSelector
                label={"최대"}
                options={areaOptions}
                onChange={(value) => setLeaseFilters((prev) => ({ ...prev, maxArea: value }))}
                value={leaseFilters.maxArea}
              />
            </FilterBox>
            <FilterBox>
              <NumberInput
                label={"최소"}
                unit={"만 원"}
                onChange={(value) => setLeaseFilters((prev) => ({ ...prev, rentMin: value }))}
                value={leaseFilters.rentMin}
              />
              <NumberInput
                label={"최대"}
                unit={"만 원"}
                onChange={(value) => setLeaseFilters((prev) => ({ ...prev, rentMax: value }))}
                value={leaseFilters.rentMax}
              />
            </FilterBox>
          </FilterGroup>
        )}
      </div>
    </motion.section>
  );
}

function FilterGroup({ title, subtitle, regions, children }) {
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
              {children}
            </motion.div>
          )}
        </AnimatePresence>
      </div>
    </section>
  );
}

function FilterBox({ title, children }) {
  return (
    <div className="p-5 rounded-xl border border-border bg-primary-bg/40">
      <h4 className="text-sm font-medium text-text-primary mb-3">{title}</h4>
      <div className="grid sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-3">{children}</div>
    </div>
  );
}

function AreaSelector({ options, label, value, onChange }) {
  return (
    <div className="flex flex-col gap-1">
      <label className="text-xs text-text-secondary">{label}</label>
      <div className="grid grid-cols-3 gap-2">
        {options.map((opt) => {
          const isActive = value === opt;
          return (
            <ToggleButton
              isActive={isActive}
              key={opt}
              onClick={() => onChange(isActive ? "" : opt)}
              label={`${opt}㎡`}
            />
          );
        })}
      </div>
    </div>
  );
}
