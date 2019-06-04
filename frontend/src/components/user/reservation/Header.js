import { Container, Jumbotron } from "react-bootstrap";
import React from "react";

export const Header = () => (
  <Jumbotron fluid className="p-3">
    <Container>
      <h1>Rezervace</h1>
      <p>Zde naleznete své nadcházející rezervace.</p>
    </Container>
  </Jumbotron>
);
