import FieldTitle from "@/app/field-notes/new/_components/field/FieldTitle";

export default function TextInputField({ title, value, onChange, placeholder }) {
  return (
    <>
      <FieldTitle main={title.main} sub={title.sub} />
      <input
        type="text"
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        className="min-h-14 w-full rounded-[1.25rem] border border-slate-200 bg-white px-4 text-sm text-slate-900 placeholder:text-slate-400 focus:outline-none"
      />
    </>
  );
}
