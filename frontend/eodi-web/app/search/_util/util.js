export const formatWon = (value) => {
  const num = value ? value : 0;

  const jo = Math.floor(num / 100000000);
  const eok = Math.floor((num % 100000000) / 10000);
  const man = num % 10000;

  let result = "";

  result += jo > 0 ? `${jo}조 ` : "";
  result += eok > 0 ? `${eok}억 ` : "";
  result += man > 0 ? `${man}만 ` : "";
  result += jo === 0 && eok === 0 && man === 0 ? "0 " : "";

  result += "원";
  return result;
};
