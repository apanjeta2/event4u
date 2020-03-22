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

const password = crypto
  .createHash('sha256')
  .update(process.env.DEFAULT_PASSWORD)
  .digest('base64');

export const DEFAULT_USERS = [
  { username: 'masha', name: 'Haris', surname: 'Masovic', password },
  { username: 'dzenana', name: 'Dzenana', surname: 'Sabovic', password },
  { username: 'ajla', name: 'Ajla', surname: 'Panjeta', password },
];
