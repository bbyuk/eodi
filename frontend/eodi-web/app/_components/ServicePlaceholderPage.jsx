import Link from "next/link";

export default function ServicePlaceholderPage({ service }) {
  return (
    <div className="mx-auto flex min-h-[calc(100vh-4rem)] w-full max-w-5xl items-center px-4 pb-12 pt-24 sm:px-6 sm:pt-28 lg:px-8 lg:pt-32">
      <section className="w-full rounded-[2rem] border border-slate-200 bg-white p-6 shadow-[0_24px_60px_rgba(15,23,42,0.06)] sm:p-8 lg:p-10">
        <div className={`mb-6 h-2 w-28 rounded-full bg-gradient-to-r ${service.accent}`} />

        <div className="flex flex-col gap-4 lg:flex-row lg:items-start lg:justify-between">
          <div className="max-w-2xl space-y-4">
            <p className="text-sm font-semibold tracking-wide text-slate-500">{service.status}</p>
            <h1 className="text-3xl font-semibold tracking-tight text-slate-950 sm:text-4xl">
              {service.name}
            </h1>
            <p className="text-base leading-7 text-slate-600">{service.summary}</p>
            <p className="text-sm leading-6 text-slate-600">{service.description}</p>
          </div>
          <div className="rounded-2xl bg-slate-50 px-4 py-3 text-sm font-medium text-slate-700">
            서비스 라우트는 준비되었습니다
          </div>
        </div>

        <div className="mt-8 grid gap-3 sm:grid-cols-3">
          {service.points.map((point) => (
            <div
              key={point}
              className="rounded-2xl border border-slate-200 bg-slate-50 px-4 py-4 text-sm text-slate-700"
            >
              {point}
            </div>
          ))}
        </div>

        <div className="mt-8 flex flex-col gap-3 sm:flex-row">
          <Link
            href="/search"
            className="inline-flex items-center justify-center rounded-full bg-slate-950 px-5 py-3 text-sm font-semibold text-white transition hover:bg-slate-800"
          >
            실거래 검색 먼저 사용하기
          </Link>
          <Link
            href="/"
            className="inline-flex items-center justify-center rounded-full border border-slate-300 bg-white px-5 py-3 text-sm font-semibold text-slate-700 transition hover:border-slate-400 hover:bg-slate-50"
          >
            홈으로 돌아가기
          </Link>
        </div>
      </section>
    </div>
  );
}

