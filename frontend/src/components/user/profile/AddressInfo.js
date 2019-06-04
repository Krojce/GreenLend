import { Card, ListGroup } from "react-bootstrap";
import React, { Component } from "react";

export default class AddressInfo extends Component {
  render() {
    const { street, city, zipcode } = this.props.address;

    return (
      <Card>
        <Card.Header>
          <strong>
            <i className="fas fa-map-marked-alt mr-2 text-success" /> Adresa
          </strong>
        </Card.Header>
        <ListGroup variant="flush">
          <ListGroup.Item>
            Ulice a číslo popisné: <strong>{street}</strong>
          </ListGroup.Item>
          <ListGroup.Item>
            Obec: <strong>{city}</strong>
          </ListGroup.Item>
          <ListGroup.Item>
            PSČ: <strong>{zipcode}</strong>
          </ListGroup.Item>
        </ListGroup>
      </Card>
    );
  }
}
