import React, { Component } from "react";
import { Redirect } from "react-router-dom";
import { connect } from "react-redux";
import { setSearchQuery } from "../../actions/filter/filterDataActions";

class SearchBar extends Component {


  handleChange = event => {
    const { setSearchQuery } = this.props;
    setSearchQuery(event.target.value);
  };

  handleKeyDown = event => {
    if (event.key === "Enter") {
      this.props.onSearch();
    }
  };

  render() {
    return (
      <div className="full-page-searchbar">
        <i className="fas fa-search"/>
        <input
          type="text"
          placeholder="Hledat..."
          onChange={this.handleChange}
          onKeyPress={this.handleKeyDown}
        />
      </div>
    );
  }

}

const mapStateToProps = state => {
  return {};
};

export default connect(
  mapStateToProps,
  {
    setSearchQuery
  }
)(SearchBar);
