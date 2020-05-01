import axios from '../config/axios-config';

const requestUserProfile = username => {
  return axios({
    method: 'GET',
    url: `/api/users/${username}`,
  });
};

const requestUpdateProfile = data => {
  return axios({
    method: 'PUT',
    url: '/api/users/update',
    data,
  });
};

const requestImageUpload = formData => {
  return axios({
    method: 'POST',
    url: '/api/users/profile-picture',
    data: formData,
  });
};

export default {
  requestUserProfile,
  requestUpdateProfile,
  requestImageUpload,
};
