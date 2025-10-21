import { Fragment } from "react";

export default function PageHeader({ title, description, children }) {
  return (
    <header className="mb-14">
      <h1 className="text-3xl md:text-4xl font-semibold text-text-primary mb-3 leading-tight">
        {title}
      </h1>
      <p className="text-base text-text-secondary leading-relaxed">
        {description.map((line, idx) => (
          <Fragment key={idx}>
            {idx > 0 && <br className="hidden sm:block" />}
            {line}
          </Fragment>
        ))}
      </p>
      {children}
    </header>
  );
}
