import { combineReducers } from 'redux';

import { auth } from '../features/auth/reducers/auth-reducers';
import { snackbar } from '../features/snackbar/reducers/snackbar-reducers';

const appReducer = combineReducers({
  auth,
  snackbar,
});

export const rootReducer = (state, action) => {
  return appReducer(state, action);
};
