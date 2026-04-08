"use client";

import { ChevronLeft, ChevronRight } from "lucide-react";

const WEEKDAYS = ["일", "월", "화", "수", "목", "금", "토"];

function getMonthStart(date) {
  return new Date(date.getFullYear(), date.getMonth(), 1);
}

function getMonthLabel(date) {
  return `${date.getFullYear()}년 ${date.getMonth() + 1}월`;
}

function isSameDate(firstDate, secondDate) {
  if (!firstDate || !secondDate) {
    return false;
  }

  return (
    firstDate.getFullYear() === secondDate.getFullYear() &&
    firstDate.getMonth() === secondDate.getMonth() &&
    firstDate.getDate() === secondDate.getDate()
  );
}

function buildCalendarDays(monthDate) {
  const monthStart = getMonthStart(monthDate);
  const calendarStart = new Date(monthStart);
  calendarStart.setDate(monthStart.getDate() - monthStart.getDay());

  return Array.from({ length: 42 }, (_, index) => {
    const date = new Date(calendarStart);
    date.setDate(calendarStart.getDate() + index);

    return {
      date,
      inCurrentMonth: date.getMonth() === monthDate.getMonth(),
    };
  });
}

export function Calendar({ selected, onSelect, month, onMonthChange, className = "" }) {
  const visibleMonth = month ? getMonthStart(month) : getMonthStart(new Date());
  const days = buildCalendarDays(visibleMonth);
  const today = new Date();

  const moveMonth = (monthOffset) => {
    onMonthChange?.(new Date(visibleMonth.getFullYear(), visibleMonth.getMonth() + monthOffset, 1));
  };

  return (
    <div className={className}>
      <div className="flex items-center justify-between">
        <button
          type="button"
          onClick={() => moveMonth(-1)}
          aria-label="이전 달"
          className="inline-flex h-9 w-9 items-center justify-center rounded-full border border-slate-200 bg-white text-slate-500 transition hover:bg-slate-50 hover:text-slate-900"
        >
          <ChevronLeft className="h-4 w-4" />
        </button>
        <p className="text-base font-semibold text-slate-950">{getMonthLabel(visibleMonth)}</p>
        <button
          type="button"
          onClick={() => moveMonth(1)}
          aria-label="다음 달"
          className="inline-flex h-9 w-9 items-center justify-center rounded-full border border-slate-200 bg-white text-slate-500 transition hover:bg-slate-50 hover:text-slate-900"
        >
          <ChevronRight className="h-4 w-4" />
        </button>
      </div>

      <div className="mt-5 grid grid-cols-7 gap-1 text-center text-xs font-semibold text-slate-400">
        {WEEKDAYS.map((weekday) => (
          <div key={weekday} className="py-1">
            {weekday}
          </div>
        ))}
      </div>

      <div className="mt-2 grid grid-cols-7 gap-1">
        {days.map(({ date, inCurrentMonth }) => {
          const active = isSameDate(date, selected);
          const current = isSameDate(date, today);

          return (
            <button
              key={date.toISOString()}
              type="button"
              onClick={() => onSelect?.(date)}
              className={`flex h-11 items-center justify-center rounded-2xl text-sm font-semibold transition ${
                active
                  ? "bg-slate-950 text-white shadow-[0_10px_24px_rgba(15,23,42,0.18)]"
                  : current
                    ? "border border-slate-300 bg-white text-slate-900"
                    : inCurrentMonth
                      ? "text-slate-700 hover:bg-slate-100"
                      : "text-slate-300 hover:bg-slate-50"
              }`}
            >
              {date.getDate()}
            </button>
          );
        })}
      </div>
    </div>
  );
}
