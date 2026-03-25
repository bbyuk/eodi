"use client";

import ActionButton from "@/components/ui/input/ActionButton";

export default function SaveButtonBar({ disabled, helperText, onSave }) {
  return (
    <div className="fixed bottom-0 left-0 right-0 z-50 border-t border-slate-200 bg-white/96 px-4 py-3 backdrop-blur-md [padding-bottom:calc(env(safe-area-inset-bottom)+0.75rem)]">
      <div className="mx-auto max-w-3xl space-y-2">
        {helperText ? <p className="text-xs font-medium text-red-600">{helperText}</p> : null}
        <ActionButton
          type="button"
          variant="dark"
          size="md"
          block
          onClick={onSave}
          className={disabled ? "pointer-events-none opacity-50" : ""}
        >
          저장하기
        </ActionButton>
      </div>
    </div>
  );
}

