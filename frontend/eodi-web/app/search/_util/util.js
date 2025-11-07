export const formatWon = (value) => {
  const num = value ? value : 0;

  const jo = Math.floor(num / 100000000);
  const eok = Math.floor((num % 100000000) / 10000);
  const man = num % 10000;

  let eokStr = new String(eok);
  let manStr = new String(man);

  if (eok >= 1000) {
    eokStr = `${eokStr.charAt(0)},${eokStr.substring(1, 4)}`;
  }
  if (man >= 1000) {
    manStr = `${manStr.charAt(0)},${manStr.substring(1, 4)}`;
  }

  let result = "";

  result += jo > 0 ? `${jo}조 ` : "";
  result += eok > 0 ? `${eokStr}억 ` : "";
  result += man > 0 ? `${manStr}만 ` : "";
  result += jo === 0 && eok === 0 && man === 0 ? "0 " : "";

  result += "원";
  return result;
};
