import {
  LOGOUT,
  SET_CATEGORIES,
  SET_CURRENT_ITEM,
  SET_DESCRIPTION,
  SET_END_DATE,
  SET_NAME,
  SET_PHOTO,
  SET_PRICE,
  SET_SHOW_MODAL,
  SET_START_DATE
} from "../../constants/dataActionConstants";

export const setPhoto = photo => {
  return {
    type: SET_PHOTO,
    payload: photo
  };
};

export const setName = name => {
  return {
    type: SET_NAME,
    payload: name
  };
};

export const setDescription = description => {
  return {
    type: SET_DESCRIPTION,
    payload: description
  };
};

export const setPrice = price => {
  return {
    type: SET_PRICE,
    payload: price
  };
};

export const setCategories = categories => {
  return {
    type: SET_CATEGORIES,
    payload: categories
  };
};

export const setStartDate = startDate => {
  return {
    type: SET_START_DATE,
    payload: startDate
  };
};

export const setEndDate = endDate => {
  return {
    type: SET_END_DATE,
    payload: endDate
  };
};

export const setCurrentItem = currentItem => {
  return {
    type: SET_CURRENT_ITEM,
    payload: currentItem
  };
};

export const setShowModal = showModal => {
  return {
    type: SET_SHOW_MODAL,
    payload: showModal
  };
};

export const logoutUser = () => {
  return {
    type: LOGOUT
  };
};
