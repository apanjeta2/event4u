import express from 'express';
import dotenv from 'dotenv';
import bodyParser from 'body-parser';
import cookieParser from 'cookie-parser';

dotenv.config();
const app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());

const PORT = process.env.PORT || 4000;

app.get('/', (req, res) => {
  res.send('Auth service!');
});

app.listen(PORT, () => console.log(`[auth-service] Listening on port ${PORT}!`));
