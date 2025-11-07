"use client";

import { createContext, useContext, useEffect } from "react";
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

  useEffect(() => {
    return () => {
      resetSearchStore();
    };
  }, []);

  return (
    <section className="relative flex flex-col min-h-screen bg-white overflow-hidden">
      {/* 콘텐츠 */}

      <div className="flex-1 flex justify-center px-10">
        <div className="w-full max-w-[70rem] transition-opacity duration-300 ease-in-out">
          <SearchContext.Provider value={{ goNext, goPrev }}>{children}</SearchContext.Provider>
        </div>
      </div>

      {/* 하단 버튼바 */}
      <div className="fixed bottom-0 left-0 w-full bg-white/80 backdrop-blur-md border-t border-border py-4 px-10 z-50">
        <div className="max-w-[90rem] mx-auto flex justify-between items-center">
          {currentContext.prevButton ? (
            <button
              onClick={goPrev}
              className="px-6 py-3 rounded-xl border text-sm font-medium text-text-secondary hover:bg-primary-bg transition-all"
            >
              {currentContext.prevButton.label}
            </button>
          ) : (
            <div />
          )}

          {currentContext.nextButton ? (
            <button
              onClick={() => {
                if (isNextButtonActive[currentContext.step]) {
                  goNext();
                }
              }}
              className={`px-6 py-3 rounded-xl text-sm font-semibold transition-all shadow-sm 
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
