import express from 'express';

import UserController from '../controllers/User';
import AuthController from '../controllers/Auth';

const userRouter = express.Router();

userRouter.get('/', AuthController.checkValidToken, UserController.getAllUsers);

userRouter.get('/:username', AuthController.checkValidToken, UserController.getSpecificUser);

userRouter.put('/update', AuthController.checkValidToken, UserController.updateUser);

userRouter.delete('/delete', AuthController.checkValidToken, UserController.deleteUser);

export default userRouter;
