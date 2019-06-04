import { Button, Card, FormControl, InputGroup } from "react-bootstrap";
import React, { Component } from "react";
import { connect } from "react-redux";
import { setUserAddress } from "../../../actions/user/userDataActions";
import { ErrorAlert } from "../../utils/ErrorAlert";

class AddressForm extends Component {
  handleInputChange = event => {
    const { setUserAddress } = this.props;
    setUserAddress(event.target.value);
  };

  handleKeyDown = event => {
    const { validateAddress } = this.props;
    if (event.key === "Enter") {
      validateAddress();
    }
  };

  render() {
    const { validateAddress, addressInputs, userInfo } = this.props;
    const { error } = userInfo;

    console.log(userInfo);

    let errorAlert = null;
    if (error != null) {
      errorAlert = <ErrorAlert message={error}/>;
    }

    return (
      <Card>
        <Card.Header>
          <strong>
            <i className="fas fa-exclamation-triangle mr-2 text-warning" />
            Nemáte vyplněnou adresu
          </strong>
        </Card.Header>
        <Card.Body>
          <p>Dokud si nevyplníte adresu, nebudete moct vkládat nabídky.</p>
          <InputGroup>
            {errorAlert}
            <FormControl
              placeholder="Zadejte vaši adresu"
              onChange={this.handleInputChange}
              onKeyPress={this.handleKeyDown}
              value={addressInputs.addressString}
            />
            <InputGroup.Append>
              <Button variant="outline-warning" onClick={validateAddress}>
                Ověřit
              </Button>
            </InputGroup.Append>
          </InputGroup>
        </Card.Body>
      </Card>
    );
  }
}

const mapStateToProps = state => {
  return {
    addressInputs: state.addressInputs,
    userInfo: state.userInfo
  };
};

export default connect(
  mapStateToProps,
  {
    setUserAddress
  }
)(AddressForm);
