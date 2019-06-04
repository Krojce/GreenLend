import React, { Component } from "react";
import { Col, Container, Jumbotron, Row } from "react-bootstrap";
import { connect } from "react-redux";
import { geocode } from "../../../apis/hereApi";
import { addAddress } from "../../../apis/greenLendApi";
import AddressInfo from "./AddressInfo";
import AddressForm from "./AddressForm";
import { addAddressFailure, addAddressInit, addAddressSuccess } from "../../../actions/user/userApiActions";
import UserInfo from "./UserInfo";

class Profile extends Component {
  validateAddress = () => {
    const {
      addressInputs,
      addAddressInit,
      addAddressSuccess,
      addAddressFailure
    } = this.props;

    addAddressInit();
    geocode(addressInputs.addressString)
      .then(r => {
        const {
          Address,
          DisplayPosition
        } = r.data.Response.View[0].Result[0].Location;

        if (
          (Address.District || Address.City) &&
          Address.Street &&
          Address.HouseNumber &&
          Address.PostalCode
        ) {
          const address = {
            city: Address.District || Address.City,
            street: Address.Street + " " + Address.HouseNumber,
            zipcode: Address.PostalCode,
            latitude: DisplayPosition.Latitude,
            longitude: DisplayPosition.Longitude
          };

          addAddressSuccess(address);

          addAddress(localStorage.getItem("userId"), address)
            .then(res => console.log(res))
            .catch(e => addAddressFailure(e));
        } else {
          addAddressFailure(
            "Je nám líto, ale adresa není dostatečně přesná. Zkuste to pořádně!"
          );
        }
      })
      .catch(error => {
        addAddressFailure(
          "Je nám líto, ale adresa není dostatečně přesná. Zkuste to pořádně!"
        );
      });
  };

  render() {
    const { user, address } = this.props.userInfo;

    return (
      <>
        <Jumbotron fluid className="p-3">
          <Container>
            <h1>Profil</h1>
            <p>Zde naleznete informace o svém profilu.</p>
          </Container>
        </Jumbotron>
        <Container className="pt-3">
          <Row>
            <Col md={6}>
              <UserInfo user={user} />
            </Col>
            <Col md={6}>
              {address.city == null ? (
                <AddressForm validateAddress={this.validateAddress} />
              ) : (
                <AddressInfo address={address} />
              )}
            </Col>
          </Row>
        </Container>
      </>
    );
  }
}

const mapStateToProps = state => {
  return {
    userInfo: state.userInfo,
    addressInputs: state.addressInputs
  };
};

export default connect(
  mapStateToProps,
  {
    addAddressInit,
    addAddressSuccess,
    addAddressFailure
  }
)(Profile);
