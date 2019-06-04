import React, { Component } from "react";
import ReactMapboxGl, { Feature, Image, Layer } from "react-mapbox-gl";
import marker from "./../../images/map_marker.png";

const MapBox = ReactMapboxGl({
  minZoom: 8,
  maxZoom: 15,
  accessToken:
    "pk.eyJ1IjoidG9tYWpkYWdyZWVubGVuZCIsImEiOiJjanV0d3BvdHYwYm05NDRvYXNob2Y3aTZoIn0.mZkgKckAaL9bshW1javuRA"
});

export default class ItemsMap extends Component {
  handleHoverEnter = event => {
    const { map = {} } = event;
    map.getCanvas().style.cursor = "pointer";
  };

  handleHoverLeave = event => {
    const { map = {} } = event;
    map.getCanvas().style.cursor = "";
  };

  render() {
    const { center, items, onItemClick, onItemHover, onItemLeave } = this.props;

    return (
      <MapBox style={"mapbox://styles/mapbox/streets-v9"} center={center}>
        <Image id={"greenlend-marker"} url={marker}/>
        <Layer
          type="symbol"
          id="marker"
          layout={{ "icon-image": "greenlend-marker", "icon-size": 0.6 }}
        >
          {items.map((item, index) => {
            const { latitude, longitude } = item.address;
            return (
              <Feature
                key={index}
                onMouseEnter={e => {
                  this.handleHoverEnter(e);
                  onItemHover(item);
                }}
                onMouseLeave={e => {
                  this.handleHoverLeave(e);
                  onItemLeave();
                }}
                onClick={() => onItemClick(item)}
                coordinates={[longitude, latitude]}
              />
            );
          })}
        </Layer>
        <Layer
          type="symbol"
          id="home"
          layout={{ "icon-image": "home-15", "icon-size": 2 }}
        >
          <Feature coordinates={center}/>
        </Layer>
      </MapBox>
    );
  }
}
