import CategoryButton from "@/components/ui/input/CategoryButton";

export default function CategoryTab({ list, value, onSelect }) {
  return (
    <div className="flex flex-wrap gap-2 mb-5">
      {list.map((data) => {
        const isActive = value === data.code;
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
