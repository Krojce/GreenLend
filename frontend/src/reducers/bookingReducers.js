import {
  GET_BOOKING_BEGIN,
  GET_BOOKING_FAILURE,
  GET_BOOKING_SUCCESS
} from "../constants/apiActionConstants";

const getInitialBookingsState = {
  bookings: [],
  loading: false,
  error: null
};

export const getBookingsReducer = (state = getInitialBookingsState, action) => {
  switch (action.type) {
    case GET_BOOKING_BEGIN:
      return { ...state, loading: true };
    case GET_BOOKING_SUCCESS:
      return {
        ...state,
        loading: false,
        bookings: action.payload,
        error: null
      };
    case GET_BOOKING_FAILURE:
      return { ...state, loading: false, error: action.payload };
    default:
      return state;
  }
};
