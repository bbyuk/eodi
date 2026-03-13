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

  const hasCoord = !!data.naverUrl;

  return (
    <article className="relative border border-gray-200 rounded-xl bg-white/80 shadow-sm hover:shadow-md transition-all duration-300 p-5 flex flex-col justify-between">
      {data.dateOfRegistration && (
        <div className="absolute top-3 right-3 flex items-center gap-1.5 px-2.5 py-1 rounded-full bg-green-50 border border-green-200 text-green-700 text-[11px] font-medium shadow-sm">
          <CheckCircle className="w-3.5 h-3.5 text-green-600" />
          <span>등기</span>
        </div>
      )}

      <div>
        <h3 className="text-lg font-semibold text-gray-900 truncate">
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

      <div className="mt-4 flex items-center gap-2">
        {/* 메인 버튼 */}
        <div className={`relative ${!hasCoord ? "group flex-1" : "flex-1"}`}>
          <Button
            as="a"
            href={data.naverUrl || "https://new.land.naver.com/"}
            target="_blank"
            rel="noopener noreferrer"
            fullWidth
          >
            <Search className="w-4 h-4 relative top-[1px]" />
            네이버 부동산 보기
          </Button>

          {!hasCoord && (
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

        {/* 복사 버튼 (좌표 없을 때만) */}
        {!hasCoord && (
          <div className="relative group shrink-0">
            <button
              type="button"
              onClick={() => onCopyButtonClick(data.targetName)}
              className="
                h-10 w-10
                rounded-md
                border border-gray-200
                bg-white
                text-gray-600
                hover:bg-gray-50
                flex items-center justify-center
              "
            >
              <Copy className="w-4 h-4" />
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
