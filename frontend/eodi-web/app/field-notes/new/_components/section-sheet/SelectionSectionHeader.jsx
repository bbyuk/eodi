export default function SelectionSectionHeader({ icon: Icon, label, count }) {
  return (
    <div className="flex items-center justify-between gap-3 px-1">
      <div className="flex items-center gap-2 text-sm font-semibold text-slate-900">
        {Icon ? <Icon className="h-4 w-4 text-slate-400" /> : null}
        <span>{label}</span>
      </div>

      {typeof count === "number" ? (
        <span className="text-xs font-medium text-slate-500">{count}개</span>
      ) : null}
    </div>
  );
}
