import { all } from 'redux-saga/effects';

import authSagas from '../features/auth/sagas/auth-sagas';

export default function* rootSaga() {
  yield all([authSagas()]);
}
