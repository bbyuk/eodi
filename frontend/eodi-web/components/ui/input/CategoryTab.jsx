import CategoryButton from "@/components/ui/input/CategoryButton";

/**
 * 카테고리 탭 UI 컴포넌트
 * @param list 카테고리 목록 list
 * @param value 선택된 값
 * @param onSelect 선택 이벤트 핸들러
 * @param type 카테고리 탭 타입
 */
export default function CategoryTab({ list, value, onSelect, type = "toggle" }) {
  return (
    <div className={"flex gap-2"}>
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
