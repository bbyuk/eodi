export default function FormTitle({
  main,
  sub,
  preserveSubSpace = true,
  className = "space-y-1",
  mainClassName = "text-sm font-semibold text-slate-900",
  subClassName = "text-xs font-medium text-slate-500",
  mainAs: MainTag = "p",
  subAs: SubTag = "p",
}) {
  return (
    <div className={className}>
      <MainTag className={mainClassName}>{main}</MainTag>
      {sub || preserveSubSpace ? (
        <SubTag className={subClassName}>{sub}</SubTag>
      ) : null}
    </div>
  );
}
