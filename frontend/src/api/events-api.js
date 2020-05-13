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

export default {
  requestGetCategories,
  requestGetEventsByCategory,
};
