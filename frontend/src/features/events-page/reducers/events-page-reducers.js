import { EVENTS_ACTIONS } from '../constants/events-page-constants';

const getEventsInitialState = () => {
  return {
    getCategoriesInProgres: false,
    categories: [],
    category: null,
    getEventsInProgres: false,
    events: [],
    event: null,
  };
};

export const events = (state = getEventsInitialState(), action) => {
  switch (action.type) {
    case EVENTS_ACTIONS.HANDLE_GET_CATEGORIES_SUCCESS:
      return { ...state, categories: action.data };
    case EVENTS_ACTIONS.HANDLE_GET_CATEGORIES_IN_PROGRESS:
      return { ...state, getCategoriesInProgres: action.status };
    case EVENTS_ACTIONS.HANDLE_CATEGORY_CHOSEN:
      return { ...state, category: action.data };
    case EVENTS_ACTIONS.HANDLE_GET_EVENTS_BY_CATEGORY_SUCCESS:
      return { ...state, events: action.data };
    case EVENTS_ACTIONS.HANDLE_GET_EVENTS_BY_CATEGORY_IN_PROGRESS:
      return { ...state, getCategoriesInProgres: action.status };
    case EVENTS_ACTIONS.HANDLE_EVENT_CHOSEN:
      return { ...state, event: action.data };
    default:
      return state;
  }
};
