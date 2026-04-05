import Field from "@/app/field-notes/new/_components/field/Field";
import TextEntryInput from "@/app/field-notes/new/_components/input/TextEntryInput";

export default function TextInputField({ title, value, onChange, placeholder }) {
  return (
    <Field title={title}>
      <TextEntryInput value={value} onChange={onChange} placeholder={placeholder} />
    </Field>
  );
}
