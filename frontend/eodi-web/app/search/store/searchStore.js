import { create } from "zustand";
import { context } from "@/app/search/context";

export const useSearchStore = create((set) => ({
  currentContext: context.cash,
  cash: "",
  selectedSellRegions: new Set(),
  selectedLeaseRegions: new Set(),
  currentAnimation: "fade-up",

  setCurrentContext: (currentContext) => set({ currentContext }),
  setCash: (cash) => set({ cash }),
  setCurrentAnimation: (currentAnimation) => set({ currentAnimation }),

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
