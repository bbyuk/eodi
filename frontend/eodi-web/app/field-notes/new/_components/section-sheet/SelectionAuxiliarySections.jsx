import SelectionItemSection from "@/app/field-notes/new/_components/section-sheet/SelectionItemSection";

export default function SelectionAuxiliarySections({ open, sections = [] }) {
  return (
    <div
      className={`grid overflow-hidden transition-[grid-template-rows,opacity,margin] duration-200 ${
        open ? "grid-rows-[1fr] opacity-100" : "grid-rows-[0fr] opacity-0"
      }`}
    >
      <div className="min-h-0 overflow-hidden">
        <div className="space-y-5 pb-1">
          {sections.map((section) => (
            <SelectionItemSection
              key={section.name}
              icon={section.icon}
              label={section.label}
              items={section.items}
              selectedKey={section.selectedKey}
              onSelect={section.onSelect}
            />
          ))}
        </div>
      </div>
    </div>
  );
}
