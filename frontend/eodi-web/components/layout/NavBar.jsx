"use client";
import { useState, useEffect } from "react";
import Link from "next/link";
import { usePathname } from "next/navigation";
import { Menu, X } from "lucide-react";
import { siteNavigation } from "@/app/_data/siteNavigation";
import ActionButton from "@/components/ui/ActionButton";

export default function Navbar() {
  const [isScrolled, setIsScrolled] = useState(false);
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const pathname = usePathname();

  useEffect(() => {
    const handleScroll = () => setIsScrolled(window.scrollY > 10);
    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  useEffect(() => {
    setIsMenuOpen(false);
  }, [pathname]);

  const isFieldNotesPage = pathname?.startsWith("/field-notes");

  return (
    <header
      className={`fixed top-0 w-full z-30 transition-all duration-300 ${
        isScrolled
          ? "border-b border-border bg-white/88 backdrop-blur-xl shadow-[0_12px_40px_rgba(15,23,42,0.06)]"
          : "border-b border-transparent bg-transparent"
      }`}
    >
      <div className="mx-auto flex h-16 max-w-7xl items-center justify-between px-4 sm:px-6 lg:px-8">
        <Link
          href="/"
          className="text-base font-semibold tracking-tight text-text-primary sm:text-lg"
        >
          어디살까
        </Link>

        <nav className="hidden items-center gap-7 text-sm font-medium text-slate-600 lg:flex">
          {siteNavigation.map((item) => (
            <Link
              key={item.href}
              href={item.href}
              className="transition-colors hover:text-slate-950"
            >
              {item.label}
            </Link>
          ))}
        </nav>

        <div className="hidden items-center gap-3 lg:flex">
          <ActionButton
            href={isFieldNotesPage ? "/" : "/field-notes/new"}
            variant="frosted"
            size="sm"
          >
            {isFieldNotesPage ? "홈으로" : "새 임장노트"}
          </ActionButton>
        </div>

        <button
          type="button"
          onClick={() => setIsMenuOpen((prev) => !prev)}
          className="inline-flex h-11 w-11 items-center justify-center rounded-full border border-slate-300 bg-white/80 text-slate-700 transition hover:bg-white lg:hidden"
          aria-label="메뉴 열기"
          aria-expanded={isMenuOpen}
        >
          {isMenuOpen ? <X className="h-5 w-5" /> : <Menu className="h-5 w-5" />}
        </button>
      </div>

      {isMenuOpen && (
        <div className="border-t border-slate-200 bg-white/95 px-4 py-4 backdrop-blur-xl lg:hidden">
          <div className="mx-auto flex max-w-7xl flex-col gap-2">
            {siteNavigation.map((item) => (
              <Link
                key={item.href}
                href={item.href}
                className="rounded-2xl px-4 py-3 text-sm font-semibold text-slate-700 transition hover:bg-slate-100"
              >
                {item.label}
              </Link>
            ))}
            <ActionButton
              href={isFieldNotesPage ? "/" : "/field-notes/new"}
              variant="dark"
              size="md"
              rounded="xl"
              block
              className="mt-2"
            >
              {isFieldNotesPage ? "홈으로 이동" : "새 임장노트 만들기"}
            </ActionButton>
          </div>
        </div>
      )}
    </header>
  );
}
