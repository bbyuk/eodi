import { CheckCircle, Search, Copy } from "lucide-react";
import { formatWon } from "@/app/search/_util/util";
import { definedHousingType } from "@/const/code";
import Button from "@/components/ui/input/Button";

export default function ResultCard({ data, dealType, onCopyButtonClick }) {
  const priceLabel = () => {
    if (dealType.code === "sell") {
      return formatWon(data.price);
    } else if (dealType.code === "charter") {
      return formatWon(data.deposit);
    } else if (dealType.code === "monthly-rent") {
      return `${formatWon(data.deposit)} · ${data.monthlyRent}`;
    }
    return formatWon(0);
  };

  return (
    <article className="relative flex h-full flex-col justify-between rounded-xl border border-gray-200 bg-white/90 p-4 shadow-sm transition-all duration-300 hover:shadow-md sm:p-5">
      {data.dateOfRegistration && (
        <div className="absolute right-3 top-3 flex items-center gap-1.5 rounded-full border border-green-200 bg-green-50 px-2.5 py-1 text-[11px] font-medium text-green-700 shadow-sm">
          <CheckCircle className="w-3.5 h-3.5 text-green-600" />
          <span>등기</span>
        </div>
      )}

      <div>
        <h3 className="truncate pr-14 text-lg font-semibold text-gray-900">
          {data.targetName ? `${data.targetName}` : ""}
        </h3>
        <p className="text-sm text-gray-500">{data.legalDongFullName}</p>

        <div className="mt-3 space-y-1 text-sm">
          <p>
            <span className="font-medium text-gray-800">{dealType.label}</span> ·{" "}
            <span className="text-gray-600">{data.netLeasableArea}㎡</span>
            <span className="text-gray-600">{data.floor ? ` · ${data.floor}층` : ""}</span>
          </p>
          <p>
            <span className="text-gray-600">{definedHousingType[data.housingType].name}</span>
          </p>
          <p className="text-blue-600 font-semibold">{priceLabel()}</p>
          <p className="text-xs text-gray-400">
            {data.contractDate ? `${data.contractDate} 계약` : ""}
          </p>
        </div>
      </div>

      <div className="mt-4 flex flex-col gap-2 sm:flex-row sm:items-center">
        <div className={`relative ${!data.hasLink ? "group flex-1" : "flex-1"}`}>
          <Button as="a" href={data.naverUrl} target="_blank" rel="noopener noreferrer" fullWidth>
            <Search className="w-4 h-4 relative top-[1px]" />
            네이버 부동산 보기
          </Button>

          {!data.hasLink && (
            <div
              className="
          pointer-events-none
          absolute
          left-1/2
          top-full
          z-10
          mt-2
          hidden
          -translate-x-1/2
          whitespace-nowrap
          rounded-md
          bg-gray-900
          px-2 py-1
          text-xs
          text-white
          shadow-md
          group-hover:block
        "
            >
              위치 정보가 없어 이름 검색으로 확인할 수 있어요
            </div>
          )}
        </div>

        {!data.hasLink && (
          <div className="relative group shrink-0 sm:self-stretch">
            <button
              type="button"
              onClick={() => onCopyButtonClick(data.targetName)}
              className="flex h-10 w-full items-center justify-center rounded-md border border-gray-200 bg-white px-4 text-gray-600 hover:bg-gray-50 sm:w-10 sm:px-0"
            >
              <Copy className="w-4 h-4" />
              <span className="ml-2 text-sm font-medium sm:hidden">검색어 복사</span>
            </button>

            <div
              className="
                pointer-events-none
                absolute
                right-0
                top-full
                z-10
                mt-2
                hidden
                whitespace-nowrap
                rounded-md
                bg-gray-900
                px-2 py-1
                text-xs
                text-white
                shadow-md
                group-hover:block
              "
            >
              검색어 복사
            </div>
          </div>
        )}
      </div>
    </article>
  );
}
