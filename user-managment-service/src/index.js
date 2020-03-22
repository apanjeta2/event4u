import express from 'express';
import dotenv from 'dotenv';
import bodyParser from 'body-parser';
import cookieParser from 'cookie-parser';

import database from './lib/db/';
import userRoutes from './routes/user-routes';
import authRoutes from './routes/auth-routes';

import { DEFAULT_USERS, PORT, INITIAL_DB_SETUP } from './config/constants';

dotenv.config();
database.sequelize.sync({ force: INITIAL_DB_SETUP }).then(async () => {
  if (INITIAL_DB_SETUP) {
    await database.User.bulkCreate(DEFAULT_USERS);
    console.log('[user-managment-service] Successfully crated a new database and imported initial data!');
  }
});

const app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());

app.use('/api/users', userRoutes);
app.use('/api/auth', authRoutes);

app.listen(PORT, () => console.log(`[user-managment-service] Listening on port ${PORT}!`));
