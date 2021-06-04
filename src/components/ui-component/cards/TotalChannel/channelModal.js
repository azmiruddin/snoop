
import React, {Fragment, useState, useEffect} from 'react';
import { makeStyles } from '@material-ui/core';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import { useForm, Controller } from 'react-hook-form';

//data handling
// import {useDispatch} from "react-redux";
// import { openChannel } from '../../../../redux/api/actions/actions';

import axios from 'axios'

const useStyles = makeStyles(theme => ({
  root: {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    padding: theme.spacing(2),

    '& .MuiTextField-root': {
      margin: theme.spacing(1),
      width: '300px',
    },
    '& .MuiButtonBase-root': {
      margin: theme.spacing(2),
    },
  },
}));

const ChannelModal = () => {
  const classes = useStyles();
  const { handleSubmit, control } = useForm();
  const [channelKey, setChannelKey] = useState('')
  const [dataTrx, setDataTrx] = useState({})
 
  const onSubmit = data => {
    console.log(data)
    // axios.post('http://localhost:8085/mediatorApi/channel', data)
    axios.post('http://35.246.148.192:8085/mediatorApi/channel', data)
    .then((res) => {
      // localStorage.setItem("channel key", res.channelKey)
      setChannelKey(res.data.channelKey)
      setDataTrx(res.data)
      // console.log(res.data)
    })
  }

  console.log(channelKey)

  return (
    <Fragment>  
      <form className={classes.root} onSubmit={handleSubmit(onSubmit)}>
          <h2>{channelKey}</h2>
          <Controller
            name="addressFromPk"
            control={control}
            defaultValue=""
            render={({ field: { onChange, value }, fieldState: { error } }) => (
              <TextField
                label="Root Address Pkey"
                variant="filled"
                value={value}
                onChange={onChange}
                error={!!error}
                helperText={error ? error.message : null}
                type="password"
              />
            )}
            rules={{ required: 'Root Address Private Key required' }}
          />
          <Controller
            name="addressTo"
            control={control}
            defaultValue=""
            render={({ field: { onChange, value }, fieldState: { error } }) => (
              <TextField
                label="Address To"
                variant="filled"
                value={value}
                onChange={onChange}
                error={!!error}
                helperText={error ? error.message : null}
              />
            )}
            rules={{ required: 'Second Address required' }}
          />
          <Controller
            name="addressAudit"
            control={control}
            defaultValue=""
            render={({ field: { onChange, value }, fieldState: { error } }) => (
              <TextField
                label="Audit Address"
                variant="filled"
                value={value}
                onChange={onChange}
                error={!!error}
                helperText={error ? error.message : null}
              />
            )}
            rules={{ required: 'Auditor/Third Address required' }}
          />
          <Controller
            name="depositMinimum"
            control={control}
            defaultValue=""
            render={({ field: { onChange, value }, fieldState: { error } }) => (
              <TextField
                label="Minimum Amount Deposit"
                variant="filled"
                value={value}
                onChange={onChange}
                error={!!error}
                helperText={error ? error.message : null}
              />
            )}
            rules={{ required: 'Minimum deposit is required' }}
          />
          
          
          <div>
        
            <Button type="submit" variant="contained" color="primary">
              Open Channel
            </Button>
          </div>
          <h3>
            This is your channel Key, please save for the next transaction
            <br />
            <br />
            {channelKey}
            {/* <div>
              <h4>Open Channel Data</h4>
              {dataTrx}
            </div> */}
          </h3>
          
      </form>
      
   
    </Fragment>
   );
};

export default ChannelModal