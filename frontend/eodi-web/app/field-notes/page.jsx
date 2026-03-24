import Link from "next/link";
import WorkspacePanel from "@/app/_components/WorkspacePanel";
import { noteTemplates, recentFieldNotes } from "@/app/_data/workspace";
import ActionButton from "@/components/ui/input/ActionButton";

export const metadata = {
  title: "임장노트 | 어디살까",
  description: "임장노트 작업공간",
};

export default function FieldNotesPage() {
  return (
    <div className="mx-auto flex w-full max-w-7xl flex-col gap-10 px-4 pb-16 pt-24 sm:px-6 sm:pt-28 lg:px-8 lg:pt-32">
      <section className="rounded-[2rem] border border-slate-200 bg-[linear-gradient(135deg,#f0fdf4_0%,#ffffff_55%,#f8fafc_100%)] px-5 py-8 shadow-[0_24px_60px_rgba(15,23,42,0.06)] sm:px-8 sm:py-10">
        <div className="flex flex-col gap-5 lg:flex-row lg:items-end lg:justify-between">
          <div className="max-w-3xl space-y-3">
            <p className="text-sm font-semibold tracking-wide text-emerald-700">
              임장노트 작업공간
            </p>
            <h1 className="text-3xl font-semibold tracking-tight text-slate-950 sm:text-4xl">
              현장에서 본 내용을 바로 남기고 다시 꺼내보는 공간
            </h1>
            <p className="text-sm leading-6 text-slate-600 sm:text-base">
              최근 노트 확인, 새 노트 생성, 템플릿 선택을 한 화면에서 시작할 수 있도록
              구성했습니다.
            </p>
          </div>

          <div className="flex flex-col gap-3 sm:flex-row">
            <ActionButton
              href="/field-notes/new"
              variant="dark"
              size="md"
            >
              새 임장노트 만들기
            </ActionButton>
            <ActionButton
              href="/search"
              variant="outline"
              size="md"
            >
              실거래 검색 연결
            </ActionButton>
          </div>
        </div>
      </section>

      <div className="grid gap-4 xl:grid-cols-[1.1fr_0.9fr]">
        <WorkspacePanel
          title="최근 임장노트"
          description="이어서 수정하거나 공유할 최근 노트를 확인합니다."
        >
          <div className="space-y-3">
            {recentFieldNotes.map((note) => (
              <div
                key={note.id}
                className="flex items-start justify-between gap-4 rounded-2xl border border-slate-200 bg-slate-50 px-4 py-4"
              >
                <div className="space-y-1">
                  <p className="text-base font-semibold text-slate-950">{note.title}</p>
                  <p className="text-sm text-slate-600">{note.region}</p>
                  <p className="text-xs font-medium text-slate-500">{note.updatedAt}</p>
                </div>
                <span className="rounded-full bg-white px-3 py-1 text-xs font-semibold text-slate-600">
                  {note.status}
                </span>
              </div>
            ))}
          </div>
        </WorkspacePanel>

        <WorkspacePanel
          title="노트 템플릿"
          description="빈 노트 대신 자주 쓰는 형식으로 바로 시작합니다."
        >
          <div className="space-y-3">
            {noteTemplates.map((template) => (
              <Link
                key={template.id}
                href="/field-notes/new"
                className="block rounded-2xl border border-slate-200 bg-slate-50 px-4 py-4 transition hover:border-slate-300 hover:bg-white"
              >
                <p className="text-base font-semibold text-slate-950">{template.title}</p>
                <p className="mt-2 text-sm leading-6 text-slate-600">{template.description}</p>
              </Link>
            ))}
          </div>
        </WorkspacePanel>
      </div>
    </div>
  );
}
