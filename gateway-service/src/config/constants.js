import dotenv from 'dotenv';

dotenv.config();

export const PORT = process.env.PORT || 4200;

export const FRONTEND_URL = process.env.FRONTEND_URL;
export const EUREKA_HOST_BASE_URL = process.env.EUREKA_HOST_BASE_URL;
export const BACKEND_HOST_BASE_URL = process.env.BACKEND_HOST_BASE_URL;
export const FULL_BASE_URL = process.env.FULL_BASE_URL;

// list of services
export const SERVICES = {
  EVENT_SERVICE: 'EVENT-SERVICE',
  SYSTEM_EVENTS_SERVICE: 'SYSTEM-EVENTS-SERVICE',
  USER_MANAGEMENT_SERVICE: 'USER-MANAGEMENT-SERVICE',
};
