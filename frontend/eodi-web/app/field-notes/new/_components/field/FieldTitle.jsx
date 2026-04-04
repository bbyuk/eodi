export default function FieldTitle({ main, sub }) {
  return (
    <div className="space-y-1">
      <p className="text-sm font-semibold text-slate-900">{main}</p>
      <p className="text-xs font-medium text-slate-500">{sub}</p>
    </div>
  );
}
