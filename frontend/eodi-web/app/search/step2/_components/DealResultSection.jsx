import ResultGrid from "@/app/search/step2/_components/ResultGrid";
import ResultCard from "@/app/search/step2/_components/ResultCard";

export default function DealResultSection({ type, info, loadMoreRef }) {
  const placeholder = () => {
    return (
      <div className="col-span-full flex flex-col items-center justify-center py-20 text-gray-400">
        <p className="text-base font-medium">조건에 맞는 거래가 없습니다</p>
        <p className="text-sm mt-2">지역이나 예산 조건을 변경해 보세요</p>
      </div>
    );
  };

  const render = () => {
    return info.data.map((item) => (
      <ResultCard
        key={item.id}
        data={item}
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
    <ResultGrid>
      {info.data.length > 0 ? render() : placeholder()}
      <div ref={loadMoreRef} className="h-6" />
    </ResultGrid>
  );
}
