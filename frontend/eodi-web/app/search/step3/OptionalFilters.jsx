"use client";

import { useState, useEffect } from "react";
import PageHeader from "@/components/ui/PageHeader";
import CashInput from "@/components/ui/input/CashInput";
import FilterGroup from "@/app/search/_components/FilterGroup";
import AreaSelector from "@/app/search/_components/AreaSelector";
import FilterBox from "@/app/search/_components/FilterBox";
import { useSearchStore } from "@/app/search/store/searchStore";
import { context } from "@/app/search/_const/context";
import { formatWon } from "@/app/search/_util/util";

const id = "filter";

export default function OptionalFilters({ onBack, onApply }) {
  const title = "맞춤 조건을 설정해주세요";
  const description = [
    "매매와 전·월세 각각에 적용할 조건을 설정할 수 있어요.",
    "면적, 월세 등 항목은 모두 선택사항이에요.",
  ];

  const { selectedSellRegions, selectedLeaseRegions, setCurrentContext } = useSearchStore();

  const areaOptions = ["33", "59", "74", "84", "99", "120"];

  const [sellFilters, setSellFilters] = useState({ minArea: "", maxArea: "" });
  const [leaseFilters, setLeaseFilters] = useState({
    minArea: "",
    maxArea: "",
    rentMin: "",
    rentMax: "",
  });

  const hasSell = selectedSellRegions?.size > 0;
  const hasLease = selectedLeaseRegions?.size > 0;

  useEffect(() => {
    setCurrentContext(context[id]);
  }, []);

  return (
    <section className="max-w-5xl mx-auto px-6 pt-[1vh] pb-[5vh] overflow-x-hidden">
      <PageHeader title={title} description={description} />

      <div className="space-y-14">
        {hasSell && (
          <FilterGroup title="매매 조건" regions={selectedSellRegions} filters={sellFilters}>
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
          <FilterGroup title="전·월세 조건" regions={selectedLeaseRegions} filters={leaseFilters}>
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
              <CashInput
                label={"최소"}
                unit={"만 원"}
                onChange={(value) => setLeaseFilters((prev) => ({ ...prev, rentMin: value }))}
                value={leaseFilters.rentMin}
                formatter={formatWon}
              />
              <CashInput
                label={"최대"}
                unit={"만 원"}
                onChange={(value) => setLeaseFilters((prev) => ({ ...prev, rentMax: value }))}
                value={leaseFilters.rentMax}
                formatter={formatWon}
              />
            </FilterBox>
          </FilterGroup>
        )}
      </div>
    </section>
  );
}
