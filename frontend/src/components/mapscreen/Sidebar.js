import React, { Component } from "react";

export default class Sidebar extends Component {
  withOnLeftContent() {
    return (
      <div className="Sidebar-wrapper">
        <div className="Sidebar-onLeft">{this.props.onLeft}</div>
        {this.sidebarOnly()}
      </div>
    );
  }

  sidebarOnly() {
    return <div className="Sidebar">{this.props.children}</div>;
  }

  render() {
    return this.props.onLeft !== undefined
      ? this.withOnLeftContent()
      : this.sidebarOnly();
  }
}
