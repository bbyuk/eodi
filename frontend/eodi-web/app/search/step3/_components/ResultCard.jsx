import { CheckCircle, Search } from "lucide-react";
import { formatWon } from "@/app/search/_util/util";
import { definedHousingType } from "@/const/code";

/**
 * const MOCK_DATA = [
 *   {
 *     id: 1,
 *     region: "강남구 삼성동",
 *     price: "18억 2,000만원",
 *     dealType: "매매",
 *     building: "래미안 삼성1차",
 *     area: "84",
 *     floor: "15층",
 *     date: "2025.10.15",
 *     url: "https://new.land.naver.com/complexes?ms=강남구 삼성동 아파트",
 *   },
 *   {
 *     id: 2,
 *     region: "노원구 중계동",
 *     price: "6억 5,000만원",
 *     dealType: "매매",
 *     building: "중계주공3단지",
 *     area: "59.21",
 *     floor: "8층",
 *     date: "2025.09.10",
 *     url: "https://new.land.naver.com/complexes?ms=노원구 중계동 아파트",
 *   },
 * ];
 * @param data 결과 데이터
 * @param dealType 거래 유형
 * @returns {JSX.Element}
 * @constructor
 */

export default function ResultCard({ data, dealType }) {
  const priceLabel = () => {
    if (dealType.code === "sell") {
      return formatWon(data.prcie);
    } else if (dealType.code === "charter") {
      return formatWon(data.deposit);
    } else if (dealType.code === "monthly-rent") {
      return `${formatWon(data.deposit)} · ${data.monthlyRent}`;
    }
    return formatWon(0);
  };

  return (
    <article className="relative border border-gray-200 rounded-xl bg-white/80 shadow-sm hover:shadow-md transition-all duration-300 p-5 flex flex-col justify-between">
      {data.dateOfRegistration && (
        <div className="absolute top-3 right-3 flex items-center gap-1.5 px-2.5 py-1 rounded-full bg-green-50 border border-green-200 text-green-700 text-[11px] font-medium shadow-sm">
          <CheckCircle className="w-3.5 h-3.5 text-green-600" />
          <span>등기</span>
        </div>
      )}
      <div>
        <h3 className="text-lg font-semibold text-gray-900">
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

      <a
        href={data.naverUrl}
        target="_blank"
        rel="noopener noreferrer"
        className="mt-5 w-full flex items-center justify-center gap-2 py-2 rounded-md bg-blue-600 text-white font-medium text-sm hover:bg-blue-700 transition cursor-pointer"
      >
        <Search className="w-4 h-4 relative top-[1px]" />
        네이버 부동산에서 더 보기
      </a>
    </article>
  );
}
