import { Fragment } from "react";
import InfoTooltip from "@/components/etc/InfoTooltip";

export default function SectionHeader({ title, description, info }) {
  return (
    <div className="mb-4">
      <div className="flex items-center gap-2 mb-1">
        <h2 className="text-lg font-semibold text-text-primary">{title}</h2>
        {info && <InfoTooltip position="bottom" content={info} />}
      </div>

      {description && (
        <p className="text-sm text-text-secondary leading-relaxed">
          {Array.isArray(description)
            ? description.map((line, idx) => (
                <Fragment key={idx}>
                  {idx > 0 && <br />}
                  {line}
                </Fragment>
              ))
            : description}
        </p>
      )}
    </div>
  );
}
