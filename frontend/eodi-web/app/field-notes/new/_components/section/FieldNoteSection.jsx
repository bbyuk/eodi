export default function FieldNoteSection({ children, className = "" }) {
  return (
    <section className={`rounded-[1.5rem] border border-slate-200 p-4 ${className}`}>{children}</section>
  );
}
