import React, { Component } from "react";
import { Form } from "react-bootstrap";
import { connect } from "react-redux";
import { setDescription, setName, setPrice } from "../../../actions/item/itemDataActions";

class TextInputs extends Component {
  handleInputChange = event => {
    const { setName, setDescription, setPrice } = this.props;

    switch (event.target.name) {
      case "name":
        setName(event.target.value);
        break;
      case "description":
        setDescription(event.target.value);
        break;
      case "price":
        setPrice(event.target.value);
        break;
      default:
        console.error("This should not happen. If you see this... RUN!!");
    }
  };

  render() {
    const { name, description, price } = this.props.textInputs;
    return (
      <>
        <Form.Group controlId="name">
          <Form.Label>Název nabídky</Form.Label>
          <Form.Control
            name="name"
            type="text"
            placeholder="Motorová pila, strunová sekačka..."
            value={name}
            onChange={this.handleInputChange}
          />
        </Form.Group>

        <Form.Group controlId="description">
          <Form.Label>Popis nabídky</Form.Label>
          <Form.Control
            name="description"
            as="textarea"
            rows="3"
            value={description}
            onChange={this.handleInputChange}
          />
        </Form.Group>

        <Form.Group controlId="price">
          <Form.Label>Cena za pronájem</Form.Label>
          <Form.Control
            name="price"
            type="number"
            value={price || ""}
            placeholder="Zadejte cenu za den"
            onChange={this.handleInputChange}
          />
        </Form.Group>
      </>
    );
  }
}

const mapStateToProps = state => {
  return {
    textInputs: state.textInputs
  };
};

export default connect(
  mapStateToProps,
  {
    setName,
    setDescription,
    setPrice
  }
)(TextInputs);
