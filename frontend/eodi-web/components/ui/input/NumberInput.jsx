/**
 * 숫자 입력 UI 컴포넌트
 * @param label 좌측 상단 label
 * @param value 값
 * @param placeholder placeholder
 * @param onChange onChange event handler
 * @param unit 단위
 */
export default function NumberInput({ label, unit, ...props }) {
  return (
    <section>
      <label className="block text-sm font-medium text-text-secondary mb-2">{label}</label>

      <div className="flex items-center gap-3">
        <div className="relative flex-1">
          <input
            type="text"
            inputMode="numeric"
            className={`w-full px-4 py-3 border border-border rounded-lg text-right text-text-primary
                    placeholder:text-text-secondary focus:ring-2 focus:ring-primary
                    focus:border-primary focus:outline-none transition
                    ${unit ? "pr-12" : "pr-4"}`}
            {...props}
          />

          {unit && (
            <span className="absolute right-4 top-1/2 -translate-y-1/2 text-text-secondary text-sm">
              {unit}
            </span>
          )}
        </div>
      </div>
    </section>
  );
}
