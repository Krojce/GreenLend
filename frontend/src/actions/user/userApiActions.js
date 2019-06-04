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
} from "../../constants/apiActionConstants";

export const loginInit = data => {
  return {
    type: LOGIN_BEGIN,
    payload: data
  };
};

export const loginSuccess = data => {
  return {
    type: LOGIN_SUCCESS,
    payload: data
  };
};

export const loginFailure = data => {
  return {
    type: LOGIN_FAILURE,
    payload: data
  };
};

export const registerInit = data => {
  return {
    type: REGISTER_BEGIN,
    payload: data
  };
};

export const registerSuccess = data => {
  return {
    type: REGISTER_SUCCESS,
    payload: data
  };
};

export const registerFailure = data => {
  return {
    type: REGISTER_FAILURE,
    payload: data
  };
};

export const resetRegisterInfo = () => {
  return {
    type: RESET_REGISTER_INFO
  };
};

export const addAddressInit = data => {
  return {
    type: ADD_ADDRESS_BEGIN,
    payload: data
  };
};

export const addAddressSuccess = data => {
  return {
    type: ADD_ADDRESS_SUCCESS,
    payload: data
  };
};

export const addAddressFailure = data => {
  return {
    type: ADD_ADDRESS_FAILURE,
    payload: data
  };
};
