import React from "react";
import {
  Button,
  ButtonToolbar,
  Container,
  Dropdown,
  Navbar
} from "react-bootstrap";
import { Link } from "react-router-dom";
import logo from "../../../images/logo_green.png";

export const LoggedInNavigation = props => (
  <Navbar expand="md" bg="light">
    <Container>
      <Link to="/">
        <Navbar.Brand>
          <img src={logo} alt="Greenlend" />
        </Navbar.Brand>
      </Link>
      <Navbar.Toggle aria-controls="main-navigation" />
      <Navbar.Collapse id="main-navigation">
        <ButtonToolbar className="ml-auto">
          <Link to="/user/offers/add">
            <Button variant="light" className="mr-1">
              Přidej nabídku
            </Button>
          </Link>
          <Link to="/user/reservations">
            <Button variant="light" className="mr-1">
              Rezervace
            </Button>
          </Link>
          <Link to="/user/offers">
            <Button variant="light" className="mr-1">
              Nabídky
            </Button>
          </Link>
          <Link to="/user/messages">
            <Button variant="light" className="mr-1">
              Zprávy
            </Button>
          </Link>
          <Dropdown>
            <Dropdown.Toggle
              variant="light"
              id="userDetailDropdown"
              className="font-weight-bold"
            >
              {props.username || "nepřihlášen"}
            </Dropdown.Toggle>
            <Dropdown.Menu>
              <Link to="/user/profile" className="dropdown-item">
                Profil
              </Link>
              <Link to="/user/logout" className="dropdown-item">
                Odhlásit se
              </Link>
            </Dropdown.Menu>
          </Dropdown>
        </ButtonToolbar>
      </Navbar.Collapse>
    </Container>
  </Navbar>
);
