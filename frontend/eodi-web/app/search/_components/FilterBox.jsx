export default function FilterBox({ title, children }) {
  return (
    <section className="p-5 rounded-xl border border-border/60 bg-white/70 backdrop-blur-sm shadow-sm transition-all duration-200">
      {title && (
        <h4 className="text-sm font-semibold text-text-primary mb-4 tracking-tight">{title}</h4>
      )}

      <div className="grid grid-cols-1 sm:grid-cols-2 w-full divide-y sm:divide-y-0 sm:divide-x divide-border/60">
        {children.map((child, index) => (
          <div key={`filter-${index}`} className="p-3">
            {child}
          </div>
        ))}
      </div>
    </section>
  );
}
