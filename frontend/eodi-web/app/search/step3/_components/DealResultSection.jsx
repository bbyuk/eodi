import ResultGrid from "@/app/search/step3/_components/ResultGrid";
import ResultCard from "@/app/search/step3/_components/ResultCard";

export default function DealResultSection({ type, info, loadMoreRef }) {
  return (
    <ResultGrid>
      {info.data.map((item) => (
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
      ))}
      <div ref={loadMoreRef} className="h-6" />
    </ResultGrid>
  );
}
