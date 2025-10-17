import Link from "next/link";

export default function Hero({ onStart }) {
  return (
    <section className="flex flex-col items-center justify-center text-center min-h-[80vh] px-6">
      <div className="max-w-3xl">
        <h1 className="text-5xl md:text-6xl font-bold tracking-tight text-slate-900 mb-6 leading-tight">
          Find your next home with ease
        </h1>
        <p className="text-lg text-slate-600 mb-10 max-w-xl mx-auto">
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Enter your budget and explore
          where you can live.
        </p>

        <Link
          href="/search"
          className="px-8 py-4 bg-blue-600 text-white text-lg font-medium rounded-lg hover:bg-blue-700 transition"
        >
          Get Started
        </Link>
      </div>
    </section>
  );
}
