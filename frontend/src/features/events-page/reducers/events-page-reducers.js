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
    getCreatorInProgres: false,
    creatorOfEvent: null,
    myEvents: [],
    locations: [],
    getLocationsInProgres: false,
    addNewEventInProgres: false,
    deleteEventInProgres: false,
    selectedDate: new Date(),
    expended: false,
    updateEventInProgres: false,
    getSubscribers: false,
    mySubscribers: [],
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
    case EVENTS_ACTIONS.HANDLE_GET_CREATOR_IN_PROGRESS:
      return { ...state, getCreatorInProgres: action.status };
    case EVENTS_ACTIONS.HANDLE_GET_CREATOR_SUCCESS:
      return { ...state, creatorOfEvent: action.data };
    case EVENTS_ACTIONS.HANDLE_GET_EVENTS_BY_CREATOR_IN_PROGRESS:
      return { ...state, getEventsInProgres: action.status };
    case EVENTS_ACTIONS.HANDLE_GET_EVENTS_BY_CREATOR_SUCCESS:
      return { ...state, myEvents: action.data };
    case EVENTS_ACTIONS.HANDLE_GET_LOCATIONS_SUCCESS:
      return { ...state, locations: action.data };
    case EVENTS_ACTIONS.HANDLE_GET_LOCATIONS_IN_PROGRESS:
      return { ...state, getLocationsInProgres: action.status };
    case EVENTS_ACTIONS.HANDLE_ADD_NEW_EVENT_SUCCESS:
      return { ...state, eventInfo: action.data };
    case EVENTS_ACTIONS.HANDLE_ADD_NEW_EVENT_IN_PROGRESS:
      return { ...state, addNewEventInProgres: action.status };
    case EVENTS_ACTIONS.HANDLE_UPDATE_EVENT_SUCCESS:
      return { ...state, eventInfo: action.data };
    case EVENTS_ACTIONS.HANDLE_UPDATE_EVENT_IN_PROGRESS:
      return { ...state, updateEventInProgres: action.status };
    case EVENTS_ACTIONS.HANDLE_SET_DATE:
      return { ...state, selectedDate: action.data };
    case EVENTS_ACTIONS.HANDLE_DELETE_EVENT_IN_PROGRESS:
      return { ...state, deleteEventInProgres: action.status };
    case EVENTS_ACTIONS.HANDLE_DELETE_EVENT_SUCCESS:
      return { ...state, events: action.data };
    case EVENTS_ACTIONS.HANDLE_EXPAND_CLICKED:
      return { ...state, expanded: action.data };
    case EVENTS_ACTIONS.HANDLE_GET_SUBSCRIBERS_IN_PROGRESS:
      return { ...state, getSubscribers: action.status };
    case EVENTS_ACTIONS.HANDLE_GET_SUBSCRIBERS_SUCCESS:
      return { ...state, mySubscribers: action.data };
    case EVENTS_ACTIONS.HANDLE_SUBSCRIBE_TO_IN_PROGRESS:
      return { ...state, getSubscribers: action.status };
    default:
      return state;
  }
};
