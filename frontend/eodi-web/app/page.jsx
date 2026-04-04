import HomeHero from "./_components/HomeHero";
import {
  comparisonCandidates,
  favoriteRegions,
  recentFieldNotes,
  recentTransactions,
  workspaceSummary,
} from "./_data/workspace";
import WorkspacePanel from "./_components/WorkspacePanel";
import Link from "next/link";

export default function LandingPage() {
  return (
    <div className="bg-[linear-gradient(180deg,#f8fbff_0%,#f7f8fa_30%,#ffffff_100%)]">
      <div className="mx-auto flex w-full max-w-7xl flex-col gap-16 px-4 pb-16 pt-24 sm:px-6 sm:pt-28 lg:px-8 lg:pt-32">
        <HomeHero workspaceSummary={workspaceSummary} />

        <section className="grid gap-4 xl:grid-cols-2">
          <WorkspacePanel
            title="최근 임장노트"
            description="최근에 작성하거나 공유한 노트를 바로 이어서 확인합니다."
            actionLabel="모든 노트"
            actionHref="/field-notes"
          >
            <div className="space-y-3">
              {recentFieldNotes.map((note) => (
                <Link
                  key={note.id}
                  href={note.href}
                  className="flex items-start justify-between gap-4 rounded-2xl border border-slate-200 bg-slate-50 px-4 py-4 transition hover:border-slate-300 hover:bg-white"
                >
                  <div className="space-y-1">
                    <p className="text-base font-semibold text-slate-950">{note.title}</p>
                    <p className="text-sm text-slate-600">{note.region}</p>
                    <p className="text-xs font-medium text-slate-500">{note.updatedAt}</p>
                  </div>
                  <span className="rounded-full bg-white px-3 py-1 text-xs font-semibold text-slate-600">
                    {note.status}
                  </span>
                </Link>
              ))}
            </div>
          </WorkspacePanel>

          <WorkspacePanel
            title="관심 지역"
            description="자주 보는 지역을 저장해 두고 실거래 검색으로 바로 이어갑니다."
            actionLabel="실거래 검색"
            actionHref="/search"
          >
            <div className="space-y-3">
              {favoriteRegions.map((region) => (
                <Link
                  key={region.id}
                  href={region.href}
                  className="block rounded-2xl border border-slate-200 bg-slate-50 px-4 py-4 transition hover:border-slate-300 hover:bg-white"
                >
                  <p className="text-base font-semibold text-slate-950">{region.name}</p>
                  <p className="mt-2 text-sm leading-6 text-slate-600">{region.summary}</p>
                </Link>
              ))}
            </div>
          </WorkspacePanel>

          <WorkspacePanel
            title="최근 본 실거래"
            description="노트 작성 전에 마지막으로 살펴본 거래를 다시 확인합니다."
            actionLabel="검색 계속하기"
            actionHref="/search"
          >
            <div className="space-y-3">
              {recentTransactions.map((deal) => (
                <div
                  key={deal.id}
                  className="rounded-2xl border border-slate-200 bg-slate-50 px-4 py-4"
                >
                  <div className="flex items-start justify-between gap-4">
                    <div className="space-y-1">
                      <p className="text-base font-semibold text-slate-950">{deal.name}</p>
                      <p className="text-sm text-slate-600">{deal.meta}</p>
                    </div>
                    <p className="text-sm font-semibold text-blue-700">{deal.price}</p>
                  </div>
                  <p className="mt-3 text-xs font-medium text-slate-500">{deal.viewedAt}</p>
                </div>
              ))}
            </div>
          </WorkspacePanel>

          <WorkspacePanel
            title="비교 중인 후보"
            description="노트와 시각화 기능으로 이어질 비교 후보를 관리합니다."
            actionLabel="비교 화면"
            actionHref="/data-visualization"
          >
            <div className="space-y-3">
              {comparisonCandidates.map((candidate) => (
                <Link
                  key={candidate.id}
                  href={candidate.href}
                  className="block rounded-2xl border border-slate-200 bg-slate-50 px-4 py-4 transition hover:border-slate-300 hover:bg-white"
                >
                  <p className="text-base font-semibold text-slate-950">{candidate.name}</p>
                  <p className="mt-2 text-sm leading-6 text-slate-600">{candidate.summary}</p>
                  <div className="mt-3 flex flex-wrap gap-2">
                    {candidate.tags.map((tag) => (
                      <span
                        key={tag}
                        className="rounded-full border border-slate-200 bg-white px-3 py-1 text-xs font-medium text-slate-600"
                      >
                        {tag}
                      </span>
                    ))}
                  </div>
                </Link>
              ))}
            </div>
          </WorkspacePanel>
        </section>

        <section className="rounded-[2rem] border border-slate-200 bg-slate-950 px-5 py-8 text-white sm:px-8 sm:py-10">
          <div className="max-w-2xl space-y-3">
            <p className="text-sm font-semibold tracking-wide text-cyan-200">작업 흐름</p>
            <h2 className="text-3xl font-semibold tracking-tight sm:text-4xl">
              홈에서 바로 이어지는 네 가지 작업
            </h2>
            <p className="text-sm leading-6 text-slate-300 sm:text-base">
              새 임장노트 생성이 1차 진입이고, 실거래 검색은 필요할 때 연결해서 쓰는 보조
              기능으로 배치했습니다.
            </p>
          </div>

          <div className="mt-8 grid gap-4 lg:grid-cols-4">
            {[
              "새 임장노트 만들기",
              "최근 노트 이어서 작성",
              "실거래 검색으로 후보 확인",
              "비교 후보를 시각화로 넘기기",
            ].map((item, index) => (
              <article
                key={item}
                className="rounded-[1.5rem] border border-white/10 bg-white/5 p-5 backdrop-blur"
              >
                <p className="text-sm font-semibold text-cyan-200">{String(index + 1).padStart(2, "0")}</p>
                <p className="mt-4 text-base font-semibold text-white">{item}</p>
              </article>
            ))}
          </div>
        </section>
      </div>
    </div>
  );
}
