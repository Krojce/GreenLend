import {
  SET_CURRENT_CENTER,
  SET_HOME_TITLE,
  SET_HOVER_ITEM,
  SET_IS_CENTERED,
  SET_LOCATION_TITLE,
  SET_USER_LOCATION,
  SET_USER_LOCATION_ALLOWED
} from "../../constants/dataActionConstants";

export const setIsCentered = bool => {
  return {
    type: SET_IS_CENTERED,
    payload: bool
  };
};

export const setCurrentCenter = location => {
  return {
    type: SET_CURRENT_CENTER,
    payload: location
  };
};

export const setUserLocation = location => {
  return {
    type: SET_USER_LOCATION,
    payload: location
  };
};

export const setUserLocationAllowed = bool => {
  return {
    type: SET_USER_LOCATION_ALLOWED,
    payload: bool
  };
};

export const setHomeTitle = title => {
  return {
    type: SET_HOME_TITLE,
    payload: title
  };
};

export const setLocationTitle = title => {
  return {
    type: SET_LOCATION_TITLE,
    payload: title
  };
};

export const setHoverItem = item => {
  return {
    type: SET_HOVER_ITEM,
    payload: item
  };
};
