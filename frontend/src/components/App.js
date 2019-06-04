import React, { Component } from "react";
import { BrowserRouter } from "react-router-dom";
import { Route } from "react-router";
import Login from "./authentication/Login";
import Register from "./authentication/Register";
import MainScreen from "./MainScreen";
import MapScreen from "./mapscreen/MapScreen";
import FilterScreen from "./filter/FilterScreen";
import ManagementView from "./user/navigation/ManagementView";

class App extends Component {
  /*   constructor() {
    super();
    if (!localStorage.getItem("loggedInDoPici"))
      localStorage.setItem("loggedInDoPici", false);
  }
 */
  render() {
    return (
      <BrowserRouter>
        <Route path={"/"} exact component={MainScreen} />
        <Route path={"/filter"} component={FilterScreen} />
        <Route path={"/map"} component={MapScreen} />
        <Route path={"/login"} component={Login} />
        <Route path={"/register"} component={Register} />
        <Route path={"/user/:page"} component={ManagementView} />
      </BrowserRouter>
    );
  }
}

export default App;
