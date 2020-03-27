import axios from '../config/axios-config';

const requestLogin = data => {
  return axios({
    method: 'POST',
    url: '/api/auth/login',
    data,
  });
};

const requestSignup = data => {
  return axios({
    method: 'POST',
    url: '/api/auth/signup',
    data,
  });
};

export default {
  requestLogin,
  requestSignup,
};
