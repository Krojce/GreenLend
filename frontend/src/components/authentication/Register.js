import React, { Component } from "react";
import { Button, Card, Col, Form } from "react-bootstrap";
import { Link } from "react-router-dom";
import FullPageBackground from "../background/FullPageBackground";
import Sidebar from "../mapscreen/Sidebar";
import { connect } from "react-redux";
import { registerFailure, registerInit, registerSuccess } from "../../actions/user/userApiActions";
import {
  resetRegisterInputs,
  setEmail,
  setFirstname,
  setLastname,
  setPassword,
  setPhoneNumber,
  setUsername
} from "../../actions/user/userDataActions";
import { register as registerUser } from "../../apis/greenLendApi";
import { ErrorAlert } from "../utils/ErrorAlert";

class Register extends Component {
  handleSubmit = () => {
    const data = this.getRegisterData();
    const {
      registerInit,
      registerSuccess,
      registerFailure,
      resetRegisterInputs
    } = this.props;
    registerInit();
    registerUser(data)
      .then(result => {
        registerSuccess(result.data);
        if (result.status === 200) {
          resetRegisterInputs();
          this.props.history.push("/login");
        }
      })
      .catch(error => {
        registerFailure(error.response.data.message);
      });
  };

  componentDidMount() {
    document.querySelector("#registerFormFirstName").focus();
  }

  handleInputChange = event => {
    const {
      setFirstname,
      setLastname,
      setUsername,
      setPhoneNumber,
      setEmail,
      setPassword
    } = this.props;

    switch (event.target.name) {
      case "firstName":
        setFirstname(event.target.value);
        break;
      case "lastName":
        setLastname(event.target.value);
        break;
      case "username":
        setUsername(event.target.value);
        break;
      case "phonenumber":
        setPhoneNumber(event.target.value);
        break;
      case "email":
        setEmail(event.target.value);
        break;
      case "password":
        setPassword(event.target.value);
        break;
      default:
        console.error("This should not happen. If you see this... RUN!!");
    }
  };

  getRegisterData() {
    const { registerInputs } = this.props;
    return {
      firstname: registerInputs.firstName,
      lastname: registerInputs.lastName,
      username: registerInputs.username,
      phone: registerInputs.phone,
      email: registerInputs.email,
      password: registerInputs.password
    };
  }

  handleKeyDown = event => {
    if (event.key === "Enter") {
      this.handleSubmit();
    }
  };

  render() {
    const { error } = this.props.registerInfo;

    let errorAlert = null;

    if (error != null) {
      errorAlert = <ErrorAlert message={error}/>;
    }

    return (
      <Sidebar onLeft={<FullPageBackground />}>
        <span className="display-4">Registrační údaje</span>
        {errorAlert}
        <Card className="shadow p-3">
          <Card.Body>
            <Form>
              <Form.Row>
                <Form.Group as={Col} controlId="registerFormFirstName">
                  <Form.Label>Křestní jméno</Form.Label>
                  <Form.Control
                    type="text"
                    name="firstName"
                    onChange={this.handleInputChange}
                    onKeyPress={this.handleKeyDown}
                  />
                </Form.Group>
                <Form.Group as={Col} controlId="registerFormLastName">
                  <Form.Label>Příjmení</Form.Label>
                  <Form.Control
                    type="text"
                    name="lastName"
                    onChange={this.handleInputChange}
                    onKeyPress={this.handleKeyDown}
                  />
                </Form.Group>
              </Form.Row>
              <Form.Row>
                <Form.Group as={Col} controlId="registerFormUsername">
                  <Form.Label>Uživatelské jméno</Form.Label>
                  <Form.Control
                    type="text"
                    name="username"
                    onChange={this.handleInputChange}
                    onKeyPress={this.handleKeyDown}
                  />
                </Form.Group>
                <Form.Group as={Col} controlId="registerFormPhone">
                  <Form.Label>Telefonní číslo</Form.Label>
                  <Form.Control
                    type="text"
                    name="phonenumber"
                    onChange={this.handleInputChange}
                    onKeyPress={this.handleKeyDown}
                  />
                </Form.Group>
              </Form.Row>
              <Form.Row>
                <Form.Group as={Col} controlId="registerFormEmail">
                  <Form.Label>Email</Form.Label>
                  <Form.Control
                    type="email"
                    name="email"
                    onChange={this.handleInputChange}
                    onKeyPress={this.handleKeyDown}
                  />
                </Form.Group>
                <Form.Group as={Col} controlId="registerFormEmailAgain">
                  <Form.Label>Email znovu</Form.Label>
                  <Form.Control type="email" />
                </Form.Group>
              </Form.Row>
              <Form.Row>
                <Form.Group as={Col} controlId="registerFormPassword">
                  <Form.Label>Heslo</Form.Label>
                  <Form.Control
                    type="password"
                    name="password"
                    onChange={this.handleInputChange}
                    onKeyPress={this.handleKeyDown}
                  />
                </Form.Group>
                <Form.Group as={Col} controlId="registerFormPasswordAgain">
                  <Form.Label>Heslo znovu</Form.Label>
                  <Form.Control type="password" />
                </Form.Group>
              </Form.Row>
              <Button variant="light" block onClick={this.handleSubmit}>
                Registrovat
              </Button>
            </Form>
          </Card.Body>
        </Card>
        <Link to="/login">Již máte účet? Přihlaste se.</Link>
      </Sidebar>
    );
  }
}

const mapStateToProps = state => {
  return {
    registerInputs: state.registerInputs,
    registerInfo: state.registerInfo
  };
};

export default connect(
  mapStateToProps,
  {
    setFirstname,
    setLastname,
    setUsername,
    setPhoneNumber,
    setEmail,
    setPassword,
    resetRegisterInputs,
    registerInit,
    registerSuccess,
    registerFailure
  }
)(Register);
