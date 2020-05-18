import axios from '../config/axios-config';

const requestGetCategories = () => {
  return axios({
    method: 'GET',
    url: `/events-micro/categories`,
  });
};

const requestGetEventsByCategory = categoryId => {
  return axios({
    method: 'GET',
    url: `/events-micro/events/category/${categoryId}`,
  });
};

const requestGetEventsByCategoryLoggedUser = categoryId => {
  return axios({
    method: 'GET',
    url: `/events-micro/events/category-user/${categoryId}`,
  });
};

const requestMarkEventAsInterested = eventId => {
  return axios({
    method: 'POST',
    url: `/events-micro/interested/${eventId}`,
  });
};

const requestMarkEventAsGoingTo = eventId => {
  return axios({
    method: 'POST',
    url: `/events-micro/going/${eventId}`,
  });
};

const requestRemoveMark = eventId => {
  return axios({
    method: 'DELETE',
    url: `/events-micro/removeMark/${eventId}`,
  });
};

const requestGetCategoryById = categoryId => {
  return axios({
    method: 'GET',
    url: `/events-micro/categories/${categoryId}`,
  });
};

const requestGetEventById = eventId => {
  return axios({
    method: 'GET',
    url: `/events-micro/events/${eventId}`,
  });
};

export default {
  requestGetCategories,
  requestGetEventsByCategory,
  requestGetEventsByCategoryLoggedUser,
  requestMarkEventAsInterested,
  requestMarkEventAsGoingTo,
  requestRemoveMark,
  requestGetCategoryById,
  requestGetEventById,
};
