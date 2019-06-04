import React from "react";
import { Alert, Container } from "react-bootstrap";

export const NoMessagesYet = () => (
  <Container className="pt-3">
    <Alert variant="info">
      Zprávy můžete přijímat a posílat, až když si něco půjčíte vy, nebo někdo
      od Vás.
    </Alert>
  </Container>
);
