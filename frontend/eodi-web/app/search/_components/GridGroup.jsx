/**
 * 가능한 지역 확인 페이지 GridGroup 컴포넌트
 * @param children
 * @param title
 * @returns {JSX.Element}
 * @constructor
 */
export default function GridGroup({ children, title }) {
  return (
    <section className={"mb-14"}>
      <h2 className="text-xl font-semibold text-text-primary mb-4">{title}</h2>
      {children}
    </section>
  );
}
