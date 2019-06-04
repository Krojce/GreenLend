export const dateFormat = dateString => {
  let date = new Date(dateString);

  let d = date
    .getDate()
    .toString()
    .padStart(2, "0");
  let m = (date.getMonth() + 1).toString().padStart(2, "0");
  let y = date.getFullYear().toString();

  return `${d}.${m}.${y}`;
};
