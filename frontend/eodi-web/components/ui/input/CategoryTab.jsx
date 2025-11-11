"use client";

import CategoryButton from "@/components/ui/input/CategoryButton";

/**
 * 카테고리 탭 UI 컴포넌트
 * @param list 카테고리 목록 list
 * @param value 선택된 값
 * @param onSelect 선택 이벤트 핸들러
 * @param iconClassName 카테고리 버튼의 icon class 명
 * @param type 카테고리 탭 타입
 */
export default function CategoryTab({
  list,
  value,
  onSelect,
  countCalculator = (value) => 0,
  useBadge = false,
  iconClassName = "",
  type = "toggle",
}) {
  return (
    <div className={"flex gap-2 items-start py-2 min-h-[48px]"}>
      {list.map((data) => {
        const isActive =
          type === "toggle"
            ? value === data.code
            : type === "select"
              ? value.has(data.code)
              : false;
        return (
          <CategoryButton
            key={data.code}
            iconClassName={iconClassName}
            useBadge={useBadge}
            count={countCalculator(data)}
            icon={data.icon}
            onClick={() => onSelect(data.code)}
            isActive={isActive}
            label={data.displayName}
          />
        );
      })}
    </div>
  );
}
