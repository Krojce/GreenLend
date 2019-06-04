import React, { Component } from "react";
import { Card, Form } from "react-bootstrap";
import { connect } from "react-redux";
import { setPhoto } from "../../actions/item/itemDataActions";
import Dropzone from "react-dropzone";
import { getBase64 } from "../../images/encoder";

class CustomDropzone extends Component {
  handleFile = file => {
    const { setPhoto } = this.props;
    getBase64(file[0]).then(image => setPhoto(image));
  };

  render() {
    const { photo } = this.props;
    const image =
      photo !== null ? (
        <img src={photo} width="100px" height="100px" alt={"Inserted"}/>
      ) : null;
    return (
      <Form.Group controlId="photo" className="mt-1">
        <Dropzone onDrop={acceptedFiles => this.handleFile(acceptedFiles)}>
          {({ getRootProps, getInputProps }) => (
            <Card bg="light" style={{ cursor: "pointer" }} {...getRootProps()}>
              <input {...getInputProps()} />
              <Card.Body>
                <Card.Title>Sem přesuňte fotky nabídky</Card.Title>
                <Card.Text>...nebo klikněte a vyberte je ručně</Card.Text>
              </Card.Body>
              {image}
            </Card>
          )}
        </Dropzone>
      </Form.Group>
    );
  }
}

const mapStateToProps = state => {
  return {
    photo: state.photo
  };
};

export default connect(
  mapStateToProps,
  {
    setPhoto
  }
)(CustomDropzone);
