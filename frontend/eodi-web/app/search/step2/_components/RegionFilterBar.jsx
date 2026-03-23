"use client";

import Select from "@/components/ui/input/Select";
import ChipSelect from "@/components/ui/input/ChipSelect";

/**
 * RegionFilterBar
 *
 * props
 * - selectedSido
 * - sidoOptions
 * - sigunguOptions
 * - selectedRegions (Set)
 * - isSigunguLoading
 * - onChangeSido
 * - onToggleRegion
 */

export default function RegionFilterBar({
  selectedSido,
  sidoOptions,
  sigunguOptions,
  selectedRegions,
  isSigunguLoading,
  onChangeSido,
  onToggleRegion,
}) {
  return (
    <>
      <Select
        value={selectedSido}
        allOption
        width="w-full sm:w-[150px]"
        onChange={onChangeSido}
        options={sidoOptions.map((el) => ({
          value: el.code,
          label: el.displayName,
        }))}
      />

      {selectedSido !== "all" && (
        <ChipSelect
          width="w-full sm:w-[180px]"
          onSelect={onToggleRegion}
          selected={selectedRegions}
          options={sigunguOptions.map((el) => ({
            value: el.id,
            label: el.displayName,
          }))}
          placeholder={isSigunguLoading ? "불러오는 중..." : "전체"}
        />
      )}
    </>
  );
}
