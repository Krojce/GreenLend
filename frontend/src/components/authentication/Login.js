import React, { Component } from "react";
import { Button, Card, Form } from "react-bootstrap";
import { Link } from "react-router-dom";
import FullPageBackground from "../background/FullPageBackground";
import Sidebar from "../mapscreen/Sidebar";
import { connect } from "react-redux";
import { setPassword, setUsername } from "../../actions/user/userDataActions";
import { loginFailure, loginInit, loginSuccess } from "../../actions/user/userApiActions";
import { login } from "../../apis/greenLendApi";
import { ErrorAlert } from "../utils/ErrorAlert";

class Login extends Component {
  convertLoginData = data => {
    console.log(data);
    return {
      user: {
        id: data.id,
        firstname: data.firstname,
        lastname: data.lastname,
        username: data.username,
        phone: data.phone,
        email: data.email
      },
      address: data.address
    };
  };

  handleSubmit = () => {
    const data = this.getLoginData();
    const { loginInit, loginSuccess, loginFailure } = this.props;

    loginInit();

    let formData = new FormData();
    formData.set("username", data.username);
    formData.set("password", data.password);

    login(formData)
      .then(result => {
        loginSuccess(this.convertLoginData(result.data));
        localStorage.setItem("userId", result.data.id);
        this.props.history.push("");
      })
      .catch(error => {
        loginFailure("Špatné přihlašovací údaje!");
      });
  };

  handleKeyDown = event => {
    if (event.key === "Enter") {
      this.handleSubmit();
    }
  };

  componentDidMount() {
    document.querySelector("#loginFormUsername").focus();
  }

  handleInputChange = event => {
    const { setUsername, setPassword } = this.props;

    switch (event.target.name) {
      case "username":
        setUsername(event.target.value);
        break;
      case "password":
        setPassword(event.target.value);
        break;
      default:
        console.error("This should not happen. If you see this... RUN!!");
    }
  };

  getLoginData() {
    const { loginInputs } = this.props;
    return {
      username: loginInputs.username,
      password: loginInputs.password
    };
  }

  render() {
    const { error } = this.props.userInfo;

    let errorAlert = null;

    if (error != null) {
      errorAlert = <ErrorAlert message={error}/>;
    }

    return (
      <Sidebar onLeft={<FullPageBackground />}>
        <span className="display-4">Přihlašovací údaje</span>
        {errorAlert}
        <Card className="shadow p-3">
          <Card.Body>
            <Form>
              <Form.Group controlId="loginFormUsername">
                <Form.Label>Uživatelské jméno</Form.Label>
                <Form.Control
                  name="username"
                  type="text"
                  onChange={this.handleInputChange}
                  onKeyPress={this.handleKeyDown}
                />
              </Form.Group>
              <Form.Group controlId="loginFormPassword">
                <Form.Label>Heslo</Form.Label>
                <Form.Control
                  name="password"
                  type="password"
                  onChange={this.handleInputChange}
                  onKeyPress={this.handleKeyDown}
                />
              </Form.Group>
              <Form.Group controlId="loginFormKeepMeSigned">
                <Form.Check custom type="checkbox" label="Zůstat přihlášen" />
              </Form.Group>
              <Button variant="light" onClick={this.handleSubmit}>
                Přihlásit
              </Button>
            </Form>
          </Card.Body>
        </Card>
        <Link to="/register">Ještě nemáte účet? Zaregistrujte se.</Link>
      </Sidebar>
    );
  }
}

const mapStateToProps = state => {
  return {
    loginInputs: state.loginInputs,
    userInfo: state.userInfo
  };
};

export default connect(
  mapStateToProps,
  {
    setUsername,
    setPassword,
    loginInit,
    loginSuccess,
    loginFailure
  }
)(Login);
