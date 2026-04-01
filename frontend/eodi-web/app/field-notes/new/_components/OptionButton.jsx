export default function OptionButton(props) {
  return (
    <button
      {...props}
      type="button"
      className={`rounded-full border px-3 py-3 text-sm font-semibold transition ${
        props?.active
          ? "border-slate-950 bg-slate-950 text-white"
          : "border-slate-200 bg-white text-slate-700 hover:border-slate-300 hover:bg-slate-50"
      }`}
    >
      {props?.label}
    </button>
  );
}
