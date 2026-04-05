export default function TextAreaInput({ value, onChange, placeholder, className = "" }) {
  return (
    <textarea
      value={value}
      onChange={onChange}
      placeholder={placeholder}
      className={`min-h-32 w-full rounded-[1.25rem] border border-slate-200 bg-white px-4 py-4 text-sm text-slate-900 placeholder:text-slate-400 focus:outline-none ${className}`}
    />
  );
}
