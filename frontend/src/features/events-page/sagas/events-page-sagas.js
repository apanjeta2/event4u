import { call, put, takeLatest } from 'redux-saga/effects';

import { handleGetCategoriesSuccess, handleGetCategoriesInProgress, handleGetEventsByCategoryInProgress, handleGetEventsByCategorySuccess } from '../actions/events-page-actions';

import { handleShowMessage } from '../../snackbar/actions/snackbar-actions';

import EventsApi from '../../../api/events-api';

import { SNACKBAR_SEVERITY_VARIANTS } from '../../snackbar/constants/snackbar-constants';
import { EVENTS_ACTIONS } from '../constants/events-page-constants';

function* getCategories() {
  try {
    yield put(handleGetCategoriesInProgress(true));
    const res = yield call(EventsApi.requestGetCategories);
    yield put(handleGetCategoriesSuccess(res.data));
  } catch (err) {
    yield put(handleShowMessage('EVENTS.ERROR_GET_CATEGORIES', SNACKBAR_SEVERITY_VARIANTS.ERROR));
  } finally {
    yield put(handleGetCategoriesInProgress(false));
  }
}

function* getEventsByCategory({ data }) {
  try {
    yield put(handleGetEventsByCategoryInProgress(true));
    const res = yield call(EventsApi.requestGetEventsByCategory, data);
    yield put(handleGetEventsByCategorySuccess(res.data));
  } catch (err) {
    yield put(handleShowMessage('EVENTS.ERROR_GET_EVENTS', SNACKBAR_SEVERITY_VARIANTS.ERROR));
  } finally {
    yield put(handleGetEventsByCategoryInProgress(false));
  }
}

export default function* saga() {
  yield takeLatest(EVENTS_ACTIONS.HANDLE_GET_CATEGORIES, getCategories);
  yield takeLatest(EVENTS_ACTIONS.HANDLE_GET_EVENTS_BY_CATEGORY, getEventsByCategory);
}
