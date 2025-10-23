import { create } from "zustand";
import { context } from "@/app/search/_const/context";

export const useSearchStore = create((set) => ({
  currentContext: context.cash,
  cash: "",
  selectedSellRegions: new Set(),
  selectedLeaseRegions: new Set(),
  currentAnimation: "fade-up",
  currentDirection: "initial",

  setCurrentContext: (currentContext) => set({ currentContext }),
  setCash: (cash) => set({ cash }),
  resetCash: () => set({ cash: "" }),
  setCurrentAnimation: (currentAnimation) => set({ currentAnimation }),
  setCurrentDirection: (currentDirection) => set({ currentDirection }),

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
}));
