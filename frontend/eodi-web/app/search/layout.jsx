"use client";

import { useRouter } from "next/navigation";
import StepIndicator from "@/app/search/StepIndicator";
import { useSearchStore } from "@/app/search/store/searchStore";

export default function SearchLayout({ children }) {
  const router = useRouter();
  const { currentContext } = useSearchStore();

  const goToStep = (n) => router.push(`/search/step${n}`, { scroll: false });
  const goBack = () => router.back();

  return (
    <section className="relative w-full min-h-screen bg-white overflow-hidden">
      <div className="sticky top-16 w-full bg-white/80 backdrop-blur-md border-b border-border z-40">
        <div className="max-w-6xl mx-auto px-6 py-4 flex justify-center">
          <StepIndicator step={currentContext.step} />
        </div>
      </div>

      {/* ▼ 콘텐츠 (StepIndicator 높이만큼 정확히 띄움) */}
      <div
        className={`transition-opacity duration-300 ease-in-out max-w-6xl mx-auto px-6`}
        style={{
          marginTop: "calc(4rem + 4rem)", // Navbar + StepIndicator 높이
          marginBottom: "5rem", // BottomBar 공간 확보
        }}
      >
        {children}
      </div>

      {/* ▼ 하단 버튼바 */}
      <div className="fixed bottom-0 left-0 w-full bg-white/80 backdrop-blur-md border-t border-border py-4 px-6 z-50">
        <div className="max-w-6xl mx-auto flex justify-between items-center">
          {currentContext.prevButton ? (
            <button
              onClick={goBack}
              className="px-6 py-3 rounded-xl border text-sm font-medium text-text-secondary hover:bg-primary-bg transition-all"
            >
              {currentContext.prevButton.label}
            </button>
          ) : (
            <div></div>
          )}

          {currentContext.nextButton ? (
            <button
              onClick={() =>
                currentContext.step < 3
                  ? goToStep(currentContext.step + 1)
                  : alert("필터 적용 완료!")
              }
              className="px-6 py-3 rounded-xl text-sm font-semibold transition-all shadow-sm bg-primary text-white hover:bg-primary-hover"
            >
              {currentContext.nextButton.label}
            </button>
          ) : (
            <></>
          )}
        </div>
      </div>
    </section>
  );
}
