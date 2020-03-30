import cacheHelper from '../../../core/helpers/cookies-helper';

import { AUTH_ACTIONS } from '../constants/auth-constants';

const getAuthInitialState = () => {
  const token = cacheHelper.getCookie('token');

  return {
    userLoggedIn: token ? true : false,
    loginInProgress: false,
    signupInProgress: false,
    token,
    user: {
      id: null,
      name: '',
      surname: '',
      username: '',
    },
  };
};

export const auth = (state = getAuthInitialState(), action) => {
  switch (action.type) {
    case AUTH_ACTIONS.HANDLE_LOGIN_IN_PROGRESS:
      return { ...state, loginInProgress: action.status };
    case AUTH_ACTIONS.HANDLE_LOGIN_SUCCESS:
      const { user, token } = action.data;
      return { ...state, user, token };
    case AUTH_ACTIONS.HANDLE_SIGNUP_IN_PROGRESS:
      return { ...state, signupInProgress: action.status };
    default:
      return state;
  }
};