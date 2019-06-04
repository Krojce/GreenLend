import React, { Component } from "react";
import { Redirect } from "react-router-dom";
import { connect } from "react-redux";
import { book, getBookings } from "../../../apis/greenLendApi";
import { getBookingsFailure, getBookingsInit, getBookingsSuccess } from "../../../actions/booking/bookingApiActions";
import { changeFilter } from "../../../actions/reservation/reservationDataActions";
import CustomDatePicker from "../../utils/CustomDatePicker";
import { Button, Col, Container, Form, Jumbotron, Row, Spinner } from "react-bootstrap";
import { ErrorAlert } from "../../utils/ErrorAlert";
import { ReservationFilter } from "../reservation/ReservationFilter";

class Booking extends Component {

  state = {
    loading: false,
    shouldRedirect: false,
    error: null
  }

  createBooking = () => {
    const { currentItemStore, dateInputs } = this.props;
    const { currentItem } = currentItemStore;

    this.setState({ loading: true });

    book(currentItem.lendOfferId, dateInputs)
      .then(() => {
        const { changeFilter } = this.props;
        changeFilter(ReservationFilter.borrows);
        this.setState({ loading: false, shouldRedirect: true, error: null });
      })
      .catch(e => {
        this.setState({ loading: false, error: e.response.data.message })
      })
  };

  componentDidMount() {
    const {
      currentItemStore,
      getBookingsInit,
      getBookingsSuccess,
      getBookingsFailure
    } = this.props;

    getBookingsInit();
    getBookings(currentItemStore.currentItem.lendOfferId)
      .then(result => {
        getBookingsSuccess(result.data);
      })
      .catch(error => {
        //console.log(error.response.data)
        getBookingsFailure(error.response.data.message);
      });
  }

  getDerivedPrice() {
    const { from, to } = this.props.dateInputs;
    const { currentItem } = this.props.currentItemStore;
    let dateFrom = new Date(from);
    let dateTo = new Date(to);
    let daysBetween = Math.round((dateTo - dateFrom) / (1000 * 60 * 60 * 24));
    return (daysBetween + 1) * currentItem.price + "Kč";
  }

  render() {
    if(this.state.shouldRedirect) {
      return <Redirect to="/user/reservations" />
    }

    const { bookings } = this.props.bookingsInfo;
    const { error, loading } = this.state;
    const { currentItem } = this.props.currentItemStore;
    const { user } = this.props.userInfo;
    const { owner } = currentItem;

    let errorAlert = error == null ? null : <ErrorAlert message={error} />;

    return (
      <Container>
        <Jumbotron className="p-5 my-4">
          <Row>
            <Col md={4}>
              <img
                src={currentItem.thumbnail}
                alt={currentItem.name}
                style={{ maxWidth: "100%" }}
              />
            </Col>
            <Col>
              <h1 className="mb-0">{currentItem.name}</h1>
              <p className="text-secondary mb-3">
                {currentItem.categories.map(c => c.name).join(", ") || "-"}
              </p>
              <p>{currentItem.description}</p>
              <p className="text-secondary">
                Přidal uživatel{" "}
                {currentItem.owner.firstname + " " + currentItem.owner.lastname}
              </p>
            </Col>
          </Row>
        </Jumbotron>
        <Jumbotron className="p-5 my-4">
          <span className="h4">Detaily rezervace</span>
          <CustomDatePicker excluded={bookings} />
          <Form.Group>
            <Form.Label>Cena</Form.Label>
            <Form.Control plaintext readOnly value={this.getDerivedPrice()} />
          </Form.Group>
          {errorAlert}
          <Button
            variant="success"
            onClick={this.createBooking}
            disabled={loading || owner.id === user.id}
          >
            {loading ? (
              <Spinner animation="border" variant="light" />
            ) : (
              `Rezervovat předmět`
            )}
          </Button>
        </Jumbotron>
      </Container>
    );
  }
}

const mapStateToProps = state => {
  return {
    currentItemStore: state.currentItemStore,
    bookingsInfo: state.bookingsInfo,
    dateInputs: state.dateInputs,
    userInfo: state.userInfo
  };
};

export default connect(
  mapStateToProps,
  {
    getBookingsInit,
    getBookingsSuccess,
    getBookingsFailure,
    changeFilter
  }
)(Booking);
