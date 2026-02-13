export default function Button({ onClick = () => {}, label = "확인", active = true }) {
  return (
    <button
      onClick={onClick}
      className={`w-full mt-4 py-2 rounded-md font-medium text-sm transition ${active ? "bg-primary text-white hover:bg-primary/90" : "bg-gray-200 text-gray-400 cursor-not-allowed"}`}
    >
      {label}
    </button>
  );
}
