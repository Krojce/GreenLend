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
} from "../constants/apiActionConstants";
import { ReservationFilter } from "../components/user/reservation/ReservationFilter";
import { CHANGE_FILTER } from "../constants/dataActionConstants";

const reservationInitialState = {
  filter: ReservationFilter.borrows,
  shownItems: [],
  loading: false,
  error: null
};

export const reservationsReducer = (
  state = reservationInitialState,
  action
) => {
  switch (action.type) {
    case CHANGE_FILTER:
      return { ...state, filter: action.payload };
    case GET_RESERVATIONS_BEGIN:
      return { ...state, loading: true };
    case GET_RESERVATIONS_SUCCESS:
      return {
        ...state,
        loading: false,
        shownItems: action.payload,
        error: null
      };
    case GET_RESERVATIONS_FAILURE:
      return { loading: false, error: action.payload };
    case GET_USER_LENDS_BEGIN:
      return { ...state, loading: true };
    case GET_USER_LENDS_SUCCESS:
      return {
        ...state,
        loading: false,
        shownItems: action.payload,
        error: null
      };
    case GET_USER_LENDS_FAILURE:
      return { loading: false, error: action.payload };
    case GET_USER_BORROWS_BEGIN:
      return { ...state, loading: true };
    case GET_USER_BORROWS_SUCCESS:
      return {
        ...state,
        loading: false,
        shownItems: action.payload,
        error: null
      };
    case GET_USER_BORROWS_FAILURE:
      return { loading: false, error: action.payload };
    default:
      return state;
  }
};
