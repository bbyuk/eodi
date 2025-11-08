import { Search } from "lucide-react";

export default function ResultCard({ data }) {
  console.log(data);
  return (
    <article className="border border-gray-200 rounded-xl bg-white/80 shadow-sm hover:shadow-md transition-all duration-300 p-5 flex flex-col justify-between">
      <div>
        <h3 className="text-lg font-semibold text-gray-900">{data.building}</h3>
        <p className="text-sm text-gray-500">{data.region}</p>

        <div className="mt-3 space-y-1 text-sm">
          <p>
            <span className="font-medium text-gray-800">{data.dealType}</span> ·{" "}
            <span className="text-gray-600">{data.area}㎡</span> ·{" "}
            <span className="text-gray-600">{data.floor}</span>
          </p>
          <p className="text-blue-600 font-semibold">{data.price}</p>
          <p className="text-xs text-gray-400">{data.date} 거래</p>
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
