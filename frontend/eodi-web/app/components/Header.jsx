"use client";

import { useState } from "react";
import { Menu, X } from "lucide-react";

export default function Header() {
  const [open, setOpen] = useState(false);

  return (
    <header className="px-8 py-5 flex items-center justify-between backdrop-blur-md bg-white/30 border-b border-white/40 shadow-sm">
      <h1 className="text-2xl font-semibold tracking-tight text-gray-800">
        어디살까<span className="text-blue-600">?</span>
      </h1>

      <nav className="hidden md:flex space-x-8 text-gray-700 font-medium">
        <a href="#" className="hover:text-blue-600 transition-colors">
          홈
        </a>
        <a href="#" className="hover:text-blue-600 transition-colors">
          지역 검색
        </a>
        <a href="#" className="hover:text-blue-600 transition-colors">
          대출 계산기
        </a>
        <a href="#" className="hover:text-blue-600 transition-colors">
          커뮤니티
        </a>
      </nav>

      <button
        className="md:hidden text-gray-700 hover:text-blue-600 transition-colors"
        onClick={() => setOpen(!open)}
      >
        {open ? <X size={24} /> : <Menu size={24} />}
      </button>

      {open && (
        <div className="absolute top-16 left-0 w-full bg-white/80 backdrop-blur-lg border-b border-gray-200 shadow-md md:hidden z-10">
          <nav className="flex flex-col items-center py-4 space-y-3 text-gray-700 font-medium">
            <a href="#" className="hover:text-blue-600" onClick={() => setOpen(false)}>
              홈
            </a>
            <a href="#" className="hover:text-blue-600" onClick={() => setOpen(false)}>
              지역 검색
            </a>
            <a href="#" className="hover:text-blue-600" onClick={() => setOpen(false)}>
              대출 계산기
            </a>
            <a href="#" className="hover:text-blue-600" onClick={() => setOpen(false)}>
              커뮤니티
            </a>
          </nav>
        </div>
      )}
    </header>
  );
}
