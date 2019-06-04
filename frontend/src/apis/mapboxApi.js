const access_token = "pk.eyJ1IjoidG9tYWpkYWdyZWVubGVuZCIsImEiOiJjanV0d3BvdHYwYm05NDRvYXNob2Y3aTZoIn0.mZkgKckAaL9bshW1javuRA";

export const modalBackground = (lon, lat, width, height) => {
  return mapboxstatic(lon - (360 / 2 ** 12) * (2 / 5), lat + (180 / 2 ** 12) * (1 / 5), 12, width, height);
};

export const mapboxstatic = (lon, lat, zoom = 9, width = 800, height = 650) => {
  return `https://api.mapbox.com/styles/v1/mapbox/streets-v11/static/${lon},${lat},${zoom}/${width}x${height}?access_token=${access_token}`;
};