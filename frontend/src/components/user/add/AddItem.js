import React, { Component } from "react";
import { Redirect } from "react-router-dom";
import { connect } from "react-redux";
import { Button, Card, Col, Container, Form, ProgressBar, Row, Spinner } from "react-bootstrap";
import TextInputs from "./TextInputs";
import { addNewItemFailure, addNewItemInit, addNewItemSuccess } from "../../../actions/item/itemApiActions";
import CustomDropzone from "../../utils/CustomDropzone";
import { addOffer, addOfferPhoto } from "../../../apis/greenLendApi";
import CustomMultipleSelect from "../../utils/CustomMultipleSelect";
import { ErrorAlert } from "../../utils/ErrorAlert";
import { setName, setDescription, setPrice, setPhoto, setCategories } from "../../../actions/item/itemDataActions";

class AddItem extends Component {

  state = {
    shouldRedirect: false
  }

  createOfferObject = () => {
    const { textInputs } = this.props;
    return {
      price: textInputs.price,
      name: textInputs.name,
      description: textInputs.description,
      categories: textInputs.categories
    };
  };

  addItem = () => {
    // Data and callbacks
    const {
      addNewItemInit,
      addNewItemSuccess,
      addNewItemFailure,
      photo
    } = this.props;

    // Init process
    addNewItemInit();

    // Upload text data
    addOffer(this.createOfferObject())
      .then(response => {
        return addOfferPhoto(response.data.lendOfferId, photo);
      })
      .then(response => {
        const { setName, setDescription, setPrice, setPhoto, setCategories } = this.props;
        setName("");
        setDescription("");
        setPrice("");
        setPhoto(null);
        setCategories(null);
        addNewItemSuccess(response.data);
        this.setState({shouldRedirect: true});
      })
      .catch(error => {
        addNewItemFailure(error.response.data.message);
      });
  };

  renderProgressBar() {
    const addItemUploadPercentage = 55;

    if (this.props.newItemInfo.loading)
      return (
        <ProgressBar
          animated
          variant="success"
          now={addItemUploadPercentage}
          className="mt-2"
        />
      );
  }

  render() {
    if(this.state.shouldRedirect) {
      return <Redirect to="/user/offers" />
    }

    const { loading, error } = this.props.newItemInfo;

    let errorAlert = null;

    if (error != null) {
      errorAlert = <ErrorAlert message={error}/>;
    }

    return (
      <Container className="mt-5">
        <Row className="justify-content-center">
          <Col md={10} lg={7}>
            <Card className="shadow p-3">
              <Card.Body>
                <div className="text-center">
                  <i className="fas fa-share text-success fa-3x" />
                  <p className="display-4">Přidejte nabídku</p>
                  <p className="subtitle mb-3">
                    Začněte pronajímat věci, které již nepoužíváte
                  </p>
                </div>
                {errorAlert}
                <Form>
                  <TextInputs />
                  <CustomMultipleSelect />
                  <CustomDropzone />
                  <Button
                    block
                    variant="success"
                    disabled={loading}
                    onClick={this.addItem}
                  >
                    {loading ? (
                      <Spinner animation="border" variant="light" size="sm" />
                    ) : (
                      "Přidat nabídku"
                    )}
                  </Button>
                  {this.renderProgressBar()}
                </Form>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </Container>
    );
  }
}

const mapStateToProps = state => {
  return {
    photo: state.photo,
    textInputs: state.textInputs,
    newItemInfo: state.newItemInfo,
    userInfo: state.userInfo,
    categoriesInfo: state.categoriesInfo
  };
};

export default connect(
  mapStateToProps,
  {
    addNewItemInit,
    addNewItemSuccess,
    addNewItemFailure,
    setName,
    setPhoto,
    setDescription,
    setPrice,
    setCategories
  }
)(AddItem);
