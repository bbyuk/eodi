"use client";

import ResultGrid from "@/app/search/step2/_components/ResultGrid";
import ResultCard from "@/app/search/step2/_components/ResultCard";

export default function DealResultSection({
  type,
  info,
  loadMoreRef,
  isInitialLoading = false,
  isFetchingMore = false,
  metadata,
  onCopyButtonClick,
}) {
  const items = info?.data ?? [];
  const isEmpty = !isInitialLoading && items.length === 0;

  const renderEmpty = () => {
    return (
      <div className="col-span-full flex flex-col items-center justify-center py-20 text-gray-400">
        <p className="text-base font-medium">조건에 맞는 거래가 없습니다</p>
        <p className="mt-2 text-sm">지역이나 예산 조건을 변경해 보세요</p>
      </div>
    );
  };

  const renderLoading = () => {
    return (
      <div className="col-span-full flex items-center justify-center py-20 text-gray-400">
        실거래 데이터를 조회하고 있어요...
      </div>
    );
  };

  const renderItems = () => {
    return items.map((item) => (
      <ResultCard
        key={item.id}
        data={item}
        onCopyButtonClick={onCopyButtonClick}
        dealType={
          type === "sell"
            ? { code: "sell", label: "매매" }
            : {
                code: item.monthlyRent === 0 ? "charter" : "monthly-rent",
                label: item.monthlyRent === 0 ? "전세" : "월세",
              }
        }
      />
    ));
  };

  return (
    <div className="relative">
      <div className="mb-3 text-right text-xs text-gray-400 sm:absolute sm:right-0 sm:-top-6 sm:mb-0">
        {metadata?.updateDate} 업데이트
      </div>

      <ResultGrid>
        {isInitialLoading ? renderLoading() : isEmpty ? renderEmpty() : renderItems()}

        {isFetchingMore && (
          <div className="col-span-full py-4 text-center text-sm text-gray-400">
            더 불러오는 중...
          </div>
        )}

        <div ref={loadMoreRef} className="h-6" />
      </ResultGrid>
    </div>
  );
}
