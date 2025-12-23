export const toMinFilterParam = (key) => {
  return `min${key.charAt(0).toUpperCase() + key.slice(1)}`;
};

export const toMaxFilterParam = (key) => {
  return `max${key.charAt(0).toUpperCase() + key.slice(1)}`;
};
