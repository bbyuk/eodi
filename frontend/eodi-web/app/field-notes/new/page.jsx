import { noteTemplates } from "@/app/_data/workspace";
import ActionButton from "@/components/ui/input/ActionButton";

export const metadata = {
  title: "새 임장노트 | 어디살까",
  description: "새 임장노트를 시작하는 화면",
};

export default function NewFieldNotePage() {
  return (
    <div className="mx-auto flex w-full max-w-6xl flex-col gap-8 px-4 pb-16 pt-24 sm:px-6 sm:pt-28 lg:px-8 lg:pt-32">
      <section className="rounded-[2rem] border border-slate-200 bg-white p-6 shadow-[0_24px_60px_rgba(15,23,42,0.06)] sm:p-8">
        <div className="max-w-3xl space-y-3">
          <p className="text-sm font-semibold tracking-wide text-emerald-700">새 임장노트</p>
          <h1 className="text-3xl font-semibold tracking-tight text-slate-950 sm:text-4xl">
            빈 노트로 시작하거나 템플릿을 골라 바로 기록하세요
          </h1>
          <p className="text-sm leading-6 text-slate-600 sm:text-base">
            실제 작성 기능이 붙기 전까지는 시작 구조와 템플릿 골격을 먼저 잡아둔 상태입니다.
          </p>
        </div>

        <div className="mt-6 flex flex-col gap-3 sm:flex-row">
          <ActionButton type="button" variant="dark" size="md">
            빈 노트로 시작
          </ActionButton>
          <ActionButton
            href="/search"
            variant="outline"
            size="md"
          >
            실거래 검색 먼저 연결하기
          </ActionButton>
        </div>
      </section>

      <section className="grid gap-4 lg:grid-cols-3">
        {noteTemplates.map((template) => (
          <article
            key={template.id}
            className="rounded-[1.75rem] border border-slate-200 bg-white p-5 shadow-[0_18px_50px_rgba(15,23,42,0.05)]"
          >
            <p className="text-base font-semibold text-slate-950">{template.title}</p>
            <p className="mt-3 text-sm leading-6 text-slate-600">{template.description}</p>
            <ActionButton type="button" variant="outline" size="sm" className="mt-6">
              이 템플릿으로 시작
            </ActionButton>
          </article>
        ))}
      </section>
    </div>
  );
}
