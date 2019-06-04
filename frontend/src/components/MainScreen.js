import React, { Component } from "react";
import { Link, Redirect } from "react-router-dom";
import FullPageBackground from "./background/FullPageBackground";
import ItemList from "./items/ItemList";
import ItemDetailModal from "./items/ItemDetailModal";
import { connect } from "react-redux";
import { setCurrentItem, setShowModal } from "../actions/item/itemDataActions";
import { greenLendApi } from "../apis/greenLendApi";
import { getItemsFailure, getItemsInit, getItemsSuccess } from "../actions/item/itemApiActions";

class MainScreen extends Component {
  
  state = {
    shouldRedirect: false
  }

  componentWillMount() {
    this.getItemsFromAPI("/lendoffers");
    this.hideItemDetail(); // Fixes opened modal when coming back from booking
  }

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

  hideItemDetail = () => {
    const { setCurrentItem, setShowModal } = this.props;
    setCurrentItem(null);
    setShowModal(false);
  };

  redirectToFilter = () => {
    this.setState({shouldRedirect: true})
  };

  render() {
    if(this.state.shouldRedirect) {
      return <Redirect to="/filter" />;
    }
    const { showModal, currentItem } = this.props.currentItemStore;
    const { user } = this.props.userInfo;
    return (
      <FullPageBackground
        searchbar
        navLinks={
          user.id === null
            ? [
              <Link key={0} to="/map">
                Mapa
              </Link>,
                <Link key={1} to="/login">
                  Přihlásit
                </Link>,
                <Link key={2} to="/register">
                  Registrovat
                </Link>
              ]
            : [
              <Link key={0} to="/map">
                Mapa
              </Link>,
                <Link key={1} to="/user/profile">
                  Profil
                </Link>
              ]
        }
        onSearch={this.redirectToFilter}
      >
        <ItemList title="Nejnovější nabídky"/>
        <ItemDetailModal
          show={showModal}
          onHide={this.hideItemDetail}
          item={currentItem}
        />
      </FullPageBackground>
    );
  }
}

const mapStateToProps = state => {
  return {
    currentItemStore: state.currentItemStore,
    userInfo: state.userInfo
  };
};

export default connect(
  mapStateToProps,
  {
    getItemsInit,
    getItemsSuccess,
    getItemsFailure,
    setCurrentItem,
    setShowModal
  }
)(MainScreen);
