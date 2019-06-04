import { Button, Col, Row } from "react-bootstrap";
import React from "react";

export const MapButtons = props => {

  const { userLocationAllowed, address, centerHome, locationTitle, homeTitle, centerCurrentLocation } = props;

  console.log(props);

  return (
    <Row className="w-auto m-0 p-0 text-center">
      <Col className="p-2">
        <Button
          variant="light"
          block
          className="py-3"
          disabled={address.id === 0}
          title={
            address.id === 0 ? "Nemáte vyplněnou adresu" : homeTitle
          }
          onClick={centerHome}
        >
          <i className="fas fa-home fa-2x text-secondary"/>
        </Button>
      </Col>
      {"geolocation" in navigator ? (
        <Col className="p-2">
          <Button
            variant="light"
            block
            className="py-3"
            disabled={!userLocationAllowed}
            title={locationTitle}
            onClick={centerCurrentLocation}
          >
            <i className="fas fa-location-arrow fa-2x text-secondary"/>
          </Button>
        </Col>
      ) : null}
    </Row>
  );
};