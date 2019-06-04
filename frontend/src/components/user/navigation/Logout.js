import React from "react";
import { Redirect } from "react-router-dom";
import { logout } from "../../../apis/greenLendApi";
import { connect } from "react-redux";
import { logoutUser } from "../../../actions/item/itemDataActions";

const Logout = props => {
  const { logoutUser } = props;
  logout();
  localStorage.setItem("loggedInDoPici", false);
  document.cookie = "JSESSIONID=; expires = Thu, 01 Jan 1970 00:00:00 GMT";
  logoutUser();

  return <Redirect to="/login" />;
};

const mapStateToProps = state => {
  return {};
};

export default connect(
  mapStateToProps,
  {
    logoutUser
  }
)(Logout);
