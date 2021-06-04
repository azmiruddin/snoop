import {
  OPEN_CHANNEL_STARTED,
  OPEN_CHANNEL_SUCCESS,
  OPEN_CHANNEL_FAIL,
} from '../api/actions/types';

const initialState = [];

function openChannelReducer(state = initialState, action) {
  const {type, payload} = action;

  switch (type) {
    case OPEN_CHANNEL_SUCCESS:
      return [...state, payload];
    default:
      return state;
  }
}

export default openChannelReducer