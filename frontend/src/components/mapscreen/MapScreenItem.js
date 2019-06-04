import React, { Component } from "react";
import { Card } from "react-bootstrap";

export default class MapScreenItem extends Component {
  render() {
    const { item } = this.props;
    return (
      <Card
        className={`m-2`}
        style={{
          backgroundColor: this.props.hovered ? "#e2e6ea" : "#f8f9fa",
          transition: "ease-in-out,background-color .15s",
          cursor: "pointer"
        }}
        border="light"
        onMouseEnter={this.props.onMouseEnter}
        onMouseLeave={this.props.onMouseLeave}
        onClick={() => this.props.onClick(item)}
      >
        <Card.Body>
          <Card.Title>{item.name}</Card.Title>
          <Card.Subtitle>{item.price + "Kč/den"}</Card.Subtitle>
        </Card.Body>
      </Card>
    );
  }
}
