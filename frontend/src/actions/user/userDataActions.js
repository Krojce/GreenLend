import {
  RESET_REGISTER_INPUTS,
  SET_EMAIL,
  SET_FIRSTNAME,
  SET_LASTNAME,
  SET_PASSWORD,
  SET_PHONE_NUMBER,
  SET_USER_ADDRESS,
  SET_USERNAME
} from "../../constants/dataActionConstants";

export const setUsername = username => {
  return {
    type: SET_USERNAME,
    payload: username
  };
};

export const setPassword = password => {
  return {
    type: SET_PASSWORD,
    payload: password
  };
};

export const setFirstname = firstname => {
  return {
    type: SET_FIRSTNAME,
    payload: firstname
  };
};

export const setLastname = lastname => {
  return {
    type: SET_LASTNAME,
    payload: lastname
  };
};

export const setEmail = email => {
  return {
    type: SET_EMAIL,
    payload: email
  };
};

export const setPhoneNumber = phoneNumber => {
  return {
    type: SET_PHONE_NUMBER,
    payload: phoneNumber
  };
};

export const setUserAddress = address => {
  return {
    type: SET_USER_ADDRESS,
    payload: address
  };
};

export const resetRegisterInputs = () => {
  return {
    type: RESET_REGISTER_INPUTS
  };
};
