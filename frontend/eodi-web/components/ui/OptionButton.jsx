export default function OptionButton(props) {
  return (
    <button
      {...props}
      type="button"
      className={`rounded-full border px-3 py-3 text-sm font-semibold transition ${
        props?.active
          ? "border-[var(--choice-chip-selected-border)] bg-[var(--choice-chip-selected-bg)] text-[var(--choice-chip-selected-text)] shadow-[var(--choice-chip-selected-shadow)]"
          : "border-slate-200 bg-white text-slate-700 hover:border-[var(--choice-chip-hover-border)] hover:bg-[var(--choice-chip-hover-bg)]"
      }`}
    >
      {props?.label}
    </button>
  );
}
