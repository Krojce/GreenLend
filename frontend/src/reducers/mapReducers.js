import {
  SET_CURRENT_CENTER,
  SET_HOME_TITLE,
  SET_HOVER_ITEM,
  SET_IS_CENTERED,
  SET_LOCATION_TITLE,
  SET_USER_LOCATION,
  SET_USER_LOCATION_ALLOWED
} from "../constants/dataActionConstants";

const mapReducerInitialState = {
  isCentered: false,
  currentCenter: [14.2859323, 49.8950657],
  userLocation: null,
  userLocationAllowed: false,
  homeTitle: "Vycentrovat na vaši adresu",
  locationTitle: "Vycentrovat na aktuální polohu",
  hoverItem: null
};

export const setMapReducer = (inputs = mapReducerInitialState, action) => {
  switch (action.type) {
    case SET_IS_CENTERED:
      return { ...inputs, isCentered: action.payload };
    case SET_CURRENT_CENTER:
      return { ...inputs, currentCenter: action.payload };
    case SET_USER_LOCATION:
      return { ...inputs, userLocation: action.payload };
    case SET_USER_LOCATION_ALLOWED:
      return { ...inputs, userLocationAllowed: action.payload };
    case SET_HOME_TITLE:
      return { ...inputs, homeTitle: action.payload };
    case SET_LOCATION_TITLE:
      return { ...inputs, locationTitle: action.payload };
    case SET_HOVER_ITEM:
      return { ...inputs, hoverItem: action.payload };
    default:
      return inputs;
  }
};
