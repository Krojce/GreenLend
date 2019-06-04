import {
  GET_CHAT_BEGIN,
  GET_CHAT_FAILURE,
  GET_CHAT_SUCCESS
} from "../constants/apiActionConstants";
import {
  SET_CURRENT_THREAD,
  SET_MESSAGE
} from "../constants/dataActionConstants";

const getInitialChatState = {
  chat: [],
  loading: false,
  error: null
};

export const getChatReducer = (state = getInitialChatState, action) => {
  switch (action.type) {
    case GET_CHAT_BEGIN:
      return { ...state, loading: true };
    case GET_CHAT_SUCCESS:
      return {
        ...state,
        loading: false,
        chat: action.payload,
        error: null
      };
    case GET_CHAT_FAILURE:
      return { ...state, loading: false, error: action.payload };
    default:
      return state;
  }
};

const getInitialChatDataState = {
  currentThread: null,
  message: ""
};

export const getChatDataReducer = (state = getInitialChatDataState, action) => {
  switch (action.type) {
    case SET_CURRENT_THREAD:
      console.log("THREAD xxx", state.currentThread);
      return { ...state, currentThread: action.payload };
    case SET_MESSAGE:
      return { ...state, message: action.payload };
    default:
      return state;
  }
};
