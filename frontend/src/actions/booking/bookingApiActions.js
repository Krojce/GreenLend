import {
  GET_BOOKING_BEGIN,
  GET_BOOKING_FAILURE,
  GET_BOOKING_SUCCESS
} from "../../constants/apiActionConstants";

export const getBookingsInit = data => {
  return {
    type: GET_BOOKING_BEGIN,
    payload: data
  };
};

export const getBookingsSuccess = data => {
  return {
    type: GET_BOOKING_SUCCESS,
    payload: data
  };
};

export const getBookingsFailure = data => {
  return {
    type: GET_BOOKING_FAILURE,
    payload: data
  };
};
