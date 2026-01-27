"use client";

import { create } from "zustand";
import { context } from "@/app/search/_const/context";

export const useSearchStore = create((set, get) => ({
  // 현재 step 컨텍스트
  currentContext: context.cash,
  // 입력된 현금
  cash: "",
  // 조회된 주택 유형 목록
  inquiredHousingTypes: ["AP", "OF"],
  // 선택된 매매 지역
  selectedSellRegions: new Set(),
  // 선택된 임대차 지역
  selectedLeaseRegions: new Set(),
  // 애니메이션 방향
  currentDirection: "initial",

  // setter
  /**
   * 현재 step context setter
   * @param currentContext 현재 step 컨텍스트
   */
  setCurrentContext: (currentContext) => set({ currentContext }),
  /**
   * 보유 현금 set
   * @param cash 보유 현금 입력
   */
  setCash: (cash) => set({ cash }),
  /**
   * 애니메이션 방향 변경
   * @param currentDirection 방향
   * @example backward | forward | initial
   */
  setCurrentDirection: (currentDirection) => set({ currentDirection }),
  /**
   * 애니메이션 방향을 forward로 변경한다
   */
  setDirectionToForward: () => set({ currentDirection: "forward" }),
  /**
   * 애니메이션 방향을 backward로 변경한다
   */
  setDirectionToBackward: () => set({ currentDirection: "backward" }),
  /**
   * 지역 조회 시 파라미터로 전달한 주택 유형 목록
   * 조회 콜백에서 처리한다.
   * @param inquiredHousingTypes 조회된 주택 유형 목록
   */
  setInquiredHousingTypes: (inquiredHousingTypes) => set({ inquiredHousingTypes }),

  // reset
  /**
   * 현재 step 컨텍스트를 리셋한다
   */
  resetContext: () => set({ currentContext: context.cash }),
  /**
   * 현재 입력된 보유 현금을 리셋한다
   */
  resetCash: () => set({ cash: "" }),
  /**
   * 선택된 매매 지역 목록을 리셋한다.
   */
  resetSelectedSellRegions: () => set({ selectedSellRegions: new Set() }),
  /**
   * 선택된 임대차 지역 목록을 리셋한다.
   */
  resetSelectedLeaseRegions: () => set({ selectedLeaseRegions: new Set() }),
  /**
   * 애니메이션 방향을 initial로 초기화한다.
   */
  resetDirection: () => set({ currentDirection: "initial" }),
  /**
   * 조회된 주택 유형 목록을 리셋한다.
   */
  resetInquiredHousingTypes: () => set({ inquiredHousingTypes: ["AP", "OF"] }),

  /**
   * step1 페이지에서 사용되는 store 상태를 초기로 리셋한다.
   */
  resetStep1: () => {
    const { resetCash, resetStep2 } = get();
    resetCash();
    resetStep2();
  },
  /**
   * step2 페이지에서 사용되는 store 상태를 초기로 리셋한다.
   */
  resetStep2: () => {
    const {
      resetSelectedSellRegions,
      resetSelectedLeaseRegions,
      resetInquiredHousingTypes,
      resetStep3,
    } = get();
    resetSelectedSellRegions();
    resetSelectedLeaseRegions();
    resetInquiredHousingTypes();
    resetStep3();
  },
  /**
   * step3 페이지에서 사용되는 store 상태를 초기로 리셋한다.
   */
  resetStep3: () => {
    // console.log("step3 초기화 완료");
  },

  /**
   * 매매 지역 버튼 상태를 토글한다.
   * @param value 대상 매매 지역
   */
  toggleSellRegion: (value) =>
    set((state) => {
      const next = new Set(state.selectedSellRegions);
      next.has(value) ? next.delete(value) : next.add(value);
      return { selectedSellRegions: next };
    }),
  /**
   * 임대차 지역 버튼 상태를 토글한다.
   * @param value 대상 임대차 지역
   */
  toggleLeaseRegion: (value) =>
    set((state) => {
      const next = new Set(state.selectedLeaseRegions);
      next.has(value) ? next.delete(value) : next.add(value);
      return { selectedLeaseRegions: next };
    }),
  /**
   * search 페이지에서 사용되는 store 상태를 모두 리셋한다.
   */
  resetSearchStore: () => {
    const {
      resetContext,
      resetCash,
      resetSelectedSellRegions,
      resetSelectedLeaseRegions,
      resetDirection,
      resetInquiredHousingTypes,
    } = get();

    resetContext();
    resetCash();
    resetSelectedSellRegions();
    resetSelectedLeaseRegions();
    resetDirection();
    resetInquiredHousingTypes();
  },
}));
