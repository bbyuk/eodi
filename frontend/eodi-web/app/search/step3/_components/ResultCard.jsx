import { Search } from "lucide-react";
import { formatWon } from "@/app/search/_util/util";

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
 * @param data
 * @returns {JSX.Element}
 * @constructor
 */

export default function ResultCard({ data, dealType }) {
  console.log(data);
  return (
    <article className="border border-gray-200 rounded-xl bg-white/80 shadow-sm hover:shadow-md transition-all duration-300 p-5 flex flex-col justify-between">
      <div>
        <h3 className="text-lg font-semibold text-gray-900">
          {data.targetName ? `${data.targetName}` : ""}
        </h3>
        <p className="text-sm text-gray-500">{data.legalDongFullName}</p>

        <div className="mt-3 space-y-1 text-sm">
          <p>
            <span className="font-medium text-gray-800">{dealType}</span> ·{" "}
            <span className="text-gray-600">{data.netLeasableArea}㎡</span>
            <span className="text-gray-600">{data.floor ? ` · ${data.floor}층` : ""}</span>
          </p>
          <p className="text-blue-600 font-semibold">{formatWon(data.price)}</p>
          <p className="text-xs text-gray-400">
            {data.contractDate ? `${data.contractDate} 계약` : ""}
          </p>
        </div>
      </div>

      <a
        href={data.url}
        target="_blank"
        rel="noopener noreferrer"
        className="mt-5 w-full flex items-center justify-center gap-2 py-2 rounded-md bg-blue-600 text-white font-medium text-sm hover:bg-blue-700 transition"
      >
        <Search className="w-4 h-4 relative top-[1px]" />
        네이버 부동산에서 보기
      </a>
    </article>
  );
}
