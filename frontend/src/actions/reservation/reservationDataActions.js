import { CHANGE_FILTER } from "../../constants/dataActionConstants";

export const changeFilter = data => {
  return {
    type: CHANGE_FILTER,
    payload: data
  };
};
