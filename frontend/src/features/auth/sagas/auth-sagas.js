import { call, put, takeLatest } from 'redux-saga/effects';

import { handleLoginInProgress, handleLoginSuccess, handleSignupInProgress } from '../actions/auth-actions';
import { handleShowMessage } from '../../snackbar/actions/snackbar-actions';

import cookieHelper from '../../../core/helpers/cookies-helper';
import AuthApi from '../../../api/auth-api';
import { SNACKBAR_SEVERITY_VARIANTS } from '../../snackbar/constants/snackbar-constants';
import { AUTH_ACTIONS } from '../constants/auth-constants';

function* requestLogin({ username, password, history }) {
  try {
    yield put(handleLoginInProgress(true));

    const { data } = yield call(AuthApi.requestLogin, { username, password });

    cookieHelper.setCookie('token', data.token);

    yield put(handleLoginSuccess(data));
    history.push('/');
  } catch (err) {
    yield put(handleShowMessage('AUTH.ERROR_LOGGING_IN', SNACKBAR_SEVERITY_VARIANTS.ERROR));
  } finally {
    yield put(handleLoginInProgress(false));
  }
}

function* requestSignup({ data, history }) {
  try {
    yield put(handleSignupInProgress(true));

    yield call(AuthApi.requestSignup, data);

    yield put(handleShowMessage('AUTH.SUCCESSFULLY_CREATED_ACCOUNT', SNACKBAR_SEVERITY_VARIANTS.SUCCESS));
    history.push('/login');
  } catch (err) {
    yield put(handleShowMessage('AUTH.ERROR_CREATING_ACCOUNT', SNACKBAR_SEVERITY_VARIANTS.ERROR));
  } finally {
    yield put(handleSignupInProgress(false));
  }
}

export default function* saga() {
  yield takeLatest(AUTH_ACTIONS.HANDLE_LOGIN, requestLogin);
  yield takeLatest(AUTH_ACTIONS.HANDLE_SIGNUP, requestSignup);
}
