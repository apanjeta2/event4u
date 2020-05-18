import { call, put, takeLatest } from 'redux-saga/effects';

import {
  handleGetCategoriesSuccess,
  handleGetCategoriesInProgress,
  handleGetEventsByCategoryInProgress,
  handleGetEventsByCategorySuccess,
  handleInterestedClickedInProgress,
  handleInterestedClickedSuccess,
  handleGoingToClickedInProgress,
  handleGoingToClickedSuccess,
  handleGetCategoryInProgress,
  handleGetCategorySuccess,
  handleGetEventInProgress,
  handleGetEventSuccess,
} from '../actions/events-page-actions';

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

function* getEventsByCategoryLoggedInUser({ data }) {
  try {
    yield put(handleGetEventsByCategoryInProgress(true));
    const res = yield call(EventsApi.requestGetEventsByCategoryLoggedUser, data);
    yield put(handleGetEventsByCategorySuccess(res.data));
  } catch (err) {
    yield put(handleShowMessage('EVENTS.ERROR_GET_EVENTS', SNACKBAR_SEVERITY_VARIANTS.ERROR));
  } finally {
    yield put(handleGetEventsByCategoryInProgress(false));
  }
}

function* markEventAsInterested({ data }) {
  try {
    yield put(handleInterestedClickedInProgress(true));
    if (data.marked && !data.going) {
      const res = yield call(EventsApi.requestRemoveMark, data.event.id);
      yield put(handleInterestedClickedSuccess(res.data));
    } else {
      const res = yield call(EventsApi.requestMarkEventAsInterested, data.event.id);
      yield put(handleInterestedClickedSuccess(res.data));
    }
  } catch (err) {
    yield put(handleShowMessage('EVENTS.ERROR_MARK_EVENT', SNACKBAR_SEVERITY_VARIANTS.ERROR));
  } finally {
    yield put(handleInterestedClickedInProgress(false));
  }
}

function* markEventAsGoing({ data }) {
  try {
    yield put(handleGoingToClickedInProgress(true));
    if (data.marked && data.going) {
      const res = yield call(EventsApi.requestRemoveMark, data.event.id);
      yield put(handleGoingToClickedSuccess(res.data));
    } else {
      const res = yield call(EventsApi.requestMarkEventAsGoingTo, data.event.id);
      yield put(handleGoingToClickedSuccess(res.data));
    }
  } catch (err) {
    yield put(handleShowMessage('EVENTS.ERROR_MARK_EVENT', SNACKBAR_SEVERITY_VARIANTS.ERROR));
  } finally {
    yield put(handleGoingToClickedInProgress(false));
  }
}

function* getCategoryById({ data }) {
  try {
    yield put(handleGetCategoryInProgress(true));
    const res = yield call(EventsApi.requestGetCategoryById(data));
    //TODO: fix - api call error :(
    yield put(handleGetCategorySuccess(res.data));
  } catch (err) {
    yield put(handleShowMessage('EVENTS.ERROR_GET_CATEGORIES', SNACKBAR_SEVERITY_VARIANTS.ERROR));
  } finally {
    yield put(handleGetCategoryInProgress(false));
  }
}

function* getEventById({ data }) {
  try {
    yield put(handleGetEventInProgress(true));
    const res = yield call(EventsApi.requestGetEventById(data));
    console.log(res);
    yield put(handleGetEventSuccess(res.data));
  } catch (err) {
    yield put(handleShowMessage('EVENTS.ERROR_GET_EVENTS', SNACKBAR_SEVERITY_VARIANTS.ERROR));
  } finally {
    yield put(handleGetEventInProgress(false));
  }
}

export default function* saga() {
  yield takeLatest(EVENTS_ACTIONS.HANDLE_GET_CATEGORIES, getCategories);
  yield takeLatest(EVENTS_ACTIONS.HANDLE_GET_EVENTS_BY_CATEGORY, getEventsByCategory);
  yield takeLatest(EVENTS_ACTIONS.HANDLE_GET_EVENTS_BY_CATEGORY_LOGGED_IN_USER, getEventsByCategoryLoggedInUser);
  yield takeLatest(EVENTS_ACTIONS.HANDLE_EVENT_MARKED_AS_INTERESTED, markEventAsInterested);
  yield takeLatest(EVENTS_ACTIONS.HANDLE_EVENT_MARKED_AS_GOING_TO, markEventAsGoing);
  yield takeLatest(EVENTS_ACTIONS.HANDLE_GET_CATEGORY, getCategoryById);
  yield takeLatest(EVENTS_ACTIONS.HANDLE_GET_EVENT, getEventById);
}
