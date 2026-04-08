import Field from "@/app/field-notes/new/_components/field/Field";
import { FIELD_NOTE_INPUT_RADIUS_CLASS } from "@/app/field-notes/new/_components/styles";

export default function DateInputField({ title, value, onChange }) {
  return (
    <Field title={title}>
      <input
        type="date"
        value={value}
        onChange={(event) => onChange(event.target.value)}
        className={`min-h-14 w-full border border-slate-200 bg-white px-4 text-sm font-medium text-slate-900 focus:outline-none ${FIELD_NOTE_INPUT_RADIUS_CLASS}`}
      />
    </Field>
  );
}
