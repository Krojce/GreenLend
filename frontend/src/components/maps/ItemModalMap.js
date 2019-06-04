import React, { Component } from "react";
import mapboxgl from "mapbox-gl";
import marker from "./../../images/map_marker.png";

mapboxgl.accessToken =
  "pk.eyJ1IjoidG9tYWpkYWdyZWVubGVuZCIsImEiOiJjanV0d3BvdHYwYm05NDRvYXNob2Y3aTZoIn0.mZkgKckAaL9bshW1javuRA";

export default class Map extends Component {
  state = {
    zoom: 9
  };

  recalculateCoordsCenter(moveByX, moveByY = moveByX) {
    // console.log(this.state);
    const { zoom } = this.state;
    const tiles = 2 ** zoom;
    const nlng = this.props.lng - (360 / tiles) * moveByX;
    const nlat = this.props.lat + (180 / tiles) * moveByY;
    return { nlng, nlat, zoom };
  }

  componentDidMount() {
    const { nlng, nlat, zoom } = this.recalculateCoordsCenter(2 / 5, 1 / 5);
    const { lng, lat } = this.props;

    const map = new mapboxgl.Map({
      container: this.mapContainer,
      style: "mapbox://styles/mapbox/streets-v9",
      center: [nlng, nlat],
      zoom,
      interactive: false
    });

    map.on("load", function() {
      map.resize(); // fits the map to whole area, but is not a nice fix
      map.loadImage(marker, function(error, image) {
        if (error) throw error;
        map.addImage("cat", image);
        map.addLayer({
          id: "points",
          type: "symbol",
          source: {
            type: "geojson",
            data: {
              type: "FeatureCollection",
              features: [
                {
                  type: "Feature",
                  geometry: {
                    type: "Point",
                    coordinates: [lng, lat]
                  }
                }
              ]
            }
          },
          layout: {
            "icon-image": "cat",
            "icon-size": 1
          }
        });
      });
    });
  }

  render() {
    return <div ref={el => (this.mapContainer = el)} className="map-bg" />;
  }
}
