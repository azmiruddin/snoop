
import React, {Fragment, useState, useEffect} from 'react';
import { makeStyles } from '@material-ui/core';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import { useForm, Controller } from 'react-hook-form';

//data handling
import {useDispatch} from "react-redux";
import { openChannel } from '../../../../redux/api/actions/actions';

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

  //handling data
  // const initData = {
  //   addressFromPk: '',
  //   addressTo: '',
  //   addressAudit: '',
  //   depositMinimum: null
  // }

  // const [firstMembers, setFirstMembers] = useState(initData)
  // const [submitted, setSubmitted] = useState(false)

  // const dispatch = useDispatch()

  // const [data, setData] = useState({
  //   addressFromPk: '',
  //   addressTo: '',
  //   addressAudit: '',
  // })

  // const [sendData, setSendData] = useState([])

  // useEffect(() => {
  //   postData()
  //   return ()=>{}
  //   }, [data])

  // const postData = async () => {
  //   const requestOptions = {
  //     method: 'POST',
  //     headers: { 'Content-Type': 'application/json' },
  //     body: JSON.stringify(data)
  //   }
  //   const response = await fetch('http://localhost:8085/mediatorApi/channel', requestOptions )
  //   const dataresp = await response.json()
  //   setSendData({...sendData, ...dataresp})
  // }

  // const onSubmit = e => {
  //   console.log(e);
  //   // e.preventDefault()
  //   const datax = {
  //       data
  //   }
  //   setData({...data,...datax})
  // };

  const onSubmit = data => {
    console.log(data)
    axios.post(' http://localhost:8085/mediatorApi/channel', data)
    .then((res) => {
      // localStorage.setItem("channel key", res.channelKey)
      setChannelKey(res.channelKey)
    })
  }

  console.log(channelKey)

  // const handleInputChange = event => {
  //   const {name, value} = event.target;
  //   setFirstMembers({...firstMembers,[name]: value})
  // }

  // const pushMember = () => {
  //   const {addressFromPk, addressTo, addressAudit, depositMinimum} = firstMembers;

  //   dispatch(openChannel(
  //     addressFromPk, 
  //     addressTo, 
  //     addressAudit, 
  //     depositMinimum
  //     ))
  //   .then(data => {
  //     setFirstMembers({
  //       addressFromPk: data.addressFromPk, 
  //       addressTo: data.addressTo, 
  //       addressAudit: data.addressAudit, 
  //       depositMinimum: data.depositMinimum
  //     });
  //     setSubmitted(true);

  //     console.log(data);
  //   })
  //   .catch(e => {
  //     console.log(e)
  //   })
  // }

  // const createMembers = () => {
  //   setFirstMembers(initData);
  //   setSubmitted(false)
  // }

  return (
    <Fragment>  
      {/* { submitted ? (
        <div>
          <h4>You submitted successfully!</h4>
          <button className="btn btn-success" onClick={createMembers}>
            Add
          </button>
        </div>
      ) : ( */}
      <form className={classes.root} onSubmit={handleSubmit(onSubmit)}>
        {/* <form className={classes.root} onSubmit={handleInputChange)}>
        </form> */}
      {/*    <div className="form-group">
              <label htmlFor="addressFromPk">Address From PK</label>
             <input
             type="text"
            className="form-control"
      //       id="addressFromPk"
      //       required
      //       value={firstMembers.addressFromPk}
      //       onChange={handleInputChange}
      //       name="Sender Address PKey"
      //     />
      //   </div> */}
        {/* <div className="form-group">
          <label htmlFor="addressTo">Address To</label>
          <input
            type="text"
            className="form-control"
            id="addressTo"
            required
            value={firstMembers.addressTo}
            onChange={handleInputChange}
            name="Receiver Address"
          />
        </div> */}
        {/* <div className="form-group">
          <label htmlFor="addressAudit">Auditor Address</label>
          <input
            type="text"
            className="form-control"
            id="addressAudit"
            required
            value={firstMembers.addressAudit}
            onChange={handleInputChange}
            name="Auditor Address"
          />
        </div> */}
        {/* <div className="form-group">
          <label htmlFor="depositMinimum">Minimum Deposit</label>
          <input
            type="text"
            className="form-control"
            id="depositMinimum"
            required
            value={firstMembers.depositMinimum}
            onChange={handleInputChange}
            name="Minimum Amount"
          />
        </div> */}
        {/* <Button onClick={pushMember} type="submit" variant="contained" color="primary">
          Open Channel
        </Button> */}
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
            name="addressAudit"
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
            {/* <Button variant="contained" onClick={handleClose}>
              Cancel
            </Button>  */}
            <Button type="submit" variant="contained" color="primary">
              Open Channel
            </Button>
          </div>
          
      </form>
        )
      {/* } */}
    </Fragment>
   );
};

export default ChannelModal