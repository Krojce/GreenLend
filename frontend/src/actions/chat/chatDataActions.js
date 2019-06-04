import {
  SET_CURRENT_THREAD,
  SET_MESSAGE
} from "../../constants/dataActionConstants";

export const setCurrentThread = data => {
  return {
    type: SET_CURRENT_THREAD,
    payload: data
  };
};

export const setMessage = data => {
  return {
    type: SET_MESSAGE,
    payload: data
  };
};
