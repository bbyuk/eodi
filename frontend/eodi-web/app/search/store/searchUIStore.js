export const useSearchUIStore = create((set, get) => ({
  isRegionDrawerOpen: false,
  openRegionDrawer: () => set({ isRegionDrawerOpen: true }),
  closeRegionDrawer: () => set({ isRegionDrawerOpen: false }),
}));
