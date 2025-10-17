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
      className={`fixed top-0 w-full z-30 transition-all duration-300 ${
        isScrolled ? "backdrop-blur-md bg-white/60" : "bg-transparent"
      }`}
    >
      <div className="max-w-7xl mx-auto px-6 flex items-center justify-between h-16">
        <Link href="/" className="text-lg font-semibold tracking-tight">
          Lorem.
        </Link>
        <nav className="flex items-center gap-6 text-sm font-medium text-slate-600">
          <Link href="#sell" className="hover:text-slate-900">
            Sell
          </Link>
          <Link href="#rent" className="hover:text-slate-900">
            Rent
          </Link>
        </nav>
      </div>
    </header>
  );
}
