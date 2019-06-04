import React, { Component } from "react";
import { CardColumns, Col, Spinner } from "react-bootstrap";
import { connect } from "react-redux";
import { getPhoto } from "../../apis/greenLendApi";
import { Item } from "./Item";
import { ErrorAlert } from "../utils/ErrorAlert";
import { setCurrentItem, setShowModal } from "../../actions/item/itemDataActions";

const HIDE_ITEMS_WITHOUT_IMAGE_FILTER = item => item.thumbnail != null;

class ItemList extends Component {
  showItemDetail = item => {
    const { setCurrentItem, setShowModal } = this.props;

    getPhoto(item.lendOfferId)
      .then(response => {
        item.hires = response.data;
        setCurrentItem(item);
      })
      .catch(console.error);

    setCurrentItem(item);
    setShowModal(true);
  };

  render() {
    const { itemList, title } = this.props;
    return (
      <>
        <div className="h2 text-center mb-3">{title}</div>
        {itemList.loading ? (
          <Col className="text-center">
            <Spinner animation="grow" variant="light" />
          </Col>
        ) : itemList.error === null || itemList.error === "" ? (
          <CardColumns className="ItemList">
            {itemList.items.filter(HIDE_ITEMS_WITHOUT_IMAGE_FILTER).map((item, index) => (
              <Item
                itemData={item}
                onClick={() => this.showItemDetail(item)}
                key={item.lendOfferId}
              />
            ))}
          </CardColumns>
        ) : (
          <ErrorAlert message={itemList.error} variant="danger" />
        )}
      </>
    );
  }
}

const mapStateToProps = state => {
  return {
    itemList: state.itemList
  };
};

export default connect(
  mapStateToProps,
  {
    setCurrentItem,
    setShowModal
  }
)(ItemList);
