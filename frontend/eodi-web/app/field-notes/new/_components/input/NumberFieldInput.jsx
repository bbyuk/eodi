"use client";

import NumberInput from "@/components/ui/NumberInput";
import { FIELD_NOTE_INPUT_RADIUS_CLASS } from "@/app/field-notes/new/_components/styles";

export default function NumberFieldInput(props) {
  return <NumberInput inputClassName={FIELD_NOTE_INPUT_RADIUS_CLASS} {...props} />;
}
