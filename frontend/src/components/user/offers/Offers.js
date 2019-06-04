import React, { Component } from "react";
import { Container, Jumbotron } from "react-bootstrap";
import { OfferTable } from "./OfferTable";
import { getUsersOffers } from "../../../apis/greenLendApi";
import { ErrorAlert } from "../../utils/ErrorAlert";
import { connect } from "react-redux";
import {
  getOffersFailure,
  getOffersInit,
  getOffersSuccess
} from "../../../actions/offer/offerApiActions";
import { Loading } from "../../utils/Loading";

class Offers extends Component {
  componentWillMount() {
    const {
      getOffersInit,
      getOffersSuccess,
      getOffersFailure,
      userInfo
    } = this.props;
    const { user } = userInfo;
    getOffersInit();

    getUsersOffers(user.id)
      .then(result => {
        getOffersSuccess(result.data);
      })
      .catch(() => {
        getOffersFailure("Nepodařilo se načíst nabídky");
      });
  }

  render() {
    const { offerList } = this.props;
    let error = null;
    if (offerList.error !== null) {
      error = <ErrorAlert message={offerList.error} />;
    }
    return (
      <>
        <Jumbotron fluid className="p-3">
          <Container>
            <h1>Nabídky</h1>
            <p>
              Zde vidíte přehled položek, které nabízíte ostatním uživatelům.
            </p>
          </Container>
        </Jumbotron>
        <Container className="pt-3">
          {offerList.loading ? (
            <Loading />
          ) : (
            <>
              {error}
              <OfferTable items={offerList.offers || []} />
            </>
          )}
        </Container>
      </>
    );
  }
}

const mapStateToProps = state => {
  return {
    offerList: state.offerList,
    userInfo: state.userInfo
  };
};

export default connect(
  mapStateToProps,
  {
    getOffersInit,
    getOffersSuccess,
    getOffersFailure
  }
)(Offers);
