import Field from "@/app/field-notes/new/_components/field/Field";
import SelectionItemCard from "@/app/field-notes/new/_components/section-sheet/SelctionItemCard";

export default function SelectionItemSection({ icon, label, items = [], selectedKey, onSelect }) {
  const Icon = icon;

  if (!items.length) {
    return null;
  }

  return (
    <Field
      title={{
        main: (
          <span className="inline-flex items-center gap-2">
            {Icon ? <Icon className="h-4 w-4 text-slate-400" /> : null}
            <span>{label}</span>
          </span>
        ),
      }}
      preserveSubSpace={false}
      className="space-y-3"
      titleProps={{ className: "space-y-0" }}
    >
      <div className="space-y-2">
        {items.map((item) => (
          <SelectionItemCard
            key={item.key}
            item={item}
            active={item.key === selectedKey}
            onSelect={onSelect}
          />
        ))}
      </div>
    </Field>
  );
}
