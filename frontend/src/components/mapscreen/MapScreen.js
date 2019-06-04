import React, { Component } from "react";
import { Redirect } from "react-router-dom";
import { getLatestOffers, getNearestOffers } from "../../apis/greenLendApi";
import ItemsMap from "../maps/ItemsMap";
import { connect } from "react-redux";
import MapScreenItem from "./MapScreenItem";
import { getCurrentLocation } from "../../apis/geolocationApi";
import { setCurrentItem, setShowModal } from "../../actions/item/itemDataActions";
import ItemDetailModal from "../items/ItemDetailModal";
import { Loading } from "../utils/Loading";
import { getItemsFailure, getItemsInit, getItemsSuccess } from "../../actions/item/itemApiActions";
import {
  setCurrentCenter,
  setHomeTitle,
  setHoverItem,
  setIsCentered,
  setLocationTitle,
  setUserLocation,
  setUserLocationAllowed
} from "../../actions/map/mapDataActions";
import { MapMenu } from "./MapMenu";
import { MapButtons } from "./MapButtons";

class MapScreen extends Component {

  state = {
    shouldRedirect: false
  }

  componentWillMount() {
    this.updateItems();
  }

  centerHome = () => {
    const { setCurrentCenter } = this.props;
    const { longitude, latitude } = this.props.userInfo.address;
    setCurrentCenter([longitude, latitude]);
  };

  centerCurrentLocation = () => {
    const {
      setCurrentCenter,
      setUserLocationAllowed,
      setHomeTitle
    } = this.props;
    getCurrentLocation()
      .then(response => {
        setCurrentCenter([
          response.coords.longitude,
          response.coords.latitude
        ]);
      })
      .catch(error => {
        setUserLocationAllowed(false);
        setHomeTitle("Polohu nelze zaměřit");
      });
  };

  goBackHome = () => {
    this.setState({shouldRedirect: true});
  }

  updateItems() {
    const { address } = this.props.userInfo;

    this.findCurrentLocation().catch(() => {
      const { setUserLocationAllowed, setLocationTitle } = this.props;

      // nepodario se nacist polohu / uzivatel zakazal
      setUserLocationAllowed(false);
      setLocationTitle("Polohu nelze zaměřit");

      // tak najdi itemy blizko moji adresy pokud mam
      const { latitude, longitude } = address;

      if (latitude && longitude) {
        this.findNearestOffers(longitude, latitude);
      } else {
        // nemam ani adresu, tak vykreslim proste posledni itemy a center dam na prvni
        getLatestOffers().then(response => {
          if (response.data.length > 0) {
            const { getItemsSuccess, setCurrentCenter } = this.props;
            const { latitude, longitude } = response.data[0].address;
            getItemsSuccess(response.data);
            setCurrentCenter([longitude, latitude]);
          } else {
            // v DB neni nic
            // TODO nejakej alert
          }
        });
      }
    });
  }

  findCurrentLocation = () => {
    return getCurrentLocation().then(location => {
      // mam polohu, tak mi dej itemy blizko me
      const { setIsCentered, setCurrentCenter, setUserLocationAllowed } = this.props;
      const { latitude, longitude } = location.coords;
      setUserLocationAllowed(true);
      setIsCentered(true);
      setCurrentCenter([longitude, latitude]);
      this.findNearestOffers(longitude, latitude);
    });
  };

  findNearestOffers = (longitude, latitude) => {
    const { getItemsSuccess, getItemsFailure } = this.props;
    getNearestOffers(longitude, latitude)
      .then(result => {
        getItemsSuccess(result.data);
      })
      .catch(error => {
        getItemsFailure(error);
      });
  };

  showItemDetail = item => {
    const { setCurrentItem, setShowModal } = this.props;
    setCurrentItem(item);
    setShowModal(true);
  };

  hideItemDetail = () => {
    const { setCurrentItem, setShowModal } = this.props;
    setCurrentItem(null);
    setShowModal(false);
  };

  render() {
    if(this.state.shouldRedirect)
      return <Redirect to="/" />;

    const { showModal, currentItem } = this.props.currentItemStore;
    const { items } = this.props.itemList;
    const { address } = this.props.userInfo;
    const {
      isCentered,
      currentCenter,
      userLocation,
      userLocationAllowed,
      homeTitle,
      locationTitle,
      hoverItem
    } = this.props.mapData;
    const { setHoverItem, setCurrentCenter } = this.props;

    return (
      <div className="MapScreenGrid">
        <div className="Sidebar" style={{ overflowY: "auto" }}>
          <MapMenu onBackClick={this.goBackHome} />
          <MapButtons
            userLocationAllowed={userLocationAllowed}
            address={address}
            centerHome={() => this.centerHome()}
            locationTitle={locationTitle}
            homeTitle={homeTitle}
            centerCurrentLocation={() => this.centerCurrentLocation()}
          />
          {items.length > 0 ? (
            items.map((item, index) => (
              <MapScreenItem
                key={index}
                item={item}
                hovered={hoverItem === item.lendOfferId}
                onMouseEnter={() => {
                  setCurrentCenter([
                    item.address.longitude,
                    item.address.latitude
                  ]);
                  setHoverItem(item.lendOfferId);
                }}
                onMouseLeave={() => setHoverItem(null)}
                onClick={this.showItemDetail}
              />
            ))
          ) : (
            <Loading/>
          )}
        </div>
        <ItemsMap
          center={currentCenter}
          items={items}
          onItemHover={item => setHoverItem(item.lendOfferId)}
          onItemLeave={() => setHoverItem(null)}
          onItemClick={this.showItemDetail}
        />
        <ItemDetailModal
          show={showModal}
          onHide={this.hideItemDetail}
          item={currentItem}
        />
      </div>
    );
  }
}

const mapStateToProps = state => {
  return {
    currentItemStore: state.currentItemStore,
    userInfo: state.userInfo,
    itemList: state.itemList,
    mapData: state.mapData
  };
};

export default connect(
  mapStateToProps,
  {
    setCurrentItem,
    setShowModal,
    getItemsInit,
    getItemsSuccess,
    getItemsFailure,
    setUserLocation,
    setCurrentCenter,
    setUserLocationAllowed,
    setHomeTitle,
    setLocationTitle,
    setIsCentered,
    setHoverItem
  }
)(MapScreen);
