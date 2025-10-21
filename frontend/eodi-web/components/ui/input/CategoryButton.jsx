export default function CategoryButton({ isActive, label, ...props }) {
  return (
    <button
      type="button"
      className={`px-4 py-2 rounded-full border text-sm font-medium transition whitespace-nowrap ${
        isActive
          ? "bg-blue-50 text-blue-700 border-blue-300"
          : "bg-white text-slate-700 border-slate-300 hover:border-blue-300 hover:text-blue-600"
      }`}
      {...props}
    >
      {label}
    </button>
  );
}
