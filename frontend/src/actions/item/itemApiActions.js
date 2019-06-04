import {
  ADD_ITEM_BEGIN,
  ADD_ITEM_FAILURE,
  ADD_ITEM_SUCCESS,
  GET_ITEMS_BEGIN,
  GET_ITEMS_FAILURE,
  GET_ITEMS_SUCCESS
} from "../../constants/apiActionConstants";

export const addNewItemInit = data => {
  return {
    type: ADD_ITEM_BEGIN,
    payload: data
  };
};

export const addNewItemSuccess = data => {
  return {
    type: ADD_ITEM_SUCCESS,
    payload: data
  };
};

export const addNewItemFailure = data => {
  return {
    type: ADD_ITEM_FAILURE,
    payload: data
  };
};

export const getItemsInit = data => {
  return {
    type: GET_ITEMS_BEGIN,
    payload: data
  };
};

export const getItemsSuccess = data => {
  return {
    type: GET_ITEMS_SUCCESS,
    payload: data
  };
};

export const getItemsFailure = data => {
  return {
    type: GET_ITEMS_FAILURE,
    payload: data
  };
};
