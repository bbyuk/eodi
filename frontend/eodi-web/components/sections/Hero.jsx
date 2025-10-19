import Link from "next/link";

export default function Hero({ onStart }) {
  return (
    <section className="relative text-center px-6 py-[10vh] max-w-4xl mx-auto">
      <div className="-translate-y-[8vh]">
        <h1 className="text-4xl md:text-6xl font-semibold text-gray-900 leading-tight">
          내 예산으로 살 수 있는 동네
          <br className="hidden md:block" /> 바로 확인해보세요.
        </h1>
        <p className="mt-4 text-lg text-gray-600">
          실거래가 데이터를 기반으로 매수·전월세 가능한 지역을 찾아드립니다.
        </p>
        <Link href="/search">
          <button className="mt-8 px-6 py-3 bg-blue-600 text-white font-medium rounded-xl shadow hover:bg-blue-700 transition-colors">
            내 예산으로 알아보기
          </button>
        </Link>
      </div>
    </section>
  );
}
