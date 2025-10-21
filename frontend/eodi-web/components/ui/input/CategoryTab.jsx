import CategoryButton from "@/components/ui/input/CategoryButton";

export default function CategoryTab({ list, value, onSelect }) {
  return (
    <div className="flex flex-wrap gap-2 mb-5">
      {Object.keys(list).map((data) => {
        const isActive = value === data;
        return (
          <CategoryButton
            key={data}
            onClick={() => onSelect(data)}
            isActive={isActive}
            label={data}
          />
        );
      })}
    </div>
  );
}
