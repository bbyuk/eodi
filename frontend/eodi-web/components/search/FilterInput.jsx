export default function FilterInput({ label, enable, changeEnable, children }) {
  return (
    <div className="space-y-2">
      {/* Header */}
      <div className="flex items-center justify-between">
        <label className="text-xs font-medium text-gray-600">{label}</label>

        {enable ? (
          <button onClick={() => changeEnable(false)} className="text-xs text-gray-400">
            해제
          </button>
        ) : (
          <button onClick={() => changeEnable(true)} className="text-xs text-primary">
            +추가
          </button>
        )}
      </div>

      {/* Content */}
      {enable && <div className="pt-1">{children}</div>}
    </div>
  );
}
