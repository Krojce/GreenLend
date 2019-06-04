import axios from "axios";

export const greenLendApi = axios.create({
  baseURL: "http://localhost:8080",
  timeout: 10000,
  withCredentials: true,
  headers: {
    "Content-Type": "application/json;charset=UTF-8"
  }
});

export const URL = {
  categories: () => `/categories`,
  chat: id => `/chat/${id}`,
  chatMembers: id => `/chat/${id}/members`,
  chatOverview: () => `/chat/overview`,
  offers: () => `/lendoffers`,
  offersLatest: () => `/lendoffers/latest`,
  offersNearest: () => `/lendoffers/nearest`,
  offersSearch: () => `/lendoffers/search`,
  offer: id => `/lendoffers/${id}`,
  offerAvailability: id => `/lendoffers/${id}/intervals`,
  offerBookings: id => `/lendoffers/${id}/bookings`,
  offerPhoto: id => `/lendoffers/${id}/photo`,
  login: () => `/login`,
  logout: () => `/logout`,
  users: () => `/users`,
  user: id => `/users/${id}`,
  userAddress: id => `/users/${id}/address`,
  userLends: id => `/users/${id}/lends`,
  userBorrows: id => `/users/${id}/borrows`,
  userOffers: id => `/users/${id}/offers`,
  register: () => `/registration`
};

// Offer getters
export const getOffers = offset =>
  greenLendApi.get(URL.lendoffers(), { params: { offset } });
export const getAvailability = id =>
  greenLendApi.get(URL.offerAvailability(id));
export const getBookings = id => greenLendApi.get(URL.offerBookings(id));
export const getOfferById = id => greenLendApi.get(URL.lendoffer(id));
export const getOfferPhoto = id => greenLendApi.get(URL.offerPhoto(id));
export const getNearestOffers = (lat, lon, offset) =>
  greenLendApi.get(URL.offersNearest(), { params: { lat, lon, offset } });
export const getLatestOffers = offset =>
  greenLendApi.get(URL.offersLatest(), { params: { offset } });
export const searchOffers = (searchParams, offset) =>
  greenLendApi.get(URL.offersSearch(), { params: { ...searchParams, offset } });
export const getPhoto = id => greenLendApi.get(URL.offerPhoto(id));

// Offer setters
export const addOffer = offer => greenLendApi.post(URL.offers(), offer);
export const addOfferPhoto = (id, photo) =>
  greenLendApi.post(URL.offerPhoto(id), photo);
export const addAvailability = (id, availability) =>
  greenLendApi.post(URL.offerAvailability(id), availability);
export const editOffer = (id, offer) => greenLendApi.put(URL.offer(id), offer);

// Session
export const login = credentials => greenLendApi.post(URL.login(), credentials);
export const logout = () => greenLendApi.post(URL.logout());

// User
export const register = user => greenLendApi.post(URL.register(), user);
export const addAddress = (id, address) =>
  greenLendApi.post(URL.userAddress(id), address);
export const getLends = (id, present, offset) =>
  greenLendApi.get(URL.userLends(id), { params: { present, offset } });
export const getBorrows = id => greenLendApi.get(URL.userBorrows(id));
export const getUsersOffers = id => greenLendApi.get(URL.userOffers(id));

// Chat
export const getChats = () => greenLendApi.get(URL.chatOverview());
export const getMessages = (id, offset) =>
  greenLendApi.get(URL.chat(id), { params: { offset } });
export const sendMessage = (id, message) =>
  greenLendApi.post(URL.chat(id), message);

// Booking an offer
export const book = (id, interval) =>
  greenLendApi.post(URL.offerBookings(id), interval);

// Categories
export const getCategories = () => greenLendApi.get(URL.categories());
export const addCategory = category =>
  greenLendApi.post(URL.categories(), category);
