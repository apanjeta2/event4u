import client from '../../index';

import { SERVICES } from '../../config/constants';

const getServiceUrl = (serviceName = SERVICES.EVENT_SERVICE) => {
  const baseUrl = client.getInstancesByAppId(serviceName)[0].homePageUrl;

  switch (serviceName) {
    case SERVICES.EVENT_SERVICE:
    case SERVICES.USER_MANAGEMENT_SERVICE:
    case SERVICES.NOTIFICATION_SERVICE:
      return baseUrl;
    default:
      return null;
  }
};

export default {
  getServiceUrl,
};
