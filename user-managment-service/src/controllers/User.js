import crypto from 'crypto';
import jwt from 'jsonwebtoken';
import axios from 'axios';

import db from '../lib/db';
import servicesHelper from '../lib/helpers/services-helper';

import { JWT_SECRET, JWT_EXPIRE_TIME_TOKEN } from '../config/constants';

export const getAllUsers = async (req, res) => {
  try {
    const result = await db.User.findAll();

    res.status(200).json(result);
  } catch (e) {
    console.log('[user-management-service] getAllUsers - ', e.message);
    res.status(500).json({ error: 'Error getting all users. ' });
  }
};

export const getSpecificUser = async (req, res) => {
  try {
    const result = await db.User.findOne({ where: { username: req.params.username } });

    if (!result) {
      return res.status(404).send({
        error: true,
        message: 'User not found.',
      });
    }

    res.status(200).json(result);
  } catch (e) {
    console.log('[user-management-service] getSpecificUser - ', e.message);
    res.status(500).json({ error: 'Error getting specific user. ' });
  }
};

export const updateUser = async (req, res) => {
  try {
    const username = req.user.username;

    const dataReceived = await db.User.findOne({ where: { username } });

    if (!dataReceived) {
      return res.status(404).send({
        error: true,
        message: 'User not found.',
      });
    }

    if (req.body.password) {
      req.body.password = crypto.createHash('sha256').update(req.body.password).digest('base64');
    }

    await db.User.update(req.body, { where: { username } });

    const result = await db.User.findOne({ where: { username: req.body.username || username } });

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
    console.log('[user-management-service] updateUser - ', e.message);
    res.status(500).json({ error: 'Error updating user. ' });
  }
};

export const deleteUser = async (req, res) => {
  try {
    const username = req.user.username;

    const result = await db.User.findOne({ where: { username } });

    if (!result) {
      return res.status(404).send({
        error: true,
        message: 'User not found.',
      });
    }

    await db.User.destroy({ where: { username: result.username } });

    // event
    const deleteEventUrl = servicesHelper.getServiceUrl();
    await axios.delete(`${deleteEventUrl}/users/${result.id}`);

    res.status(200).json({ message: 'User successfully deleted. ' });
  } catch (e) {
    console.log('[user-management-service] deleteUser - ', e.message);
    res.status(400).json({ error: 'Error deleting user. ' });
  }
};

export default {
  getAllUsers,
  getSpecificUser,
  updateUser,
  deleteUser,
};
