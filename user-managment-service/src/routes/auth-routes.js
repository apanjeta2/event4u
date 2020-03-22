import express from 'express';

import AuthController from '../controllers/Auth';

const authRouter = express.Router();

authRouter.post('/login', AuthController.login);

authRouter.post('/signup', AuthController.signUp);

authRouter.get('/check-token', AuthController.checkValidToken, (req, res) => res.sendStatus(200));

export default authRouter;
