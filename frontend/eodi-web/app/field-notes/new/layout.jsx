import NewFieldNotePageShell from "@/app/field-notes/new/_components/page/NewFieldNotePageShell";

export const metadata = {
  title: "새 임장노트 | 어디살까",
  description: "새 임장노트를 시작하는 화면",
};

export default function NewFieldNoteLayout({ children }) {
  return <NewFieldNotePageShell>{children}</NewFieldNotePageShell>;
}
