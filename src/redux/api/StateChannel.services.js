import http from "../http";

import {
  openChannelSuccess,
  openChannelStarted,
  openChannelFailure
} from '../reducers/openChannel'




// 

const CreateChannel = data => {
  return http.post("/channel", data)
}

const transactionOnChannel = data => {
  // return http.post("/channel", data)
}

const StateChannelService = {
  CreateChannel
};

export default StateChannelService

// export const openChannel = ({ 
//   addressFromPk,
//   addressTo,
//   depositMinimum,
//   addressAudit 
// }) => {
//   return (dispatch, getState) => {
//     // const {dataStatus} = getState()
//     console.log('current data status: ', getState())

//     dispatch(openChannelStarted());

//     axios
//       .post(baseUrl+'/mediatorApi/channel', {
//         addressFromPk,
//         addressTo,
//         depositMinimum,
//         addressAudit
//       })
//       .then(res => {
//         setTimeout(() => {
//           dispatch(openChannelSuccess(res.data));
//         }, 2500);
//       })
//       .catch(err => {
//         dispatch(openChannelFailure(err.message));
//       });
//   };
// };

// const openChannelSuccess = dataStatus => ({
//   type: OPEN_CHANNEL_SUCCESS,
//   payload: {
//     ...dataStatus
//   }
// });

// const openChannelStarted = () => ({
//   type: OPEN_CHANNEL_STARTED
// });

// const openChannelFailure = error => ({
//   type: OPEN_CHANNEL_FAIL,
//   payload: {
//     error
//   }
// });

// export const Transaction = ({

// })