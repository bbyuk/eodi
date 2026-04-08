"use client";

import { useEffect, useMemo, useState } from "react";
import { CalendarDays, X } from "lucide-react";
import { Calendar } from "@/components/ui/calendar";
import Field from "@/app/field-notes/new/_components/field/Field";
import { FIELD_NOTE_INPUT_RADIUS_CLASS } from "@/app/field-notes/new/_components/styles";

function toDateString(date) {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");

  return `${year}-${month}-${day}`;
}

function parseDateString(value) {
  const matchedDate = value?.match(/^(\d{4})-(\d{2})-(\d{2})$/);

  if (!matchedDate) {
    return new Date();
  }

  return new Date(Number(matchedDate[1]), Number(matchedDate[2]) - 1, Number(matchedDate[3]));
}

function formatDateLabel(value) {
  const date = parseDateString(value);

  return `${date.getFullYear()}년 ${date.getMonth() + 1}월 ${date.getDate()}일`;
}

function getRelativeDate(dayOffset) {
  const date = new Date();
  date.setDate(date.getDate() + dayOffset);

  return date;
}

export default function DateInputField({ title, value, onChange }) {
  const [open, setOpen] = useState(false);
  const selectedDate = useMemo(() => parseDateString(value), [value]);
  const [visibleMonth, setVisibleMonth] = useState(() => selectedDate);

  useEffect(() => {
    if (!open) {
      return undefined;
    }

    const handleEscape = (event) => {
      if (event.key === "Escape") {
        setOpen(false);
      }
    };

    document.body.style.overflow = "hidden";
    window.addEventListener("keydown", handleEscape);

    return () => {
      document.body.style.overflow = "";
      window.removeEventListener("keydown", handleEscape);
    };
  }, [open]);

  useEffect(() => {
    if (open) {
      setVisibleMonth(selectedDate);
    }
  }, [open, selectedDate]);

  const handleSelectDate = (date) => {
    onChange(toDateString(date));
    setOpen(false);
  };

  const handleSelectQuickDate = (dayOffset) => {
    handleSelectDate(getRelativeDate(dayOffset));
  };

  return (
    <Field title={title}>
      <button
        type="button"
        onClick={() => setOpen(true)}
        className={`flex min-h-14 w-full items-center gap-3 border border-slate-200 bg-white px-4 text-left transition hover:border-slate-300 hover:bg-slate-50 focus:outline-none focus:ring-2 focus:ring-slate-200 ${FIELD_NOTE_INPUT_RADIUS_CLASS}`}
      >
        <CalendarDays className="h-4 w-4 shrink-0 text-slate-400" />
        <span className="min-w-0 flex-1 truncate text-sm font-medium text-slate-900">
          {formatDateLabel(value)}
        </span>
      </button>

      {open ? (
        <div className="fixed inset-0 z-[70]">
          <button
            type="button"
            aria-label="방문일 선택 닫기"
            onClick={() => setOpen(false)}
            className="absolute inset-0 bg-slate-950/50 backdrop-blur-sm"
          />

          <div className="absolute inset-x-0 bottom-0 rounded-t-[2rem] bg-white px-5 pb-[calc(env(safe-area-inset-bottom)+2rem)] pt-4 shadow-[0_-24px_80px_rgba(15,23,42,0.22)]">
            <div className="mx-auto mb-4 h-1.5 w-12 rounded-full bg-slate-200" />

            <div className="flex items-start justify-between gap-4">
              <div className="space-y-1">
                <h3 className="text-lg font-semibold text-slate-950">방문일</h3>
                <p className="text-sm leading-6 text-slate-600">{formatDateLabel(value)}</p>
              </div>
              <button
                type="button"
                onClick={() => setOpen(false)}
                aria-label="방문일 선택 닫기"
                className="rounded-full p-2 text-slate-400 transition hover:bg-slate-100 hover:text-slate-700"
              >
                <X className="h-4 w-4" />
              </button>
            </div>

            <div className="mt-5 grid grid-cols-2 gap-2">
              <button
                type="button"
                onClick={() => handleSelectQuickDate(0)}
                className={`min-h-11 border border-slate-200 bg-slate-50 px-4 text-sm font-semibold text-slate-700 transition hover:border-slate-300 hover:bg-white ${FIELD_NOTE_INPUT_RADIUS_CLASS}`}
              >
                오늘
              </button>
              <button
                type="button"
                onClick={() => handleSelectQuickDate(-1)}
                className={`min-h-11 border border-slate-200 bg-slate-50 px-4 text-sm font-semibold text-slate-700 transition hover:border-slate-300 hover:bg-white ${FIELD_NOTE_INPUT_RADIUS_CLASS}`}
              >
                어제
              </button>
            </div>

            <Calendar
              selected={selectedDate}
              month={visibleMonth}
              onMonthChange={setVisibleMonth}
              onSelect={handleSelectDate}
              className="mt-5"
            />
          </div>
        </div>
      ) : null}
    </Field>
  );
}
