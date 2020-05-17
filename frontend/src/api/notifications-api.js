import axios from '../config/axios-config';

const requestGetNotifications = () => {
  return axios({
    method: 'GET',
    url: `/notifications`,
  });
};

const requestGetNotificationById = userId => {
  return axios({
    method: 'GET',
    url: `/notifications/getByUserId/${userId}`,
  });
};

const requestUpdateNotificationRead = notificationId => {
  return axios({
    method: 'PUT',
    url: `/notifications/setAsRead/${notificationId}`,
  });
};

export default {
  requestGetNotifications,
  requestGetNotificationById,
  requestUpdateNotificationRead,
};
