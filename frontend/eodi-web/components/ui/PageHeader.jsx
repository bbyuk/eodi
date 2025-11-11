import { Fragment } from "react";
import InfoTooltip from "@/components/ui/InfoTooltip";

export default function PageHeader({ title, description, info, children }) {
  return (
    <header className="mb-14">
      <div className="flex items-center gap-2 mb-3">
        <h1 className="text-3xl md:text-4xl font-semibold text-text-primary leading-tight">
          {title}
        </h1>
        {info && <InfoTooltip position="bottom" content={info} />}
      </div>
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
