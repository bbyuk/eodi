import { Fragment } from "react";
import InfoTooltip from "@/components/etc/InfoTooltip";

export default function PageHeader({ title, description, info, children }) {
  return (
    <header className="mb-8 sm:mb-10 lg:mb-12">
      <div className="mb-3 flex items-start gap-2">
        <h1 className="text-2xl font-semibold leading-tight text-text-primary sm:text-3xl lg:text-4xl">
          {title}
        </h1>
        {info && <InfoTooltip position="bottom" content={info} />}
      </div>
      <p className="text-sm leading-6 text-text-secondary sm:text-base">
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
