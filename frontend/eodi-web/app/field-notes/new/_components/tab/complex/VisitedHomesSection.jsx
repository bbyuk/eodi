import { Trash2 } from "lucide-react";
import COPY from "@/app/field-notes/new/_data/complexRecordCopy";
import { VISITED_HOME_FIELDS } from "@/app/field-notes/new/_data/complexRecordFields";
import FormTitle from "@/app/field-notes/new/_components/field/FormTitle";
import CollapsibleFormSection from "@/app/field-notes/new/_components/section/CollapsibleFormSection";
import FieldRenderer from "@/app/field-notes/new/_components/tab/complex/FieldRenderer";
import { FIELD_NOTE_INPUT_RADIUS_CLASS } from "@/app/field-notes/new/_components/styles";

const renderVisitedHomeField = (field, home, onChangeHome) => (
  <FieldRenderer
    key={field.key}
    field={field}
    value={home[field.key]}
    onChange={(nextValue) => onChangeHome(field.key, nextValue)}
  />
);

export default function VisitedHomesSection({
  homes,
  openVisitedHomeIds,
  onToggleVisitedHome,
  onRemoveVisitedHome,
  onAddVisitedHome,
  onChangeVisitedHome,
  getHomeTitle,
}) {
  return (
    <div className="space-y-3">
      <FormTitle main={COPY.homesTitle} sub={COPY.homesDescription} preserveSubSpace={false} />

      <div className="space-y-4">
        {homes.map((home, index) => {
          const homeTitle = getHomeTitle(home, index);

          return (
            <CollapsibleFormSection
              key={home.id}
              title={homeTitle}
              isOpen={Boolean(openVisitedHomeIds[home.id])}
              onToggle={() => onToggleVisitedHome(home.id)}
              headerActionPlacement="beforeToggle"
              headerAction={
                <button
                  type="button"
                  onClick={() => onRemoveVisitedHome(home.id)}
                  aria-label={`${homeTitle} delete`}
                  className="inline-flex h-8 w-8 items-center justify-center rounded-full border border-slate-200 text-slate-400 transition hover:border-red-200 hover:bg-red-50 hover:text-red-600 focus:outline-none focus:ring-2 focus:ring-red-100"
                >
                  <Trash2 className="h-3.5 w-3.5" />
                </button>
              }
            >
              {VISITED_HOME_FIELDS.map((fieldDefinition, fieldIndex) =>
                Array.isArray(fieldDefinition) ? (
                  <div key={`row-${fieldIndex}`} className="grid grid-cols-2 gap-3">
                    {fieldDefinition.map((field) =>
                      renderVisitedHomeField(field, home, (fieldKey, nextValue) =>
                        onChangeVisitedHome(home.id, fieldKey, nextValue)
                      )
                    )}
                  </div>
                ) : (
                  renderVisitedHomeField(fieldDefinition, home, (fieldKey, nextValue) =>
                    onChangeVisitedHome(home.id, fieldKey, nextValue)
                  )
                )
              )}
            </CollapsibleFormSection>
          );
        })}
      </div>

      <button
        type="button"
        onClick={onAddVisitedHome}
        className={`flex min-h-12 w-full items-center justify-center border border-dashed border-slate-300 bg-white text-sm font-semibold text-slate-600 transition hover:border-slate-400 hover:bg-slate-50 focus:outline-none focus:ring-2 focus:ring-slate-200 ${FIELD_NOTE_INPUT_RADIUS_CLASS}`}
      >
        {COPY.addHomeButton}
      </button>
    </div>
  );
}
