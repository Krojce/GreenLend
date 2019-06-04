import React, { Component } from "react";
import { Dropdown } from "react-bootstrap";
import { getCategories } from "../../apis/greenLendApi";
import { connect } from "react-redux";
import { getCategoriesFailure, getCategoriesInit, getCategoriesSuccess } from "../../actions/offer/offerApiActions";
import { setCategoryFilter } from "../../actions/filter/filterDataActions";

class CategoriesSelector extends Component {
  componentWillMount() {
    const {
      getCategoriesInit,
      getCategoriesSuccess,
      getCategoriesFailure
    } = this.props;

    getCategoriesInit();
    getCategories()
      .then(result => {
        getCategoriesSuccess(result.data);
      })
      .catch(e => {
        getCategoriesFailure(e.data);
      });
  }

  render() {
    const { setCategoryFilter } = this.props;
    const { categories = [] } = this.props.categoriesInfo;
    const { categoryFilter } = this.props.filterData;

    return (
      <Dropdown>
        <Dropdown.Toggle block variant="outline-light" id="dropdown-basic">
          {"Vyberte kategorii..."}
        </Dropdown.Toggle>

        <Dropdown.Menu>
          {categories.map((category, index) => (
            <Dropdown.Item
              key={index}
              active={
                categoryFilter === category.categoryId
                  ? true.toString()
                  : undefined
              }
              onClick={() => {
                setCategoryFilter(category.categoryId);
              }}
            >
              {category.name}
            </Dropdown.Item>
          ))}

          {categoryFilter !== null ? (
            <>
              <Dropdown.Divider/>
              <Dropdown.Item
                onClick={() => {
                  setCategoryFilter(null);
                }}
              >
                Vymazat
              </Dropdown.Item>
            </>
          ) : null}
        </Dropdown.Menu>
      </Dropdown>
    );
  }
}

const mapStateToProps = state => {
  return {
    categoriesInfo: state.categoriesInfo,
    filterData: state.filterData
  };
};

export default connect(
  mapStateToProps,
  {
    getCategoriesInit,
    getCategoriesSuccess,
    getCategoriesFailure,
    setCategoryFilter
  }
)(CategoriesSelector);
