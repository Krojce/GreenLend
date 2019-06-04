import React, { Component } from "react";
import { Container } from "react-bootstrap";
import { TopBar } from "./TopBar";
import SearchBar from "./SearchBar";

export default class FullPageBackground extends Component {

  render() {
    return (
      <div className="full-page-bg">
        <div className="full-page-cover">
          <Container fluid={this.props.fluid}>
            <TopBar
              logoLink={this.props.logoLink}
              navLinks={this.props.navLinks}
            />
            {this.props.searchbar !== undefined ? (
              <SearchBar onSearch={this.props.onSearch} />
            ) : null}
            {this.props.children}
          </Container>
        </div>
      </div>
    );
  }
}