import dotenv from 'dotenv';
import crypto from 'crypto';

dotenv.config();

export const PORT = process.env.PORT || 4000;
export const JWT_SECRET = process.env.JWT_SECRET;
export const JWT_EXPIRE_TIME_TOKEN = process.env.JWT_EXPIRE_TIME_TOKEN;

export const DB_USERNAME = process.env.DB_USERNAME;
export const DB_PASSWORD = process.env.DB_PASSWORD;
export const DB_HOST = process.env.DB_HOST;
export const DB_PORT = process.env.DB_PORT;
export const DATABASE = process.env.DATABASE;
export const INITIAL_DB_SETUP = process.env.INITIAL_DB_SETUP === 'true' ? true : false;
export const EUREKA_HOST_BASE_URL = process.env.EUREKA_HOST_BASE_URL;
export const BACKEND_HOST_BASE_URL = process.env.BACKEND_HOST_BASE_URL;
export const FULL_BASE_URL = process.env.FULL_BASE_URL;

export const STORAGE_BUCKET = process.env.STORAGE_BUCKET;
export const FIREBASE_DATABASE_URL = process.env.FIREBASE_DATABASE_URL;
export const FIREBASE_STORAGE_URL = process.env.FIREBASE_STORAGE_URL;

const dateOfRegistration = new Date();
const password = crypto.createHash('sha256').update(process.env.DEFAULT_PASSWORD).digest('base64');

export const DEFAULT_PICTURE = 'https://storage.googleapis.com/event4u-d02b0.appspot.com/1588341903025_1f2cfd09c412c01124a5a13c11de5fd2.jpg';

export const DEFAULT_USERS = [
  { username: 'masha', name: 'Haris', surname: 'Masovic', password, picture: DEFAULT_PICTURE, dateOfRegistration },
  { username: 'dzenana', name: 'Dzenana', surname: 'Sabovic', password, picture: DEFAULT_PICTURE, dateOfRegistration },
  { username: 'ajla', name: 'Ajla', surname: 'Panjeta', password, picture: DEFAULT_PICTURE, dateOfRegistration },
];

// list of services
export const SERVICES = {
  EVENT_SERVICE: 'EVENT-SERVICE',
  SYSTEM_EVENTS_SERVICE: 'SYSTEM-EVENTS-SERVICE',
  USER_MANAGEMENT_SERVICE: 'USER-MANAGEMENT-SERVICE',
};

// grpc action type & resource names
export const GRPC_ACTION_TYPES = {
  CREATE: 0,
  DELETE: 1,
  GET: 2,
  UPDATE: 3,
};

export const GRPC_USER_RESOURCE = 'User';
