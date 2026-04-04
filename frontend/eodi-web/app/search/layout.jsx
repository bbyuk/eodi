"use client";

import { createContext, useCallback, useContext, useEffect } from "react";
import { usePathname, useRouter } from "next/navigation";
import { useSearchStore } from "@/app/search/store/searchStore";

const SearchContext = createContext({
  goNext: () => {},
  goPrev: () => {},
});

export const useSearchContext = () => useContext(SearchContext);

export default function SearchLayout({ children }) {
  const router = useRouter();
  const pathname = usePathname();
  const {
    cash,
    currentContext,
    selectedSellRegions,
    selectedLeaseRegions,
    setDirectionToForward,
    setDirectionToBackward,
    resetSearchStore,
    resetDirection,
  } = useSearchStore();

  /**
   * TODO isNextButtonActive 배열 추가
   * @type {((function(): *)|(function())|*)[]}
   */
  const isNextButtonActive = [
    null,
    cash && Number(cash) > 0,
    selectedSellRegions.size > 0 || selectedLeaseRegions.size > 0,
    true,
  ];

  // scroll restoration 제어
  useEffect(() => {
    if ("scrollRestoration" in window.history) {
      window.history.scrollRestoration = "manual";
    }
  }, []);

  // 페이지 이동 시 scrollTop
  useEffect(() => {
    window.scrollTo({ top: 0, behavior: "instant" });
  }, [pathname]);

  const goNext = () => {
    setDirectionToForward();
    router.push(`/search/step${currentContext.step + 1}`, { scroll: false });
  };

  const goPrev = () => {
    setDirectionToBackward();
    router.push(`/search/step${currentContext.step - 1}`, { scroll: false });
  };

  const goFirst = useCallback(() => {
    resetDirection();
    router.push("/search/step1", { scroll: false });
  }, [resetDirection]);

  useEffect(() => {
    return () => {
      resetSearchStore();
    };
  }, []);

  return (
    <section className="relative flex min-h-screen flex-col overflow-hidden bg-slate-50">
      <div className="flex flex-1 justify-center px-4 pb-28 sm:px-6 sm:pb-32 lg:px-8">
        <div className="w-full max-w-[72rem] transition-opacity duration-300 ease-in-out">
          <SearchContext.Provider value={{ goNext, goPrev, goFirst }}>
            {children}
          </SearchContext.Provider>
        </div>
      </div>

      <div className="fixed bottom-0 left-0 z-50 w-full border-t border-border bg-white/92 px-4 py-3 backdrop-blur-md sm:px-6 sm:py-4 [padding-bottom:calc(env(safe-area-inset-bottom)+0.75rem)]">
        <div className="mx-auto flex max-w-[90rem] items-center justify-between gap-3">
          {currentContext.prevButton ? (
            <button
              onClick={goPrev}
              className="min-h-12 flex-1 rounded-xl border px-4 py-3 text-sm font-medium text-text-secondary transition-all hover:bg-primary-bg sm:flex-none sm:px-6"
            >
              {currentContext.prevButton.label}
            </button>
          ) : (
            <div className="hidden sm:block" />
          )}

          {currentContext.nextButton ? (
            <button
              onClick={() => {
                if (isNextButtonActive[currentContext.step]) {
                  goNext();
                }
              }}
              className={`min-h-12 flex-1 rounded-xl px-4 py-3 text-sm font-semibold shadow-sm transition-all sm:flex-none sm:px-6
            ${
              isNextButtonActive[currentContext.step]
                ? "bg-primary text-white hover:bg-primary-hover"
                : "cursor-not-allowed bg-gray-200 text-gray-400 opacity-60"
            }`}
            >
              {currentContext.nextButton.label}
            </button>
          ) : null}
        </div>
      </div>
    </section>
  );
}
