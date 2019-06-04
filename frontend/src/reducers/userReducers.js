import {
  ADD_ADDRESS_BEGIN,
  ADD_ADDRESS_FAILURE,
  ADD_ADDRESS_SUCCESS,
  LOGIN_BEGIN,
  LOGIN_FAILURE,
  LOGIN_SUCCESS,
  REGISTER_BEGIN,
  REGISTER_FAILURE,
  REGISTER_SUCCESS,
  RESET_REGISTER_INFO
} from "../constants/apiActionConstants";
import { SET_USER_ADDRESS } from "../constants/dataActionConstants";

const loginUserInitialState = {
  user: {
    id: null,
    firstname: null,
    lastname: null,
    username: null,
    email: null,
    phone: null
  },
  address: {
    id: null,
    city: null,
    street: null,
    zipcode: null,
    latitude: null,
    longitude: null
  },
  error: null,
  loading: false
};

export const loginUserReducer = (state = loginUserInitialState, action) => {
  switch (action.type) {
    case LOGIN_BEGIN:
      return { ...state, loading: true };
    case LOGIN_SUCCESS:
      return {
        ...state,
        loading: false,
        user: action.payload.user,
        address: action.payload.address,
        error: null
      };
    case LOGIN_FAILURE:
      return { ...state, loading: false, error: action.payload };
    case ADD_ADDRESS_BEGIN:
      return { ...state, loading: true };
    case ADD_ADDRESS_SUCCESS:
      return { ...state, loading: false, address: action.payload, error: null };
    case ADD_ADDRESS_FAILURE:
      return { ...state, loading: false, error: action.payload };
    default:
      return state;
  }
};

const registerUserInitialState = {
  error: null,
  loading: false
};

export const registerUserReducer = (
  state = registerUserInitialState,
  action
) => {
  switch (action.type) {
    case REGISTER_BEGIN:
      return { ...state, loading: true };
    case REGISTER_SUCCESS:
      return { ...state, loading: false, error: null };
    case REGISTER_FAILURE:
      return { loading: false, error: action.payload };
    case RESET_REGISTER_INFO:
      return registerUserInitialState;
    default:
      return state;
  }
};

const setAddressInfoInitialState = {
  addressString: ""
};

export const setAddressInfoReducer = (
  state = setAddressInfoInitialState,
  action
) => {
  switch (action.type) {
    case SET_USER_ADDRESS:
      return { addressString: action.payload };
    default:
      return state;
  }
};
