import {
  GET_CHAT_BEGIN,
  GET_CHAT_FAILURE,
  GET_CHAT_SUCCESS
} from "../../constants/apiActionConstants";

export const getChatInit = data => {
  return {
    type: GET_CHAT_BEGIN,
    payload: data
  };
};

export const getChatSuccess = data => {
  return {
    type: GET_CHAT_SUCCESS,
    payload: data
  };
};

export const getChatFailure = data => {
  return {
    type: GET_CHAT_FAILURE,
    payload: data
  };
};
