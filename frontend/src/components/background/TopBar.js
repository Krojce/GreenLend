import { Navbar } from "react-bootstrap";
import { Link } from "react-router-dom";
import logo from "../../images/logo_white.png";
import React from "react";

export const TopBar = props => (
  <Navbar expand="sm" className="full-page-nav">
    <Link to={props.logoLink || "/"}>
      <Navbar.Brand>
        <img
          alt="Greenlend"
          src={logo}
          width="207"
          height="120"
          className="d-inline-block align-top"
        />
      </Navbar.Brand>
    </Link>
    <Navbar.Toggle aria-controls="main-navigation" />
    <Navbar.Collapse id="main-navigation" className="justify-content-end">
      {props.navLinks}
    </Navbar.Collapse>
  </Navbar>
);
