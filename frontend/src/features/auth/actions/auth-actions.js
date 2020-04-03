import cookiesHelper from '../../../core/helpers/cookies-helper';

import { AUTH_ACTIONS } from '../constants/auth-constants';

export const handleLogin = (username, password, history) => ({
  type: AUTH_ACTIONS.HANDLE_LOGIN,
  username,
  password,
  history,
});

export const handleLoginInProgress = status => ({
  type: AUTH_ACTIONS.HANDLE_LOGIN_IN_PROGRESS,
  status,
});

export const handleLoginSuccess = data => ({
  type: AUTH_ACTIONS.HANDLE_LOGIN_SUCCESS,
  data,
});

export const handleSignup = (data, history) => ({
  type: AUTH_ACTIONS.HANDLE_SIGNUP,
  data,
  history,
});

export const handleSignupInProgress = status => ({
  type: AUTH_ACTIONS.HANDLE_SIGNUP_IN_PROGRESS,
  status,
});

export const handleLogout = () => {
  cookiesHelper.removeCookie('token');

  return { type: AUTH_ACTIONS.HANDLE_LOGOUT };
};
