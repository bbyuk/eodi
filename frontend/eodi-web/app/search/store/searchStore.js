import { create } from "zustand";
import { context } from "@/app/search/_const/context";

export const useSearchStore = create((set, get) => ({
  currentContext: context.cash,
  cash: "",
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
