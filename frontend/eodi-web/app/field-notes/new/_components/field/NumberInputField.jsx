import Field from "@/app/field-notes/new/_components/field/Field";
import NumberFieldInput from "@/app/field-notes/new/_components/input/NumberFieldInput";

export default function NumberInputField({ title, value, onChange, placeholder, maxValue }) {
  return (
    <Field title={title}>
      <NumberFieldInput
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        maxValue={maxValue}
      />
    </Field>
  );
}
