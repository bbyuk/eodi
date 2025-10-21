"use client";

import { useEffect, useState } from "react";
import { usePathname, useRouter } from "next/navigation";
import StepIndicator from "@/app/search/StepIndicator";

export default function SearchLayout({ children }) {
  const pathname = usePathname();
  const router = useRouter();
  const [displayedPath, setDisplayedPath] = useState(pathname);
  const [isFading, setIsFading] = useState(false);

  const step = (() => {
    if (pathname.includes("/step1")) return 1;
    if (pathname.includes("/step2")) return 2;
    if (pathname.includes("/step3")) return 3;
    return 1;
  })();

  const goToStep = (n) => router.push(`/search/step${n}`, { scroll: false });
  const goBack = () => {
    router.back();

    // 브라우저 앞으로가기 막기
    setTimeout(() => {
      router.replace(window.location.pathname, { scroll: false });
    }, 100);
  };

  // 페이지 전환 감지 → fade-out → 교체 → fade-in
  useEffect(() => {
    if (pathname === displayedPath) return;
    setIsFading(true);
    const timeout = setTimeout(() => {
      setDisplayedPath(pathname);
      setIsFading(false);
    }, 200); // fade-out 시간
    return () => clearTimeout(timeout);
  }, [pathname]);

  return (
    <section className="pt-24 w-full min-h-[100vh] bg-white">
      <section className="relative max-w-6xl mx-auto px-6 pt-[8vh] pb-[16vh] min-h-[60vh] overflow-hidden">
        <div className="fixed top-16 left-0 w-full bg-white/90 backdrop-blur-md border-b border-border z-40">
          <div className="max-w-6xl mx-auto px-6 py-4 flex justify-center">
            <StepIndicator step={step} />
          </div>
        </div>

        {/* ▼ 페이지 콘텐츠 */}
        <div
          className={`transition-opacity duration-300 ease-in-out ${
            isFading ? "opacity-0" : "opacity-100"
          } min-h-[40vh] pt-[14vh] pb-[16vh]`}
        >
          {/* 현재 표시 중인 경로의 children */}
          {pathname === displayedPath && children}
        </div>

        {/* ▼ 하단 버튼바 */}
        <div className="fixed bottom-0 left-0 w-full bg-white/80 backdrop-blur-md border-t border-border py-4 px-6 z-50">
          <div className="max-w-6xl mx-auto flex justify-between items-center">
            {step > 1 ? (
              <button
                onClick={goBack}
                className="px-6 py-3 rounded-xl border text-sm font-medium text-text-secondary hover:bg-primary-bg transition-all"
              >
                이전으로
              </button>
            ) : (
              <div />
            )}

            <button
              onClick={() => (step < 3 ? goToStep(step + 1) : alert("필터 적용 완료!"))}
              className="px-6 py-3 rounded-xl text-sm font-semibold transition-all shadow-sm bg-primary text-white hover:bg-primary-hover"
            >
              {step === 3 ? "찾아보기" : "다음으로"}
            </button>
          </div>
        </div>
      </section>
    </section>
  );
}
