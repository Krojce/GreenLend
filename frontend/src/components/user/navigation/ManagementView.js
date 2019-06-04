import React from "react";
import { Redirect } from "react-router-dom";
import { Route } from "react-router";
import Offers from "../offers/Offers";
import Messages from "../chat/Chat";
import Logout from "./Logout";
import { connect } from "react-redux";
import Reservations from "../reservation/Reservations";
import Profile from "../profile/Profile";
import { LoggedInNavigation } from "./LoggedInNavigation";
import Booking from "../booking/Booking";
import AddItem from "../add/AddItem";

const ManagementView = props => {
  const { user } = props.userInfo;
  const { username } = user;

  if (username === null) return <Redirect to="/login" />;

  return (
    <>
      <LoggedInNavigation username={username} />
      <Route path={"/user/reservations"} component={Reservations} />
      <Route path={"/user/offers"} exact component={Offers} />
      <Route path={"/user/offers/add"} component={AddItem} />
      <Route path={"/user/messages"} component={Messages} />
      <Route path={"/user/profile"} component={Profile} />
      <Route path={"/user/logout"} component={Logout} />
      <Route path={"/user/book"} component={Booking} />
    </>
  );
};

const mapStateToProps = state => {
  return {
    userInfo: state.userInfo
  };
};

export default connect(mapStateToProps)(ManagementView);
