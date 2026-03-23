"use client";

import Link from "next/link";
import { motion } from "framer-motion";
import { ArrowRight, FileText, BarChart3 } from "lucide-react";

export default function HomeHero({ primaryService, secondaryServices }) {
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
            <p className="text-sm font-semibold tracking-wide text-blue-700">
              지금은 실거래 검색을 바로 사용할 수 있습니다
            </p>
            <h1 className="max-w-3xl text-4xl font-semibold tracking-tight text-slate-950 sm:text-5xl lg:text-6xl">
              찾고,
              <br className="hidden sm:block" />
              기록하고,
              <br className="hidden sm:block" />
              비교하는 부동산 탐색 서비스
            </h1>
            <p className="max-w-2xl text-base leading-7 text-slate-600 sm:text-lg">
              예산과 지역 기준으로 실거래가를 먼저 확인하고, 이어서 임장노트와 데이터
              시각화 기능으로 탐색을 넓혀갈 수 있도록 서비스 구조를 잡았습니다.
            </p>
          </div>

          <div className="flex flex-col gap-3 sm:flex-row">
            <Link
              href={primaryService.href}
              className="inline-flex items-center justify-center gap-2 rounded-full bg-slate-950 px-5 py-3 text-sm font-semibold text-white transition hover:bg-slate-800"
            >
              실거래 검색 시작
              <ArrowRight className="h-4 w-4" />
            </Link>
            <Link
              href={secondaryServices[0]?.href ?? "/"}
              className="inline-flex items-center justify-center gap-2 rounded-full border border-slate-300 bg-white/80 px-5 py-3 text-sm font-semibold text-slate-700 transition hover:border-slate-400 hover:bg-white"
            >
              임장노트 구성 보기
              <FileText className="h-4 w-4" />
            </Link>
          </div>
        </motion.div>

        <motion.aside
          initial={{ opacity: 0, y: 24 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.5, ease: "easeOut", delay: 0.08 }}
          className="rounded-[1.75rem] border border-slate-200/80 bg-white/85 p-5 shadow-[0_24px_60px_rgba(37,99,235,0.15)] backdrop-blur"
        >
          <div className="flex items-start justify-between gap-4">
            <div>
              <p className="text-xs font-semibold uppercase tracking-[0.2em] text-blue-600">NOW</p>
              <h2 className="mt-2 text-2xl font-semibold text-slate-950">{primaryService.name}</h2>
            </div>
            <span className="rounded-full bg-emerald-50 px-3 py-1 text-xs font-semibold text-emerald-700">
              {primaryService.status}
            </span>
          </div>

          <p className="mt-4 text-sm leading-6 text-slate-600">{primaryService.summary}</p>

          <div className="mt-5 space-y-3">
            {primaryService.points.map((item) => (
              <div
                key={item}
                className="flex items-center gap-3 rounded-2xl border border-slate-200 bg-slate-50/80 px-4 py-3 text-sm text-slate-700"
              >
                <span className="h-2.5 w-2.5 rounded-full bg-blue-500" />
                {item}
              </div>
            ))}
          </div>

          <div className="mt-6 grid gap-3 sm:grid-cols-2">
            <Link
              href={secondaryServices[0]?.href ?? "/"}
              className="rounded-2xl border border-slate-200 bg-white px-4 py-4 text-left transition hover:border-slate-300 hover:bg-slate-50"
            >
              <div className="flex items-center gap-2 text-sm font-semibold text-slate-900">
                <FileText className="h-4 w-4 text-emerald-600" />
                임장노트
              </div>
              <p className="mt-2 text-sm leading-6 text-slate-600">
                방문 메모와 공유 흐름을 준비하고 있습니다.
              </p>
            </Link>
            <Link
              href={secondaryServices[1]?.href ?? "/"}
              className="rounded-2xl border border-slate-200 bg-white px-4 py-4 text-left transition hover:border-slate-300 hover:bg-slate-50"
            >
              <div className="flex items-center gap-2 text-sm font-semibold text-slate-900">
                <BarChart3 className="h-4 w-4 text-amber-600" />
                데이터 시각화
              </div>
              <p className="mt-2 text-sm leading-6 text-slate-600">
                단지별 흐름을 차트로 비교하는 기능을 준비 중입니다.
              </p>
            </Link>
          </div>
        </motion.aside>
      </div>
    </section>
  );
}
