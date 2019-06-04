import storage from "redux-persist/lib/storage";

export const rootPersistConfig = {
  key: "root",
  storage: storage,
  blacklist: [
    "registerInfo",
    "textInputs",
    "dateInputs",
    "loginInputs",
    "addressInputs",
    "registerInfo",
    "registerInputs",
    "bookingsInfo",
    "categoriesInfo",
    "chatInfo",
    "newItemInfo",
    "mapData",
    "photo"
    //"filterData"
  ]
};
