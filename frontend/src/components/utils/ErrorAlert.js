import { Alert } from "react-bootstrap";
import React from "react";

export const ErrorAlert = props => {
  return (
    <Alert variant="danger">
      {props.message}
    </Alert>
  );
};
