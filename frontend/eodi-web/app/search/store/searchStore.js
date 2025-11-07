import { create } from "zustand";
import { context } from "@/app/search/_const/context";

export const useSearchStore = create((set, get) => ({
  currentContext: context.cash,
  cash: "",
  selectedHousingTypes: new Set(["AP", "OF"]),
  selectedSellRegions: new Set(),
  selectedLeaseRegions: new Set(),
  currentDirection: "initial",

  // setter
  setCurrentContext: (currentContext) => set({ currentContext }),
  setCash: (cash) => set({ cash }),
  setCurrentDirection: (currentDirection) => set({ currentDirection }),
  setDirectionToForward: () => set({ currentDirection: "forward" }),
  setDirectionToBackward: () => set({ currentDirection: "backward" }),

  // reset
  resetContext: () => set({ currentContext: context.cash }),
  resetCash: () => set({ cash: "" }),
  resetSelectedSellRegions: () => set({ selectedSellRegions: new Set() }),
  resetSelectedLeaseRegions: () => set({ selectedLeaseRegions: new Set() }),
  resetDirection: () => set({ currentDirection: "initial" }),

  resetStep1: () => {
    const { resetCash, resetStep2 } = get();
    resetCash();
    resetStep2();
  },
  resetStep2: () => {
    const { resetSelectedSellRegions, resetSelectedLeaseRegions, resetStep3 } = get();
    resetSelectedSellRegions();
    resetSelectedLeaseRegions();
    resetStep3();
  },
  resetStep3: () => {
    console.log("step3 초기화 완료");
  },

  toggleHousingType: (value) =>
    set((state) => {
      const next = new Set(state.selectedHousingTypes);
      next.has(value) ? next.delete(value) : next.add(value);
      return { selectedHousingTypes: next };
    }),

  toggleSellRegion: (value) =>
    set((state) => {
      const next = new Set(state.selectedSellRegions);
      next.has(value) ? next.delete(value) : next.add(value);
      return { selectedSellRegions: next };
    }),
  toggleLeaseRegion: (value) =>
    set((state) => {
      const next = new Set(state.selectedLeaseRegions);
      next.has(value) ? next.delete(value) : next.add(value);
      return { selectedLeaseRegions: next };
    }),
  resetSearchStore: () => {
    const {
      resetContext,
      resetCash,
      resetSelectedSellRegions,
      resetSelectedLeaseRegions,
      resetDirection,
    } = get();

    resetContext();
    resetCash();
    resetSelectedSellRegions();
    resetSelectedLeaseRegions();
    resetDirection();
  },
}));
