import express from 'express';
import dotenv from 'dotenv';
import bodyParser from 'body-parser';
import cookieParser from 'cookie-parser';

import database from './lib/db/';

dotenv.config();
database.sequelize.sync();

const app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());

const PORT = process.env.PORT || 4000;

app.get('/', (req, res) => {
  res.send('User managment service!');
});

app.listen(PORT, () => console.log(`[user-managment-service] Listening on port ${PORT}!`));
