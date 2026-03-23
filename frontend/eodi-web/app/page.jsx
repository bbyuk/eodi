import HomeHero from "./_components/HomeHero";
import ServiceCard from "./_components/ServiceCard";
import {
  primaryService,
  secondaryServices,
  serviceFlow,
  services,
} from "./_data/services";

export default function LandingPage() {
  return (
    <div className="bg-[linear-gradient(180deg,#f8fbff_0%,#f7f8fa_30%,#ffffff_100%)]">
      <div className="mx-auto flex w-full max-w-7xl flex-col gap-16 px-4 pb-16 pt-24 sm:px-6 sm:pt-28 lg:px-8 lg:pt-32">
        <HomeHero primaryService={primaryService} secondaryServices={secondaryServices} />

        <section className="space-y-8">
          <div className="flex flex-col gap-3 sm:flex-row sm:items-end sm:justify-between">
            <div className="space-y-2">
              <p className="text-sm font-semibold tracking-wide text-slate-500">서비스</p>
              <h2 className="text-3xl font-semibold tracking-tight text-slate-950 sm:text-4xl">
                필요한 기능을 바로 고를 수 있게 구성했습니다
              </h2>
            </div>
            <p className="max-w-xl text-sm leading-6 text-slate-600 sm:text-right">
              지금은 실거래 검색을 바로 사용할 수 있고, 이어서 임장노트와 데이터 시각화가
              같은 구조 안에서 확장되도록 라우트를 미리 잡았습니다.
            </p>
          </div>

          <div className="grid gap-4 md:grid-cols-2 xl:grid-cols-3">
            {services.map((item) => (
              <ServiceCard key={item.slug} item={item} />
            ))}
          </div>
        </section>

        <section className="rounded-[2rem] border border-slate-200 bg-slate-950 px-5 py-8 text-white sm:px-8 sm:py-10">
          <div className="max-w-2xl space-y-3">
            <p className="text-sm font-semibold tracking-wide text-cyan-200">이용 흐름</p>
            <h2 className="text-3xl font-semibold tracking-tight sm:text-4xl">
              실제로는 이런 순서로 쓰게 됩니다
            </h2>
            <p className="text-sm leading-6 text-slate-300 sm:text-base">
              실거래 검색이 출발점이고, 이후 임장 기록과 데이터 비교가 자연스럽게 이어지는
              구조를 기준으로 서비스들을 배치했습니다.
            </p>
          </div>

          <div className="mt-8 grid gap-4 lg:grid-cols-3">
            {serviceFlow.map((step) => (
              <article
                key={step.id}
                className="rounded-[1.5rem] border border-white/10 bg-white/5 p-5 backdrop-blur"
              >
                <p className="text-sm font-semibold text-cyan-200">{step.id}</p>
                <h3 className="mt-4 text-xl font-semibold">{step.title}</h3>
                <p className="mt-3 text-sm leading-6 text-slate-300">{step.description}</p>
              </article>
            ))}
          </div>
        </section>
      </div>
    </div>
  );
}
