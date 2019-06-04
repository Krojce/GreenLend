import React, { Component } from "react";
import { Col, FormControl, Row } from "react-bootstrap";
import FullPageBackground from "../background/FullPageBackground";
import { getCategories, greenLendApi } from "../../apis/greenLendApi";
import { connect } from "react-redux";
import { getCategoriesFailure, getCategoriesInit, getCategoriesSuccess } from "../../actions/offer/offerApiActions";
import CategoriesSelector from "./CategoriesSelector";
import PriceRange from "./PriceRange";
import { setSearchQuery } from "../../actions/filter/filterDataActions";
import ItemList from "../items/ItemList";
import { getItemsFailure, getItemsInit, getItemsSuccess } from "../../actions/item/itemApiActions";
import ItemDetailModal from "../items/ItemDetailModal";
import { setCurrentItem, setShowModal } from "../../actions/item/itemDataActions";

class FilterScreen extends Component {
  componentWillMount() {
    const { filterData } = this.props;

    this.getCategoriesFromAPI();
    this.getItemsFromAPI(createURL(filterData));
  }

  getCategoriesFromAPI = () => {
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
  };

  getItemsFromAPI = url => {
    const { getItemsInit, getItemsSuccess, getItemsFailure } = this.props;

    getItemsInit();
    greenLendApi
      .get(url)
      .then(result => {
        getItemsSuccess(result.data);
      })
      .catch(() => {
        getItemsFailure("Nepodařilo se načíst nabídky");
      });
  };

  shouldComponentUpdate(nextProps, nextState, nextContext) {
    const { filterData: nextPropsFilterData } = nextProps;
    const { filterData: propsFilterData } = this.props;

    const { currentItemStore: nextPropscurrentItemStore } = nextProps;
    const { currentItemStore: propscurrentItemStore } = this.props;

    if (!(nextPropsFilterData === propsFilterData)) {
      this.getItemsFromAPI(createURL(nextPropsFilterData));
      return true;
    }

    return !(nextPropscurrentItemStore === propscurrentItemStore);
  }

  handleChange = event => {
    const { setSearchQuery } = this.props;

    switch (event.target.name) {
      case "searchQuery":
        setSearchQuery(event.target.value);
        break;
      default:
        console.log("WTF kámo, tohle se nemá stát");
        break;
    }
  };

  hideItemDetail = () => {
    const { setCurrentItem, setShowModal } = this.props;
    setCurrentItem({});
    setShowModal(false);
  };

  render() {
    const { searchQuery } = this.props.filterData;
    const { items } = this.props.itemList;
    const { currentItemStore } = this.props;
    const { showModal, currentItem } = currentItemStore;

    return (
      <FullPageBackground>
        <ItemDetailModal
          show={showModal}
          onHide={this.hideItemDetail}
          item={currentItem}
        />
        <Row className="mb-3">
          <Col>
            <CategoriesSelector/>
          </Col>
          <Col>
            <PriceRange/>
          </Col>
          <Col>
            <FormControl
              block={"true"}
              placeholder="Klíčová slova v názvu"
              onChange={this.handleChange}
              value={searchQuery}
              name="searchQuery"
            />
          </Col>
        </Row>
        <ItemList items={items} title="Nalezené nabídky"/>
      </FullPageBackground>
    );
  }
}

const mapStateToProps = state => {
  return {
    categoriesInfo: state.categoriesInfo,
    filterData: state.filterData,
    itemList: state.itemList,
    currentItemStore: state.currentItemStore
  };
};

export default connect(
  mapStateToProps,
  {
    getItemsInit,
    getItemsSuccess,
    getItemsFailure,
    getCategoriesInit,
    getCategoriesSuccess,
    getCategoriesFailure,
    setCurrentItem,
    setShowModal,
    setSearchQuery
  }
)(FilterScreen);

export function createURL(filterData) {
  const { searchQuery, minPrice, maxPrice, categoryFilter } = filterData;

  let parameters = [];

  parameters.push("/lendoffers/search?");

  if (searchQuery !== "") {
    parameters.push("searchQuery=" + searchQuery + "&");
  }

  if (categoryFilter !== null) {
    parameters.push("category=" + categoryFilter.toString() + "&");
  }

  if (minPrice !== "") {
    parameters.push("min_price=" + minPrice + "&");
  }

  if (maxPrice !== "") {
    parameters.push("max_price=" + maxPrice + "&");
  }

  return parameters.join("");
}
