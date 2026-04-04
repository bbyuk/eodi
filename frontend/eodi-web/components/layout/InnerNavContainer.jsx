export default function InnerNavContainer({ children }) {
  return (
    <div className="flex min-h-[48px] flex-wrap items-center gap-2 overflow-visible">
      {children}
    </div>
  );
}
