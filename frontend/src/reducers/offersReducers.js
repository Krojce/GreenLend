import {
  GET_CATEGORIES_BEGIN,
  GET_CATEGORIES_FAILURE,
  GET_CATEGORIES_SUCCESS,
  GET_OFFERS_BEGIN,
  GET_OFFERS_FAILURE,
  GET_OFFERS_SUCCESS
} from "../constants/apiActionConstants";

const getOffersInitialState = {
  offers: [],
  loading: false,
  error: null
};

export const getOffersReducer = (state = getOffersInitialState, action) => {
  switch (action.type) {
    case GET_OFFERS_BEGIN:
      return { ...state, loading: true };
    case GET_OFFERS_SUCCESS:
      return { ...state, loading: false, offers: action.payload, error: null };
    case GET_OFFERS_FAILURE:
      return { loading: false, error: action.payload };
    default:
      return state;
  }
};

const getCategoriesInitialState = {
  categories: [],
  loading: false,
  error: null
};

export const getCategoriesReducer = (
  state = getCategoriesInitialState,
  action
) => {
  switch (action.type) {
    case GET_CATEGORIES_BEGIN:
      return { ...state, loading: true };
    case GET_CATEGORIES_SUCCESS:
      return {
        ...state,
        loading: false,
        categories: action.payload,
        error: null
      };
    case GET_CATEGORIES_FAILURE:
      return { loading: false, error: action.payload };
    default:
      return state;
  }
};
