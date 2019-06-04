import { Card, ListGroup } from "react-bootstrap";
import React, { Component } from "react";

export default class UserInfo extends Component {
  render() {
    const { username, firstname, lastname, phone, email } = this.props.user;

    return (
      <Card>
        <Card.Header>
          <strong>
            <i className="fas fa-user mr-2 text-success" /> Údaje o uživateli
          </strong>
        </Card.Header>
        <ListGroup variant="flush">
          <ListGroup.Item>
            Uživatelské jméno: <strong>{username}</strong>
          </ListGroup.Item>
          <ListGroup.Item>
            Jméno a příjmení:{" "}
            <strong>
              {firstname} {lastname}
            </strong>
          </ListGroup.Item>
          <ListGroup.Item>
            Email: <strong>{email}</strong>
          </ListGroup.Item>
          <ListGroup.Item>
            Telefon: <strong>{phone}</strong>
          </ListGroup.Item>
        </ListGroup>
      </Card>
    );
  }
}
