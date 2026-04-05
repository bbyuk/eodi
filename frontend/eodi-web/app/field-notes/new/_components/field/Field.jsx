import FormTitle from "@/app/field-notes/new/_components/field/FormTitle";

export default function Field({
  title,
  children,
  className = "space-y-3",
  titleAside = null,
  preserveSubSpace = true,
}) {
  return (
    <section className={className}>
      {title ? (
        titleAside ? (
          <div className="flex items-end justify-between gap-3">
            <FormTitle
              main={title.main}
              sub={title.sub}
              preserveSubSpace={preserveSubSpace}
            />
            {titleAside}
          </div>
        ) : (
          <FormTitle
            main={title.main}
            sub={title.sub}
            preserveSubSpace={preserveSubSpace}
          />
        )
      ) : null}
      {children}
    </section>
  );
}
