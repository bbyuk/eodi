export default function FormContainer({ children }) {
  return (
    <section className="rounded-[2rem] border border-slate-200 bg-white p-5 shadow-[0_24px_60px_rgba(15,23,42,0.06)] sm:p-6">
      <div className="space-y-6">{children}</div>
    </section>
  );
}
