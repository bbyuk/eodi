import FormTitle from "@/app/field-notes/new/_components/field/FormTitle";

export default function Field({
  title,
  children,
  className = "space-y-3",
  titleAside = null,
  preserveSubSpace = true,
  headerClassName = "flex items-end justify-between gap-3",
  titleProps = {},
}) {
  return (
    <section className={className}>
      {title ? (
        titleAside ? (
          <div className={headerClassName}>
            <FormTitle
              main={title.main}
              sub={title.sub}
              preserveSubSpace={preserveSubSpace}
              {...titleProps}
            />
            {titleAside}
          </div>
        ) : (
          <FormTitle
            main={title.main}
            sub={title.sub}
            preserveSubSpace={preserveSubSpace}
            {...titleProps}
          />
        )
      ) : null}
      {children}
    </section>
  );
}
