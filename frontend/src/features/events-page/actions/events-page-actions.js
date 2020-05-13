import { EVENTS_ACTIONS } from '../constants/events-page-constants';

export const handleGetCategories = () => ({
  type: EVENTS_ACTIONS.HANDLE_GET_CATEGORIES,
});

export const handleGetCategoriesInProgress = status => ({
  type: EVENTS_ACTIONS.HANDLE_GET_CATEGORIES_IN_PROGRESS,
  status,
});

export const handleGetCategoriesSuccess = data => ({
  type: EVENTS_ACTIONS.HANDLE_GET_CATEGORIES_SUCCESS,
  data,
});

export const handleGetEventsByCategory = data => ({
  type: EVENTS_ACTIONS.HANDLE_GET_EVENTS_BY_CATEGORY,
  data,
});

export const handleGetEventsByCategoryInProgress = status => ({
  type: EVENTS_ACTIONS.HANDLE_GET_EVENTS_BY_CATEGORY_IN_PROGRESS,
  status,
});

export const handleGetEventsByCategorySuccess = data => ({
  type: EVENTS_ACTIONS.HANDLE_GET_EVENTS_BY_CATEGORY_SUCCESS,
  data,
});

export const handleCategoryClicked = data => ({
  type: EVENTS_ACTIONS.HANDLE_CATEGORY_CHOSEN,
  data,
});

export const handleEventClicked = data => ({
  type: EVENTS_ACTIONS.HANDLE_EVENT_CHOSEN,
  data,
});
