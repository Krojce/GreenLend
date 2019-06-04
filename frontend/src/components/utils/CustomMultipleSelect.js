import React, { Component } from "react";
import { connect } from "react-redux";
import { Form } from "react-bootstrap";
import {
  getCategoriesFailure,
  getCategoriesInit,
  getCategoriesSuccess
} from "../../actions/offer/offerApiActions";
import { getCategories } from "../../apis/greenLendApi";
import { setCategories } from "../../actions/item/itemDataActions";

class CustomMultipleSelect extends Component {
  componentDidMount() {
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

  handleData = event => {
    const { setCategories } = this.props;
    setCategories(
      [...event.target.options]
        .filter(option => option.selected)
        .map(option => {
          return { categoryId: option.value };
        })
    );
  };

  render() {
    const { categories } = this.props.categoriesInfo;

    return (
      <Form.Group controlId="heheXD">
        <Form.Label>Kategorie</Form.Label>
        <Form.Control as="select" multiple onChange={this.handleData}>
          {categories.map((category, index) => {
            return (
              <option key={index} value={category.categoryId}>
                {category.name}
              </option>
            );
          })}
        </Form.Control>
      </Form.Group>
    );
  }
}

const mapStateToProps = state => {
  return {
    categoriesInfo: state.categoriesInfo
  };
};

export default connect(
  mapStateToProps,
  {
    getCategoriesInit,
    getCategoriesSuccess,
    getCategoriesFailure,
    setCategories
  }
)(CustomMultipleSelect);
