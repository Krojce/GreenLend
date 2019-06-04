import axios from "axios";

export const geocode = searchText =>
  axios.get("https://geocoder.api.here.com/6.2/geocode.json", {
    params: {
      app_id: "YaOcT4lQ1yFS727VBEZp",
      app_code: "Otm-O58dcYAtgIisAzoDbQ",
      gen: "9",
      searchText
    }
  });

export const mapview = (lon, lat, modalTranslate = false, width = 1200, height = 800) => {
  axios.get("https://image.maps.api.here.com/mia/1.6/mapview", {
    params: {
      app_id: "YaOcT4lQ1yFS727VBEZp",
      app_code: "Otm-O58dcYAtgIisAzoDbQ",
      z: 9,
      c: `${modalTranslate ? lat + (180 / 2 ** 9) * (1 / 5) : lat},${modalTranslate ? lon - (360 / 2 ** 9) * (2 / 5) : lon}`,
      w: width,
      h: height,
      ml: "cze",
      ppi: 320
    }
  });
};
