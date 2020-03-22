import jwt from 'jsonwebtoken';
import crypto from 'crypto';

import db from '../lib/db';
import { JWT_SECRET, JWT_EXPIRE_TIME_TOKEN } from '../config/constants';

export const login = async (req, res) => {
  try {
    const { username, password } = req.body;

    if (!username || !password) return res.status(400).end();

    const hashedPassword = crypto
      .createHash('sha256')
      .update(password)
      .digest('base64');

    const result = await db.User.findOne({ where: { username, password: hashedPassword } });

    if (!result) return res.status(401).end();

    const user = {
      id: result.id,
      name: result.name,
      surname: result.surname,
      username: result.username,
    };

    const token = jwt.sign(user, JWT_SECRET, {
      algorithm: 'HS256',
      expiresIn: JWT_EXPIRE_TIME_TOKEN,
    });

    const response = {
      user,
      token,
    };

    res.status(200).json(response);
  } catch (e) {
    console.log('[user-management-service] signUp - ', e.message);
    res.status(400).json({ error: 'Error while trying to login. ' });
  }
};

export const signUp = async (req, res) => {
  try {
    const { username, name, surname, password } = req.body;

    if (!username || !name || !surname || !password) return res.status(400).end();

    const hashedPassword = crypto
      .createHash('sha256')
      .update(password)
      .digest('base64');

    const result = await db.User.create({ username, name, surname, password: hashedPassword });

    if (!result) return res.status(500).end();

    res.sendStatus(204);
  } catch (e) {
    console.log('[user-management-service] signUp - ', e.message);
    res.status(400).json({ error: 'Error creating user. ' });
  }
};

export const checkValidToken = (req, res, next) => {
  const token = req.headers['authorization'] ? req.headers['authorization'].split(' ')[1] : null;

  if (token) {
    jwt.verify(token, JWT_SECRET, (err, user) => {
      if (err) return res.status(401).json({ error: true, message: 'Unauthorized access.' });
      req.user = user;
      next();
    });
  } else {
    return res.status(403).send({
      error: true,
      message: 'No token provided.',
    });
  }
};

export default {
  login,
  signUp,
  checkValidToken,
};
