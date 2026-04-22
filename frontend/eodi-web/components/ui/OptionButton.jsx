export default function OptionButton({ active, onClick, label }) {
  return (
    <button
      onClick={onClick}
      type="button"
      className={`rounded-full border px-3 py-3 text-sm font-semibold transition ${
        active
          ? "border-[var(--choice-chip-selected-border)] bg-[var(--choice-chip-selected-bg)] text-[var(--choice-chip-selected-text)] shadow-[var(--choice-chip-selected-shadow)]"
          : "border-slate-200 bg-white text-slate-700 hover:border-[var(--choice-chip-hover-border)] hover:bg-[var(--choice-chip-hover-bg)]"
      }`}
    >
      {label}
    </button>
  );
}
