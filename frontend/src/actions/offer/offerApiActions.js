import {
  GET_CATEGORIES_BEGIN,
  GET_CATEGORIES_FAILURE,
  GET_CATEGORIES_SUCCESS,
  GET_OFFERS_BEGIN,
  GET_OFFERS_SUCCESS
} from "../../constants/apiActionConstants";

export const getOffersInit = () => {
  return {
    type: GET_OFFERS_BEGIN
  };
};

export const getOffersSuccess = data => {
  return {
    type: GET_OFFERS_SUCCESS,
    payload: data
  };
};

export const getOffersFailure = data => {
  return {
    type: GET_CATEGORIES_BEGIN,
    payload: data
  };
};

export const getCategoriesInit = () => {
  return {
    type: GET_CATEGORIES_BEGIN
  };
};

export const getCategoriesSuccess = data => {
  return {
    type: GET_CATEGORIES_SUCCESS,
    payload: data
  };
};

export const getCategoriesFailure = data => {
  return {
    type: GET_CATEGORIES_FAILURE,
    payload: data
  };
};
