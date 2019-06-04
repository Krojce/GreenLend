import React from "react";
import Spinner from "react-bootstrap/Spinner";

export const Loading = props => (
  <div
    className={
      "d-flex align-items-center justify-content-center " + props.className
    }
  >
    <Spinner animation="border" variant="success" />
  </div>
);
