import COPY from "@/app/field-notes/new/_data/complexRecordCopy";
import { BASIC_RECORD_FIELDS } from "@/app/field-notes/new/_data/complexRecordFields";
import FormTitle from "@/app/field-notes/new/_components/field/FormTitle";
import CollapsibleFormSection from "@/app/field-notes/new/_components/section/CollapsibleFormSection";
import FieldRenderer from "@/app/field-notes/new/_components/tab/complex/FieldRenderer";
import { getCompletedFieldLabels } from "@/app/field-notes/new/_components/tab/complex/complexRecordFormUtils";

const getBasicRecordStatus = (basicRecord) => {
  const completedLabels = getCompletedFieldLabels(BASIC_RECORD_FIELDS, basicRecord);
  const completedCount = completedLabels.length;
  const completedSummary =
    completedCount > 2
      ? `${completedLabels.slice(0, 2).join(" / ")} +${completedCount - 2}`
      : completedLabels.join(" / ");

  if (completedCount === 0) {
    return {
      title: COPY.basicRecordCollapsedEmpty,
      summary: "",
    };
  }

  if (completedCount === BASIC_RECORD_FIELDS.length) {
    return {
      title: COPY.basicRecordCollapsedComplete,
      summary: COPY.basicRecordCollapsedCompleteSummary,
    };
  }

  return {
    title: COPY.basicRecordCollapsedPartial,
    summary: completedSummary,
  };
};

export default function BasicRecordSection({ value, isOpen, onToggle, onChange }) {
  const status = getBasicRecordStatus(value);

  return (
    <div className="space-y-3">
      <FormTitle
        main={COPY.basicRecordTitle}
        sub={COPY.basicRecordDescription}
        preserveSubSpace={false}
      />

      <CollapsibleFormSection
        title={status.title}
        headerContent={
          <div className="flex min-w-0 items-center justify-between gap-3">
            <p className="shrink-0 text-sm font-semibold text-slate-900">{status.title}</p>
            {status.summary ? (
              <p className="min-w-0 truncate text-right text-xs font-medium text-slate-500">
                {status.summary}
              </p>
            ) : null}
          </div>
        }
        isOpen={isOpen}
        onToggle={onToggle}
        toggleOnHeader={false}
        headerActionPlacement="beforeToggle"
        className="bg-slate-50 shadow-[0_18px_40px_rgba(15,23,42,0.04)]"
      >
        {BASIC_RECORD_FIELDS.map((field) => (
          <FieldRenderer
            key={field.key}
            field={field}
            value={value[field.key]}
            onChange={(nextValue) => onChange(field.key, nextValue)}
          />
        ))}
      </CollapsibleFormSection>
    </div>
  );
}
