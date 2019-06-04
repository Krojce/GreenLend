import {
  SET_CATEGORY_FILTER,
  SET_MAX_PRICE_FILTER,
  SET_MIN_PRICE_FILTER,
  SET_SEARCH_QUERY
} from "../../constants/dataActionConstants";

export const setCategoryFilter = category => {
  return {
    type: SET_CATEGORY_FILTER,
    payload: category
  };
};

export const setMinPriceFilter = minPrice => {
  return {
    type: SET_MIN_PRICE_FILTER,
    payload: minPrice
  };
};

export const setMaxPriceFilter = maxPrice => {
  return {
    type: SET_MAX_PRICE_FILTER,
    payload: maxPrice
  };
};

export const setSearchQuery = searchQuery => {
  return {
    type: SET_SEARCH_QUERY,
    payload: searchQuery
  };
};
