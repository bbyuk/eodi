"use client";
import { motion, AnimatePresence } from "framer-motion";
import { CheckSquare, CheckCircle2, X, ChevronUp } from "lucide-react";
import { useSearchStore } from "@/app/search/store/searchStore";

const OpenedCard = ({ close }) => {
  const {
    toggleSellRegion,
    toggleLeaseRegion,
    selectedSellRegions = new Set(),
    selectedLeaseRegions = new Set(),
  } = useSearchStore();

  const hasSell = selectedSellRegions.size > 0;
  const hasLease = selectedLeaseRegions.size > 0;

  return (
    <motion.aside
      initial={{ x: 20, opacity: 0 }}
      animate={{ x: 0, opacity: 1 }}
      transition={{ duration: 0.25, ease: "easeOut" }}
      className="fixed right-6 top-1/3
                 w-[240px] max-h-[40vh]
                 flex flex-col
                 rounded-2xl border border-border/50
                 bg-white/80 backdrop-blur-md shadow-lg
                 z-30 md:right-6 sm:right-3
                 overflow-hidden"
    >
      {/* Header */}
      <div className="flex-shrink-0 flex items-center justify-between px-3 py-2 border-b border-border/40 bg-white/70 backdrop-blur-sm sticky top-0 z-10">
        <div className="flex items-center gap-1">
          <CheckSquare size={16} className="text-primary" />
          <h3 className="text-sm font-semibold text-text-primary">선택 지역</h3>
        </div>
        <button
          onClick={close}
          className="p-1.5 rounded-md hover:bg-gray-100 text-gray-500 hover:text-gray-700 transition"
          aria-label="닫기"
        >
          <X size={16} strokeWidth={2} />
        </button>
      </div>

      {/* ✅ Scrollable Body */}
      <div
        className="flex-1 overflow-y-auto px-3 py-3 space-y-3
                   scrollbar-thin scrollbar-thumb-gray-300 scrollbar-track-transparent"
      >
        {/* 매매 섹션 */}
        {hasSell && (
          <section>
            <h5 className="text-xs font-semibold text-gray-500 mb-1">매매</h5>
            <div className="space-y-1.5">
              {Array.from(selectedSellRegions).map((region) => (
                <div
                  key={`sell-${region.code}`}
                  className="flex items-center justify-between px-2.5 py-1.5 rounded-md border border-border/50
                             bg-primary-bg/40 hover:bg-primary-bg/60 transition text-sm"
                >
                  <span className="truncate">{region.name}</span>
                  <button
                    onClick={() => toggleSellRegion(region)}
                    className="text-primary text-xs hover:underline"
                  >
                    삭제
                  </button>
                </div>
              ))}
            </div>
          </section>
        )}

        {/* 임대차 섹션 */}
        {hasLease && (
          <section>
            <h5 className="text-xs font-semibold text-gray-500 mb-1">임대차</h5>
            <div className="space-y-1.5">
              {Array.from(selectedLeaseRegions).map((region) => (
                <div
                  key={`lease-${region.code}`}
                  className="flex items-center justify-between px-2.5 py-1.5 rounded-md border border-border/50
                             bg-primary-bg/40 hover:bg-primary-bg/60 transition text-sm"
                >
                  <span className="truncate">{region.name}</span>
                  <button
                    onClick={() => toggleLeaseRegion(region)}
                    className="text-primary text-xs hover:underline"
                  >
                    삭제
                  </button>
                </div>
              ))}
            </div>
          </section>
        )}

        {/* 아무 것도 없을 때 */}
        {!hasSell && !hasLease && (
          <p className="text-center text-xs text-gray-400 py-6">선택된 지역이 없습니다.</p>
        )}
      </div>
    </motion.aside>
  );
};

const CardOpenButton = ({ open }) => {
  const { selectedSellRegions = new Set(), selectedLeaseRegions = new Set() } = useSearchStore();
  const selectedCount = selectedSellRegions.size + selectedLeaseRegions.size;

  return (
    <motion.button
      key="collapsed"
      initial={{ scale: 0.8, opacity: 0 }}
      animate={{ scale: 1, opacity: 1 }}
      exit={{ scale: 0.8, opacity: 0 }}
      transition={{ duration: 0.2 }}
      onClick={open}
      className="flex items-center gap-2 px-3 py-2 rounded-full
                       bg-primary text-white shadow-lg hover:bg-primary/90
                       transition text-sm font-medium"
    >
      <CheckCircle2 size={16} />
      <span>선택 {selectedCount}개</span>
      <ChevronUp size={14} className="opacity-80" />
    </motion.button>
  );
};

export default function SelectedRegionsCard({ isOpen, open, close }) {
  return (
    <div className="fixed right-6 top-1/3 z-30 md:right-6 sm:right-3">
      <AnimatePresence mode="wait">
        {isOpen ? <OpenedCard close={close} /> : <CardOpenButton open={open} />}
      </AnimatePresence>
    </div>
  );
}
