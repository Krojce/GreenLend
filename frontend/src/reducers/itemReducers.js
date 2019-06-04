import {
  ADD_ITEM_BEGIN,
  ADD_ITEM_FAILURE,
  ADD_ITEM_SUCCESS,
  GET_ITEMS_BEGIN,
  GET_ITEMS_FAILURE,
  GET_ITEMS_SUCCESS
} from "../constants/apiActionConstants";
import { SET_CURRENT_ITEM, SET_SHOW_MODAL } from "../constants/dataActionConstants";

const postItemInitialState = {
  error: null,
  loading: false
};

export const postItemReducer = (state = postItemInitialState, action) => {
  switch (action.type) {
    case ADD_ITEM_BEGIN:
      return { ...state, loading: true };
    case ADD_ITEM_SUCCESS:
      return { ...state, loading: false, error: null };
    case ADD_ITEM_FAILURE:
      return { loading: false, error: action.payload };
    default:
      return state;
  }
};

const getInitialState = {
  items: [],
  loading: false,
  error: null
};

export const getItemReducer = (state = getInitialState, action) => {
  switch (action.type) {
    case GET_ITEMS_BEGIN:
      return { ...state, loading: true };
    case GET_ITEMS_SUCCESS:
      return { ...state, loading: false, items: action.payload, error: null };
    case GET_ITEMS_FAILURE:
      return { ...state, loading: false, error: action.payload };
    default:
      return state;
  }
};

const currentItemInitialState = {
  currentItem: null,
  showModal: false
};

export const setCurrentItemReducer = (
  state = currentItemInitialState,
  action
) => {
  switch (action.type) {
    case SET_CURRENT_ITEM:
      return { ...state, currentItem: action.payload };
    case SET_SHOW_MODAL:
      return { ...state, showModal: action.payload };
    default:
      return state;
  }
};
