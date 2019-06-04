import { combineReducers } from "redux";
import {
  setDateInputsReducer,
  setLoginInputsReducer,
  setPhotoReducer,
  setRegisterInputsReducer,
  setTextInputsReducer
} from "../reducers/inputReducers";
import { getItemReducer, postItemReducer, setCurrentItemReducer } from "../reducers/itemReducers";
import { loginUserReducer, registerUserReducer, setAddressInfoReducer } from "../reducers/userReducers";
import { reservationsReducer } from "../reducers/reservationReducers";
import { getCategoriesReducer, getOffersReducer } from "../reducers/offersReducers";
import storage from "redux-persist/lib/storage";
import { LOGOUT } from "../constants/dataActionConstants";
import { getBookingsReducer } from "../reducers/bookingReducers";
import { getChatDataReducer, getChatReducer } from "../reducers/chatReducers";
import { setFilterDateReducer } from "../reducers/filterReducers";
import { setMapReducer } from "../reducers/mapReducers";

export const rootReducer = (state, action) => {
  if (action.type === LOGOUT) {
    storage.removeItem("persist:root");
    state = undefined;
  }
  return appReducer(state, action);
};

export const appReducer = combineReducers({
  photo: setPhotoReducer,
  textInputs: setTextInputsReducer,
  dateInputs: setDateInputsReducer,
  loginInputs: setLoginInputsReducer,
  registerInputs: setRegisterInputsReducer,
  addressInputs: setAddressInfoReducer,
  newItemInfo: postItemReducer,
  itemList: getItemReducer,
  userInfo: loginUserReducer,
  registerInfo: registerUserReducer,
  offerList: getOffersReducer,
  reservations: reservationsReducer,
  currentItemStore: setCurrentItemReducer,
  bookingsInfo: getBookingsReducer,
  categoriesInfo: getCategoriesReducer,
  chatInfo: getChatReducer,
  chatData: getChatDataReducer,
  filterData: setFilterDateReducer,
  mapData: setMapReducer
});
