import {
  OPEN_CHANNEL_STARTED,
  OPEN_CHANNEL_SUCCESS,
  OPEN_CHANNEL_FAIL,
} from './types';

import StateChannelDataService from '../StateChannel.services'

export const openChannel = (
  addressFromPk,
  addressTo,
  depositMinimum,
  addressAudit 
) => async(dispatch) => {
  try{
    const res = await StateChannelDataService.CreateChannel({
      addressFromPk,
      addressTo,
      depositMinimum,
      addressAudit 
    })

    dispatch({
      type: OPEN_CHANNEL_SUCCESS,
      payload: res.data
    })

    return Promise.resolve(res.data)
  }catch(err){
    return Promise.reject(err)
  } 
};