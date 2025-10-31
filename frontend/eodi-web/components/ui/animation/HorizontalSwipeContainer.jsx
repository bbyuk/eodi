"use client";
import { useEffect, useRef, useState } from "react";

export default function HorizontalSwipeContainer({ children, className = "", fadeColor = "#fff" }) {
  const ref = useRef(null);
  const [atStart, setAtStart] = useState(true);
  const [atEnd, setAtEnd] = useState(false);
  const [isDragging, setIsDragging] = useState(false);
  const state = useRef({
    isDown: false,
    startX: 0,
    scrollLeft: 0,
    lastX: 0,
    velocity: 0,
    rafId: null,
  });

  useEffect(() => {
    const el = ref.current;
    const s = state.current;
    if (!el) return;

    const dragThreshold = 8; // ✅ 8px 이상 이동 시에만 드래그로 간주

    /** Fade 업데이트 */
    const handleScroll = () => {
      const max = el.scrollWidth - el.clientWidth;
      setAtStart(el.scrollLeft <= 1);
      setAtEnd(el.scrollLeft >= max - 1);
    };
    handleScroll();
    el.addEventListener("scroll", handleScroll, { passive: true });

    /** 드래그 스크롤 */
    const start = (x) => {
      s.isDown = true;
      s.startX = x;
      s.scrollLeft = el.scrollLeft;
      s.lastX = x;
      s.velocity = 0;
      el.classList.add("cursor-grabbing");
      cancelAnimationFrame(s.rafId);
      setIsDragging(false); // 새 클릭마다 초기화
    };

    const stop = () => {
      if (!s.isDown) return;
      s.isDown = false;
      el.classList.remove("cursor-grabbing");

      // 🔹 드래그가 실제로 발생했을 경우만 관성 스크롤
      if (isDragging) momentumScroll();

      // 🔹 클릭 이벤트가 막히지 않도록 약간 지연 후 해제
      setTimeout(() => setIsDragging(false), 80);
    };

    const move = (x) => {
      if (!s.isDown) return;
      const diffX = x - s.startX;

      // 일정 거리 이상 움직였을 때만 드래그 시작으로 간주
      if (!isDragging && Math.abs(diffX) > dragThreshold) {
        setIsDragging(true);
      }

      if (isDragging) {
        const delta = x - s.lastX;
        s.lastX = x;
        s.velocity = delta;
        el.scrollLeft -= delta * 1.2;
      }
    };

    const momentumScroll = () => {
      const decay = 0.94;
      const animate = () => {
        s.velocity *= decay;
        if (Math.abs(s.velocity) > 0.5) {
          el.scrollLeft -= s.velocity;
          s.rafId = requestAnimationFrame(animate);
        } else cancelAnimationFrame(s.rafId);
      };
      requestAnimationFrame(animate);
    };

    /** 휠 이벤트: 상하 → 좌우 스크롤 */
    const handleWheel = (e) => {
      if (Math.abs(e.deltaY) > Math.abs(e.deltaX)) {
        e.preventDefault();
        el.scrollLeft += e.deltaY;
      }
    };

    /** 이벤트 등록 */
    el.addEventListener("wheel", handleWheel, { passive: false });
    el.addEventListener("mousedown", (e) => start(e.pageX));
    el.addEventListener("mousemove", (e) => move(e.pageX));
    el.addEventListener("mouseup", stop);
    el.addEventListener("mouseleave", stop);
    el.addEventListener("touchstart", (e) => start(e.touches[0].pageX), { passive: true });
    el.addEventListener("touchmove", (e) => move(e.touches[0].pageX), { passive: true });
    el.addEventListener("touchend", stop);

    return () => {
      cancelAnimationFrame(s.rafId);
      el.removeEventListener("scroll", handleScroll);
      el.removeEventListener("wheel", handleWheel);
    };
  }, [isDragging]);

  return (
    <div className="relative py-2 overflow-hidden">
      {/* 좌우 fade */}
      {!atStart && (
        <div
          className="absolute left-0 top-0 h-full w-10 pointer-events-none z-10"
          style={{
            background: `linear-gradient(to right, ${fadeColor} 0%, ${fadeColor}e6 30%, transparent 100%)`,
          }}
        />
      )}
      {!atEnd && (
        <div
          className="absolute right-0 top-0 h-full w-10 pointer-events-none z-10"
          style={{
            background: `linear-gradient(to left, ${fadeColor} 0%, ${fadeColor}e6 30%, transparent 100%)`,
          }}
        />
      )}

      {/* 스크롤 영역 */}
      <div
        ref={ref}
        className={`overflow-x-auto scrollbar-none cursor-grab px-2 ${className}`}
        style={{
          WebkitOverflowScrolling: "touch",
          scrollBehavior: "auto",
          overscrollBehaviorX: "contain",
        }}
      >
        {/* ✅ 드래그 중일 때만 클릭 차단 */}
        <div
          className={`flex gap-2 whitespace-nowrap transition-all ${
            isDragging ? "pointer-events-none select-none" : "pointer-events-auto"
          }`}
        >
          {children}
        </div>
      </div>
    </div>
  );
}
