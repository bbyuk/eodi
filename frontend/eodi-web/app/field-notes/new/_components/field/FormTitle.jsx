export default function FormTitle({ main, sub, preserveSubSpace = true }) {
  return (
    <div className="space-y-1">
      <p className="text-sm font-semibold text-slate-900">{main}</p>
      {sub || preserveSubSpace ? (
        <p className="text-xs font-medium text-slate-500">{sub}</p>
      ) : null}
    </div>
  );
}
