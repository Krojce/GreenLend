import {
  SET_CATEGORY_FILTER,
  SET_MAX_PRICE_FILTER,
  SET_MIN_PRICE_FILTER,
  SET_SEARCH_QUERY
} from "../constants/dataActionConstants";

const filterReducerInitialState = {
  categoryFilter: null,
  minPrice: "",
  maxPrice: "",
  searchQuery: ""
};

export const setFilterDateReducer = (
  inputs = filterReducerInitialState,
  action
) => {
  switch (action.type) {
    case SET_CATEGORY_FILTER:
      return { ...inputs, categoryFilter: action.payload };
    case SET_MIN_PRICE_FILTER:
      return { ...inputs, minPrice: action.payload };
    case SET_MAX_PRICE_FILTER:
      return { ...inputs, maxPrice: action.payload };
    case SET_SEARCH_QUERY:
      return { ...inputs, searchQuery: action.payload };
    default:
      return inputs;
  }
};
