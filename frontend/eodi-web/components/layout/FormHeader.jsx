export default function FormHeader({ value }) {
  return (
    <section className="space-y-2">
      <h1 className="text-3xl font-semibold tracking-tight text-slate-950 sm:text-4xl">{value}</h1>
    </section>
  );
}
