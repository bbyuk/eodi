export default function FieldNoteSection({
  children,
  className = "",
  paddingClassName = "p-4",
  as: Component = "section",
}) {
  return (
    <Component className={`rounded-[1.5rem] border border-slate-200 ${paddingClassName} ${className}`}>
      {children}
    </Component>
  );
}
