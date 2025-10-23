import ToggleButton from "@/components/ui/input/ToggleButton";

export default function AreaSelector({ options, label, value, onChange }) {
  return (
    <div className="flex flex-col gap-1">
      <label className="text-xs text-text-secondary">{label}</label>
      <div className="grid grid-cols-6 gap-2">
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
