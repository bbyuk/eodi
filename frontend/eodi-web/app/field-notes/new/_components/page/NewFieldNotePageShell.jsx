import FormHeader from "@/components/layout/FormHeader";
import FieldNoteForm from "@/app/field-notes/new/_components/form/FieldNoteForm";
import COPY from "@/app/field-notes/new/_data/complexRecordCopy";

export default function NewFieldNotePageShell({ children }) {
  return (
    <div className="mx-auto flex w-full max-w-3xl flex-col gap-8 px-4 pb-16 pt-24 sm:px-6 sm:pt-28 lg:px-8 lg:pt-32">
      <FormHeader value={COPY.pageTitle} />

      <FieldNoteForm>{children}</FieldNoteForm>
    </div>
  );
}
