export default function NumberInput({ label, value, placeholder, onChange, unit }) {
  return (
    <section>
      <label className="block text-sm font-medium text-text-secondary mb-2">{label}</label>

      <div className="flex items-center gap-3">
        <div className="relative flex-1">
          <input
            type="text"
            inputMode="numeric"
            value={value}
            onChange={onChange}
            placeholder={placeholder}
            className={`w-full px-4 py-3 border border-border rounded-lg text-right text-text-primary
                    placeholder:text-text-secondary focus:ring-2 focus:ring-primary
                    focus:border-primary focus:outline-none transition
                    ${unit ? "pr-12" : "pr-4"}`}
          />

          {unit && (
            <span className="absolute right-4 top-1/2 -translate-y-1/2 text-text-secondary text-sm">
              {unit}
            </span>
          )}
        </div>
      </div>
    </section>
  );
}
