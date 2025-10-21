export default function FilterBox({ title, children }) {
  return (
    <div className="p-5 rounded-xl border border-border bg-primary-bg/40">
      <h4 className="text-sm font-medium text-text-primary mb-3">{title}</h4>
      <div className="grid sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-3">{children}</div>
    </div>
  );
}
