import Link from "next/link";

export default function Hero({ onStart }) {
  return (
    <section className="flex flex-col items-center justify-center text-center min-h-[80vh] px-6">
      <div className="max-w-3xl">
        {/* 헤딩 */}
        <h1 className="text-5xl md:text-6xl font-bold tracking-tight text-text-primary mb-6 leading-tight">
          Find your next home with ease
        </h1>

        {/* 설명 */}
        <p className="text-lg text-text-secondary mb-10 max-w-xl mx-auto">
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Enter your budget and explore
          where you can live.
        </p>

        {/* CTA 버튼 */}
        <Link
          href="/search"
          className="inline-block px-8 py-4 rounded-lg font-medium text-lg text-white bg-primary hover:bg-primary-hover transition-all duration-200 shadow-md hover:translate-y-[1px]"
        >
          Get Started
        </Link>
      </div>
    </section>
  );
}
