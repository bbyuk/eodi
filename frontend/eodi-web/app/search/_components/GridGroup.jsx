/**
 * 가능한 지역 확인 페이지 GridGroup 컴포넌트
 * @param children
 * @param title
 * @returns {JSX.Element}
 * @constructor
 */
export default function GridGroup({ children, title }) {
  return (
    <section className="mb-10 sm:mb-12">
      {title ? <h2 className="mb-4 text-lg font-semibold text-text-primary sm:text-xl">{title}</h2> : null}
      <div className="flex flex-col gap-4 sm:gap-5">{children}</div>
    </section>
  );
}
