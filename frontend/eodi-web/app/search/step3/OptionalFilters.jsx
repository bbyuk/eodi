"use client";

import { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { ChevronDown } from "lucide-react";
import PageHeader from "@/components/ui/PageHeader";
import NumberInput from "@/components/ui/input/NumberInput";
import ToggleButton from "@/components/ui/input/ToggleButton";
import FilterGroup from "@/app/search/_components/FilterGroup";
import AreaSelector from "@/app/search/_components/AreaSelector";
import FilterBox from "@/app/search/_components/FilterBox";

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
    <section className="max-w-5xl mx-auto px-6 pt-[1vh] pb-[5vh] overflow-x-hidden">
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
    </section>
  );
}
