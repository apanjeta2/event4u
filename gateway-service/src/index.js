import express from 'express';
import dotenv from 'dotenv';
import bodyParser from 'body-parser';
import cookieParser from 'cookie-parser';
import cors from 'cors';
import proxy from 'express-http-proxy';
import { Eureka } from 'eureka-js-client';

import serviceHelper from './lib/helpers/services-helper';

import { PORT, FRONTEND_URL, FULL_BASE_URL, EUREKA_HOST_BASE_URL, BACKEND_HOST_BASE_URL, SERVICES } from './config/constants';

dotenv.config();

const app = express();
const APPLICATION_NAME = 'gateway-service';

const client = new Eureka({
  instance: {
    app: APPLICATION_NAME,
    hostName: BACKEND_HOST_BASE_URL,
    ipAddr: '127.0.0.1',
    statusPageUrl: FULL_BASE_URL,
    homePageUrl: FULL_BASE_URL,
    vipAddress: APPLICATION_NAME,
    port: {
      $: PORT,
      '@enabled': 'true',
    },
    dataCenterInfo: {
      '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
      name: 'MyOwn',
    },
    registerWithEureka: true,
    fetchRegistry: true,
  },
  eureka: {
    host: EUREKA_HOST_BASE_URL,
    port: 8761,
    servicePath: '/eureka/apps/',
  },
});

client.start((error) => {
  console.log('[gateway-service]', error || 'Eureka connected!');
  app.use(
    '*',
    cors({
      origin: FRONTEND_URL,
      optionsSuccessStatus: 200,
    })
  );

  // u slucaju ako je direct proxy
  const userManagementServiceUrl = serviceHelper.getServiceUrl(SERVICES.USER_MANAGEMENT_SERVICE);

  app.use(
    '/aggregator',
    proxy(userManagementServiceUrl, {
      filter: (req, res) => req.path.includes('/api/auth') || req.path.includes('/api/users'),
    })
  );

  app.use(bodyParser.json());
  app.use(bodyParser.urlencoded({ extended: false }));
  app.use(cookieParser());

  // u slucaju da treba endpoint kao proxy
  // app.use('/agregator/api/events/nesto/bla', eventsServiceRoutes); ispod ovdje
});

app.listen(PORT, () => console.log(`[gateway-service] Listening on port ${PORT}!`));

export default client;
