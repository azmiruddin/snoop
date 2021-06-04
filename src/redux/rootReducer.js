import {combineReducers} from 'redux';
import customizationReducer from './reducers/customizationReducer';
import snackbarReducer from './reducers/snackbarReducer';
import openChannelReducer from './reducers/openChannel';

const reducer = combineReducers({
    customization: customizationReducer,
    snackbar: snackbarReducer,
    openChannelReducer
});

export default reducer;
