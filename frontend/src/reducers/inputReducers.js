import {
  RESET_REGISTER_INPUTS,
  SET_CATEGORIES,
  SET_DESCRIPTION,
  SET_EMAIL,
  SET_END_DATE,
  SET_FIRSTNAME,
  SET_LASTNAME,
  SET_NAME,
  SET_PASSWORD,
  SET_PHONE_NUMBER,
  SET_PHOTO,
  SET_PRICE,
  SET_START_DATE,
  SET_USERNAME
} from "../constants/dataActionConstants";

export const setPhotoReducer = (photo = null, action) => {
  switch (action.type) {
    case SET_PHOTO:
      return action.payload;
    default:
      return photo;
  }
};

const textInputsInitialState = {
  name: "",
  description: "",
  price: null,
  categories: []
};

export const setTextInputsReducer = (
  inputs = textInputsInitialState,
  action
) => {
  switch (action.type) {
    case SET_NAME:
      return { ...inputs, name: action.payload };
    case SET_DESCRIPTION:
      return { ...inputs, description: action.payload };
    case SET_PRICE:
      return { ...inputs, price: action.payload };
    case SET_CATEGORIES:
      return { ...inputs, categories: action.payload };
    default:
      return inputs;
  }
};

const dateInputsInitialState = {
  from: new Date().toISOString(),
  to: new Date().toISOString()
};

export const setDateInputsReducer = (
  inputs = dateInputsInitialState,
  action
) => {
  switch (action.type) {
    case SET_START_DATE:
      return { ...inputs, from: action.payload };
    case SET_END_DATE:
      return { ...inputs, to: action.payload };
    default:
      return inputs;
  }
};

const loginInputsInitialState = {
  username: "",
  password: ""
};

export const setLoginInputsReducer = (
  inputs = loginInputsInitialState,
  action
) => {
  switch (action.type) {
    case SET_USERNAME:
      return { ...inputs, username: action.payload };
    case SET_PASSWORD:
      return { ...inputs, password: action.payload };
    default:
      return inputs;
  }
};

const registerInputsInitialState = {
  username: "",
  password: "",
  firstName: "",
  lastName: "",
  email: "",
  phone: 0
};

export const setRegisterInputsReducer = (
  inputs = registerInputsInitialState,
  action
) => {
  switch (action.type) {
    case SET_USERNAME:
      return { ...inputs, username: action.payload };
    case SET_PASSWORD:
      return { ...inputs, password: action.payload };
    case SET_FIRSTNAME:
      return { ...inputs, firstName: action.payload };
    case SET_LASTNAME:
      return { ...inputs, lastName: action.payload };
    case SET_EMAIL:
      return { ...inputs, email: action.payload };
    case SET_PHONE_NUMBER:
      return { ...inputs, phone: action.payload };
    case RESET_REGISTER_INPUTS:
      return registerInputsInitialState;
    default:
      return inputs;
  }
};
