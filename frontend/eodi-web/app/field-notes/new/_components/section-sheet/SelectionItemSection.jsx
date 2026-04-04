import SelectionSectionHeader from "@/app/field-notes/new/_components/section-sheet/SelectionSectionHeader";
import SelectionItemCard from "@/app/field-notes/new/_components/section-sheet/SelctionItemCard";

export default function SelectionItemSection({ icon, label, items = [], selectedKey, onSelect }) {
  if (!items.length) {
    return null;
  }

  return (
    <section className="space-y-3">
      <SelectionSectionHeader icon={icon} label={label} />

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
    </section>
  );
}
