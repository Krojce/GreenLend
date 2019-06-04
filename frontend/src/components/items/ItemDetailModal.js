import React, { Component } from "react";
import { Col, Modal, Row } from "react-bootstrap";
import { modalBackground } from "../../apis/mapboxApi";
import { connect } from "react-redux";
import { RedirectButton } from "./RedirectButton";

export class ItemDetailModal extends Component {

  render() {
    if (this.props.item == null || this.props.item === {}) return null;
    const {
      name,
      description,
      price,
      thumbnail,
      hires,
      categories
    } = this.props.item;
    const { firstname = "", lastname = "" } = this.props.item.owner || {};
    const { longitude = 0, latitude = 0 } = this.props.item.address || {};
    const { show, onHide } = this.props;
    const { user, address } = this.props.userInfo;

    let text = "Půjčit si";
    let url = "/user/book";
    let visual = "success";

    if (address.id === 0) {
      text = "Pro půjčování vyplňte adresu";
      url = "/user/profile";
      visual = "light";
    }

    if (user.id === null) {
      text = "Pro půjčování se přihlašte";
      url = "/login";
      visual = "light";
    }

    return (
      <Modal
        show={show}
        onHide={onHide}
        className="ItemDetailModal"
        size="lg"
        centered
      >
        <Modal.Body>
          <Row>
            <Col sm={7}>
              <h1 className="mb-0">{name}</h1>
              <p className="mb-3 text-secondary">
                {categories.map(c => c.name).join(", ")}
              </p>
              <p>{description}</p>
              <img src={hires || thumbnail} alt={name} />
              <p className="mt-2 mb-0 text-secondary">
                Přidal uživatel {firstname + " " + lastname}
              </p>
            </Col>
            <Col sm={5} className="pt-5">
              <p>
                Cena za den: <span className="font-weight-bold">{price}Kč</span>
              </p>
              <RedirectButton text={text} url={url} visual={visual}/>
            </Col>
          </Row>
          <div className="curve_bg" />
          <div className="map-bg" style={{ backgroundImage: `url(${modalBackground(longitude, latitude)})` }}/>

          {/*<Map lng={longitude} lat={latitude} />*/}
        </Modal.Body>
      </Modal>
    );
  }
}

const mapStateToProps = state => {
  return {
    userInfo: state.userInfo
  };
};

export default connect(
  mapStateToProps,
  {}
)(ItemDetailModal);
