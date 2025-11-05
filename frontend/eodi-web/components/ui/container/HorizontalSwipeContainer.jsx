"use client";
import { useEffect, useRef, useState } from "react";

/**
 * 범용 좌우 스와이프 컨테이너
 * - 마우스/터치 스와이프 모두 지원
 * - 관성 스크롤 (momentum)
 * - 드래그 중 클릭 차단
 * - fadeColor 로 좌우 fade 효과
 */
export default function HorizontalSwipeContainer({ children, className = "", fadeColor = "#fff" }) {
  const ref = useRef(null);
  const [atStart, setAtStart] = useState(true);
  const [atEnd, setAtEnd] = useState(false);
  const [isDragging, setIsDragging] = useState(false);

  const state = useRef({
    isDown: false,
    startX: 0,
    lastX: 0,
    scrollLeft: 0,
    velocity: 0,
    rafId: null,
    lastTime: 0,
  });

  useEffect(() => {
    const el = ref.current;
    if (!el) return;

    const s = state.current;
    const dragThreshold = 5; // 클릭/드래그 구분 픽셀
    const momentumDecay = 0.95;

    const updateFade = () => {
      const max = el.scrollWidth - el.clientWidth;
      setAtStart(el.scrollLeft <= 1);
      setAtEnd(el.scrollLeft >= max - 1);
    };

    const start = (x) => {
      cancelAnimationFrame(s.rafId);
      s.isDown = true;
      s.startX = x;
      s.lastX = x;
      s.scrollLeft = el.scrollLeft;
      s.velocity = 0;
      s.lastTime = performance.now();
      el.classList.add("cursor-grabbing");
      setIsDragging(false);
    };

    const move = (x) => {
      if (!s.isDown) return;

      const dx = x - s.lastX;
      const now = performance.now();
      const dt = now - s.lastTime || 16;

      s.velocity = (dx / dt) * 16; // velocity 보정 (px/frame 단위)
      s.lastTime = now;
      s.lastX = x;

      // 실제 스크롤 이동
      el.scrollLeft -= dx;

      // 일정 거리 이상 움직이면 드래그로 간주 (클릭 차단용)
      if (!isDragging && Math.abs(x - s.startX) > dragThreshold) {
        setIsDragging(true);
      }
    };

    const stop = () => {
      if (!s.isDown) return;
      s.isDown = false;
      el.classList.remove("cursor-grabbing");

      // 관성 스크롤
      const momentum = () => {
        s.velocity *= momentumDecay;
        if (Math.abs(s.velocity) > 0.5) {
          el.scrollLeft -= s.velocity;
          s.rafId = requestAnimationFrame(momentum);
          updateFade();
        } else {
          cancelAnimationFrame(s.rafId);
        }
      };
      if (Math.abs(s.velocity) > 0.5) {
        s.rafId = requestAnimationFrame(momentum);
      }

      setTimeout(() => setIsDragging(false), 80);
      updateFade();
    };

    // 터치/마우스 이벤트
    const onMouseDown = (e) => start(e.pageX);
    const onMouseMove = (e) => move(e.pageX);
    const onMouseUp = stop;
    const onMouseLeave = stop;

    const onTouchStart = (e) => start(e.touches[0].pageX);
    const onTouchMove = (e) => move(e.touches[0].pageX);
    const onTouchEnd = stop;

    const onWheel = (e) => {
      if (Math.abs(e.deltaY) > Math.abs(e.deltaX)) {
        e.preventDefault();
        el.scrollLeft += e.deltaY;
      }
      updateFade();
    };

    el.addEventListener("mousedown", onMouseDown);
    el.addEventListener("mousemove", onMouseMove);
    el.addEventListener("mouseup", onMouseUp);
    el.addEventListener("mouseleave", onMouseLeave);
    el.addEventListener("touchstart", onTouchStart, { passive: true });
    el.addEventListener("touchmove", onTouchMove, { passive: true });
    el.addEventListener("touchend", onTouchEnd);
    el.addEventListener("wheel", onWheel, { passive: false });
    el.addEventListener("scroll", updateFade, { passive: true });

    updateFade();

    return () => {
      cancelAnimationFrame(s.rafId);
      el.removeEventListener("mousedown", onMouseDown);
      el.removeEventListener("mousemove", onMouseMove);
      el.removeEventListener("mouseup", onMouseUp);
      el.removeEventListener("mouseleave", onMouseLeave);
      el.removeEventListener("touchstart", onTouchStart);
      el.removeEventListener("touchmove", onTouchMove);
      el.removeEventListener("touchend", onTouchEnd);
      el.removeEventListener("wheel", onWheel);
      el.removeEventListener("scroll", updateFade);
    };
  }, [isDragging]);

  return (
    <div className="relative py-2 overflow-hidden">
      {/* Fade 영역 */}
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
        className={`overflow-x-auto scrollbar-none cursor-grab select-none ${className}`}
        style={{
          WebkitOverflowScrolling: "touch",
          scrollBehavior: "auto",
          overscrollBehaviorX: "contain",
        }}
      >
        <div
          className={`flex gap-2 whitespace-nowrap ${
            isDragging ? "pointer-events-none" : "pointer-events-auto"
          }`}
        >
          {children}
        </div>
      </div>
    </div>
  );
}
