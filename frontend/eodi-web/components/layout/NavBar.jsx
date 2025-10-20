"use client";
import { useState, useEffect } from "react";
import Link from "next/link";

export default function Navbar() {
  const [isScrolled, setIsScrolled] = useState(false);

  useEffect(() => {
    const handleScroll = () => setIsScrolled(window.scrollY > 10);
    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  return (
    <header
      className={`fixed top-0 w-full z-30 transition-all duration-300 border-b border-transparent ${
        isScrolled ? "backdrop-blur-md bg-white/70 border-border" : "bg-transparent"
      }`}
    >
      <div className="max-w-7xl mx-auto px-6 flex items-center justify-between h-16">
        {/* 브랜드 로고 / 이름 */}
        <Link
          href="/"
          className="text-lg font-semibold tracking-tight text-text-primary hover:text-primary transition-colors"
        >
          어디살까?
        </Link>

        {/* 내비게이션 */}
        {/*<nav className="flex items-center gap-6 text-sm font-medium text-text-secondary">*/}
        {/*  <Link href="#sell" className="transition-colors hover:text-primary">*/}
        {/*    Sell*/}
        {/*  </Link>*/}
        {/*  <Link href="#rent" className="transition-colors hover:text-primary">*/}
        {/*    Rent*/}
        {/*  </Link>*/}
        {/*</nav>*/}
      </div>
    </header>
  );
}
