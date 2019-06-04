import React, { Component } from "react";
import { Redirect } from "react-router-dom";
import { ReservationFilterSwitch } from "./ReservationFilterSwitch";
import { Header } from "./Header";
import { Container } from "react-bootstrap";
import { connect } from "react-redux";
import { changeFilter } from "../../../actions/reservation/reservationDataActions";
import { ReservationFilter } from "./ReservationFilter";
import { getBorrows, getLends } from "../../../apis/greenLendApi";
import {
  getReservationsFailure,
  getReservationsInit,
  getReservationsSuccess,
  getUserBorrowsFailure,
  getUserBorrowsInit,
  getUserBorrowsSuccess,
  getUserLendsFailure,
  getUserLendsInit,
  getUserLendsSuccess
} from "../../../actions/reservation/reservationApiActions";
import { setCurrentThread } from "../../../actions/chat/chatDataActions";
import { BookingTable } from "./mybookings/BookingTable";
import { Loading } from "../../utils/Loading";
import { ReservationTable } from "./myreservations/ReservationTable";

class Reservations extends Component {

  state = {
    shouldRedirect: false
  }

  componentWillMount() {
    const { filter } = this.props.location.state || this.props.reservations;
    this.updateData(filter);
  }

  updateData = filter => {
    switch (filter) {
      case ReservationFilter.borrows:
        this.showBorrows();
        break;
      case ReservationFilter.lends:
        this.showLends();
        break;
      default:
        return null;
    }
  };

  showBorrows = () => {
    const {
      getUserBorrowsInit,
      getUserBorrowsSuccess,
      getUserBorrowsFailure,
      userInfo
    } = this.props;
    const { user } = userInfo;

    getUserBorrowsInit();
    getBorrows(user.id)
      .then(result => {
        let data = result.data.map(booking => {
          return { ...booking, isLend: false };
        });

        getUserBorrowsSuccess(data);
      })
      .catch(() => {
        getUserBorrowsFailure("Nepodařilo se načíst vaše rezervace!");
      });
  };

  showLends = () => {
    const {
      getUserLendsInit,
      getUserLendsSuccess,
      getUserLendsFailure
    } = this.props;
    const { user } = this.props.userInfo;

    getUserLendsInit();
    getLends(user.id)
      .then(result => {
        let data = result.data.map(booking => {
          return { ...booking, isLend: true };
        });
        getUserLendsSuccess(data);

      })
      .catch(() => {
        getUserLendsFailure("Nepodařilo se načíst rezervace vašich věcí!");
      });
  };

  changeFilterPreference = change => {
    const { changeFilter } = this.props;
    changeFilter(change);
    this.updateData(change);
  };

  redirectToChat = chatId => {
    const { setCurrentThread } = this.props;
    setCurrentThread(chatId);
    this.setState({shouldRedirect: true});
  };

  render() {
    if(this.state.shouldRedirect) {
      return <Redirect to="/user/messages" />;
    }

    const { shownItems, loading, filter } = this.props.reservations;

    let myReservation = true;
    if (filter === 1) {
      myReservation = false;
    }

    return (
      <>
        <Header />
        <Container className="pt-3">
          <ReservationFilterSwitch
            changeFilter={this.changeFilterPreference}
            activeFilter={filter}
          />
          {loading ? (
            <Loading/>
          ) : myReservation ? (
            <ReservationTable
              items={shownItems || []}
              redirect={this.redirectToChat}
            />
          ) : (
            <BookingTable
              items={shownItems || []}
              redirect={this.redirectToChat}
            />
          )}
        </Container>
      </>
    );
  }
}

const mapStateToProps = state => {
  return {
    reservations: state.reservations,
    userInfo: state.userInfo,
    chatData: state.chatData
  };
};

export default connect(
  mapStateToProps,
  {
    changeFilter,
    getReservationsInit,
    getReservationsSuccess,
    getReservationsFailure,
    getUserLendsInit,
    getUserLendsSuccess,
    getUserLendsFailure,
    getUserBorrowsInit,
    getUserBorrowsSuccess,
    getUserBorrowsFailure,
    setCurrentThread
  }
)(Reservations);
