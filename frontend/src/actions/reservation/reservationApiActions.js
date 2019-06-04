import {
  GET_RESERVATIONS_BEGIN,
  GET_RESERVATIONS_FAILURE,
  GET_RESERVATIONS_SUCCESS,
  GET_USER_BORROWS_BEGIN,
  GET_USER_BORROWS_FAILURE,
  GET_USER_BORROWS_SUCCESS,
  GET_USER_LENDS_BEGIN,
  GET_USER_LENDS_FAILURE,
  GET_USER_LENDS_SUCCESS
} from "../../constants/apiActionConstants";

export const getReservationsInit = () => {
  return {
    type: GET_RESERVATIONS_BEGIN
  };
};

export const getReservationsSuccess = data => {
  return {
    type: GET_RESERVATIONS_SUCCESS,
    payload: data
  };
};

export const getReservationsFailure = data => {
  return {
    type: GET_RESERVATIONS_FAILURE,
    payload: data
  };
};

export const getUserLendsInit = () => {
  return {
    type: GET_USER_LENDS_BEGIN
  };
};

export const getUserLendsSuccess = data => {
  return {
    type: GET_USER_LENDS_SUCCESS,
    payload: data
  };
};

export const getUserLendsFailure = data => {
  return {
    type: GET_USER_LENDS_FAILURE,
    payload: data
  };
};

export const getUserBorrowsInit = () => {
  return {
    type: GET_USER_BORROWS_BEGIN
  };
};

export const getUserBorrowsSuccess = data => {
  return {
    type: GET_USER_BORROWS_SUCCESS,
    payload: data
  };
};

export const getUserBorrowsFailure = data => {
  return {
    type: GET_USER_BORROWS_FAILURE,
    payload: data
  };
};
