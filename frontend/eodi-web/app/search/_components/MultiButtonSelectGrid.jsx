import ToggleButton from "@/components/ui/input/ToggleButton";

export default function MultiButtonSelectGrid({ list, selected, onSelect, placeholder }) {
  return (
    <div className="relative">
      <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-3">
        {list.map((name) => {
          const isActive = selected.has(name);
          return (
            <ToggleButton
              key={name}
              onClick={() => onSelect(name)}
              size={"md"}
              isActive={isActive}
              label={name}
            />
          );
        })}

        {list.length === 0 && (
          <div className="col-span-full py-6 text-center text-sm text-text-secondary border border-dashed border-border rounded-lg">
            {placeholder}
          </div>
        )}
      </div>
    </div>
  );
}
