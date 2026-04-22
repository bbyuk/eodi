export const flattenFieldDefinitions = (fieldDefinitions) =>
  fieldDefinitions.flatMap((fieldDefinition) =>
    Array.isArray(fieldDefinition) ? fieldDefinition : [fieldDefinition]
  );

export const createInitialValues = (fieldDefinitions) =>
  Object.fromEntries(
    flattenFieldDefinitions(fieldDefinitions).map((field) => [field.key, field.defaultValue])
  );

const hasValue = (value) => {
  if (typeof value === "string") {
    return value.trim().length > 0;
  }

  return Boolean(value);
};

export const getCompletedFieldLabels = (fieldDefinitions, values) =>
  flattenFieldDefinitions(fieldDefinitions)
    .filter((field) => {
      const value = values[field.key];

      if (typeof field.isCompleted === "function") {
        return field.isCompleted(value, values);
      }

      return hasValue(value);
    })
    .map((field) => field.title.main);

export const createVisitedHome = (index, fieldDefinitions) => ({
  id: `visited-home-${index}`,
  ...createInitialValues(fieldDefinitions),
});
