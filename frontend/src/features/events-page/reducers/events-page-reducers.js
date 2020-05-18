import { EVENTS_ACTIONS } from '../constants/events-page-constants';

const getEventsInitialState = () => {
  return {
    getCategoriesInProgres: false,
    categories: [],
    category: null,
    getEventsInProgres: false,
    events: [],
    eventInfo: null,
    going: false,
    marked: false,
    markAsInterestedInProgress: false,
    getCategoryInProgres: false,
    getEventInProgres: false,
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
      return { ...state, eventInfo: action.data };
    case EVENTS_ACTIONS.HANDLE_EVENT_MARKED_AS_INTERESTED_IN_PROGRESS:
      return { ...state, markAsInterestedInProgress: action.status };
    case EVENTS_ACTIONS.HANDLE_EVENT_MARKED_AS_INTERESTED_SUCCESS:
      return {
        ...state,
        events: state.events.map(eventInfo => (eventInfo.event.id === action.data.event.id ? { ...eventInfo, going: action.data.going, marked: action.data.marked } : eventInfo)),
        eventInfo: action.data,
      };
    case EVENTS_ACTIONS.HANDLE_EVENT_MARKED_AS_GOING_IN_PROGRESS:
      return { ...state, markAsInterestedInProgress: action.status };
    case EVENTS_ACTIONS.HANDLE_EVENT_MARKED_AS_GOING_SUCCESS:
      return {
        ...state,
        events: state.events.map(eventInfo => (eventInfo.event.id === action.data.event.id ? { ...eventInfo, going: action.data.going, marked: action.data.marked } : eventInfo)),
        eventInfo: action.data,
      };
    case EVENTS_ACTIONS.HANDLE_GET_CATEGORY_IN_PROGRESS:
      return { ...state, getCategoryInProgres: action.status };
    case EVENTS_ACTIONS.HANDLE_GET_CATEGORY_SUCCESS:
      return { ...state, category: action.data };
    case EVENTS_ACTIONS.HANDLE_GET_EVENT_IN_PROGRESS:
      return { ...state, getEventInProgres: action.status };
    case EVENTS_ACTIONS.HANDLE_GET_EVENT_SUCCESS:
      return { ...state, eventInfo: action.data };
    default:
      return state;
  }
};
