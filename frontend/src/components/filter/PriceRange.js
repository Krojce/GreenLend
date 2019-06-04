import React, { Component } from "react";
import { Dropdown, FormControl } from "react-bootstrap";
import { connect } from "react-redux";
import { setMaxPriceFilter, setMinPriceFilter } from "../../actions/filter/filterDataActions";

class PriceRange extends Component {
  handleChange = event => {
    const { setMinPriceFilter, setMaxPriceFilter } = this.props;

    switch (event.target.name) {
      case "priceRangeFrom":
        setMinPriceFilter(event.target.value);
        break;
      case "priceRangeTo":
        setMaxPriceFilter(event.target.value);
        break;
      default:
        console.log("WTF kámo, tohle se nemá stát");
        break;
    }
  };

  render() {
    const { minPrice, maxPrice } = this.props.filterData;

    return (
      <Dropdown>
        <Dropdown.Toggle block variant="outline-light" id="dropdown-basic">
          Cenové rozpětí
        </Dropdown.Toggle>

        <Dropdown.Menu>
          <FormControl
            autoFocus
            className="mx-3 my-2 w-auto"
            placeholder="Cena od..."
            value={minPrice}
            onChange={this.handleChange}
            name="priceRangeFrom"
          />
          <FormControl
            className="mx-3 my-2 w-auto"
            placeholder="Cena do..."
            value={maxPrice}
            onChange={this.handleChange}
            name="priceRangeTo"
          />
        </Dropdown.Menu>
      </Dropdown>
    );
  }
}

const mapStateToProps = state => {
  return {
    filterData: state.filterData
  };
};

export default connect(
  mapStateToProps,
  { setMinPriceFilter, setMaxPriceFilter }
)(PriceRange);
