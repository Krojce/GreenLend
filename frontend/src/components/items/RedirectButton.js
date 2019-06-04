import { Button } from "react-bootstrap";
import { Link } from "react-router-dom";
import React from "react";

export const RedirectButton = props => {
  const { text, url, visual } = props;
  return (
    <Link to={url}>
      <Button variant={visual} className="mr-1">
        {text}
      </Button>
    </Link>
  );
};