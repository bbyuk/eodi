"use client";

import { motion } from "framer-motion";
import { ArrowRight, Search, LayoutTemplate } from "lucide-react";
import ActionButton from "@/components/ui/input/ActionButton";
import TextActionLink from "@/components/ui/input/TextActionLink";

export default function HomeHero({ workspaceSummary }) {
  return (
    <section className="relative overflow-hidden rounded-[2rem] border border-white/70 bg-[radial-gradient(circle_at_top_left,_rgba(59,130,246,0.28),_transparent_32%),linear-gradient(135deg,#eff6ff_0%,#f8fafc_45%,#ecfeff_100%)] px-5 py-8 shadow-[0_32px_80px_rgba(15,23,42,0.08)] sm:px-8 sm:py-10 lg:px-12 lg:py-14">
      <div className="absolute inset-y-8 right-[-4rem] hidden w-64 rounded-full bg-white/50 blur-3xl lg:block" />

      <div className="relative grid gap-8 lg:grid-cols-[minmax(0,1.2fr)_minmax(18rem,0.8fr)] lg:items-end">
        <motion.div
          initial={{ opacity: 0, y: 18 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.45, ease: "easeOut" }}
          className="space-y-6"
        >
          <div className="space-y-4">
            <p className="text-sm font-semibold tracking-wide text-blue-700">임장 기록을 더 쉽게</p>
            <h1 className="max-w-3xl text-4xl font-semibold tracking-tight text-slate-950 sm:text-5xl lg:text-6xl">
              임장 기록이 쌓일수록
              <br />
              선택은 선명해집니다
            </h1>
            <p className="max-w-2xl text-base leading-7 text-slate-600 sm:text-lg">
              다녀온 곳을 기록해두고 비교해보세요
            </p>
          </div>

          <div className="flex flex-col gap-3 sm:flex-row">
            <ActionButton
              href="/field-notes/new"
              variant="dark"
              size="md"
            >
              새 임장노트 만들기
              <ArrowRight className="h-4 w-4" />
            </ActionButton>
            <ActionButton
              href="/search"
              variant="frosted"
              size="md"
            >
              기록 둘러보기
              <Search className="h-4 w-4" />
            </ActionButton>
          </div>

          <TextActionLink
            href="/field-notes/new"
          >
            템플릿으로 시작하기
            <LayoutTemplate className="h-4 w-4" />
          </TextActionLink>
        </motion.div>

        <motion.aside
          initial={{ opacity: 0, y: 24 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.5, ease: "easeOut", delay: 0.08 }}
          className="rounded-[1.75rem] border border-slate-200/80 bg-white/85 p-5 shadow-[0_24px_60px_rgba(37,99,235,0.15)] backdrop-blur"
        >
          <div className="space-y-1">
            <p className="text-sm font-semibold tracking-wide text-slate-500">오늘의 작업 상태</p>
            <h2 className="text-2xl font-semibold text-slate-950">작업공간 요약</h2>
          </div>

          <div className="mt-5 space-y-3">
            {workspaceSummary.map((item) => (
              <div
                key={item.label}
                className="rounded-2xl border border-slate-200 bg-slate-50/80 px-4 py-4"
              >
                <p className="text-sm font-medium text-slate-500">{item.label}</p>
                <div className="mt-2 flex items-end justify-between gap-3">
                  <p className="text-2xl font-semibold text-slate-950">{item.value}</p>
                  <p className="text-xs font-medium text-slate-500">{item.helper}</p>
                </div>
              </div>
            ))}
          </div>
        </motion.aside>
      </div>
    </section>
  );
}
