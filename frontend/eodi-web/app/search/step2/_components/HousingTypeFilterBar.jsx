import CategoryButton from "@/components/ui/input/CategoryButton";

/**
 * 주택유형 선택 필터 bar
 * @param housingTypeOptions 선택 가능 주택유형 목록
 * @param selectedHousingTypes 선택된 주택유형 목록
 * @param onSelectHousingType 주택유형 선택 event handler
 * @returns {JSX.Element}
 * @constructor
 */
export default function HousingTypeFilterBar({
  housingTypeOptions = [],
  selectedHousingTypes = new Set(),
  onSelectHousingType = (code) => {},
}) {
  return (
    <>
      {housingTypeOptions.map((option) => (
        <CategoryButton
          key={option.code}
          onClick={() => onSelectHousingType(option.code)}
          isActive={selectedHousingTypes.has(option.code)}
          label={option.name}
        />
      ))}
    </>
  );
}
