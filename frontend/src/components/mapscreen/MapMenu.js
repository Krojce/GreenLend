import { Button, Col, Image, Row } from "react-bootstrap";
import gl_logo from "../../images/logo_green.png";
import React from "react";

export const MapMenu = props => (
  <Row className="w-auto m-0 p-0 text-center">
    <Col xs={"auto"} className="p-2">
      <Button
        variant="light"
        title="Zpět"
        onClick={props.onBackClick}
      >
        <i className="fas fa-arrow-left text-secondary"/>
      </Button>
    </Col>
    <Col className="p-2">
      <Image src={gl_logo} alt="Greenlend"/>
    </Col>
  </Row>
);