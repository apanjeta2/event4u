import { combineReducers } from 'redux';

import { auth } from '../features/auth/reducers/auth-reducers';
import { snackbar } from '../features/snackbar/reducers/snackbar-reducers';
import { events } from '../features/events-page/reducers/events-page-reducers';

const appReducer = combineReducers({
  auth,
  snackbar,
  events,
});

export const rootReducer = (state, action) => {
  return appReducer(state, action);
};
