import { all } from 'redux-saga/effects';

import authSagas from '../features/auth/sagas/auth-sagas';
import eventsSagas from '../features/events-page/sagas/events-page-sagas';

export default function* rootSaga() {
  yield all([authSagas(), eventsSagas()]);
}
