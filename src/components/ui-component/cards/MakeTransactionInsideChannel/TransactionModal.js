
import React, {useState} from 'react';
import { makeStyles } from '@material-ui/core';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import { useForm, Controller } from 'react-hook-form';
import axios from 'axios';

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

const TransactionModal = () => {
  const classes = useStyles();
  const { handleSubmit, control } = useForm();
  const [transactionData, setTransactionData] = useState()

  

  const onSubmit = data => {
    console.log(data);
    // axios.post(' http://localhost:8085/mediatorApi/approve', data)
    axios.post(' http://35.246.148.192:8085/mediatorApi/approve', data)
    .then((res) => {
      setTransactionData(res.data.key)
    })
  };

  console.log(transactionData)

  return (
    <form className={classes.root} onSubmit={handleSubmit(onSubmit)}>
     <Controller
        name="addressFromPk"
        control={control}
        defaultValue=""
        render={({ field: { onChange, value }, fieldState: { error } }) => (
          <TextField
            label="Private key Sender"
            variant="filled"
            value={value}
            onChange={onChange}
            error={!!error}
            helperText={error ? error.message : null}
            type="password"
          />
        )}
        rules={{ required: 'Sender Address Private Key required' }}
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
        rules={{ required: 'Address To required' }}
      />
      <Controller
        name="valueTrx"
        control={control}
        defaultValue=""
        render={({ field: { onChange, value }, fieldState: { error } }) => (
          <TextField
            label="Amount"
            variant="filled"
            value={value}
            onChange={onChange}
            error={!!error}
            helperText={error ? error.message : null}
          />
        )}
        rules={{ required: 'Amount Token required' }}
      />
      <Controller
        name="channelKey"
        control={control}
        defaultValue=""
        render={({ field: { onChange, value }, fieldState: { error } }) => (
          <TextField
            label="Channel Key"
            variant="filled"
            value={value}
            onChange={onChange}
            error={!!error}
            helperText={error ? error.message : null}
          />
        )}
        rules={{ required: 'Channel Key required' }}
      />
      
      <div>
        {/* <Button variant="contained" onClick={handleClose}>
          Cancel
        </Button> */}
        <Button type="submit" variant="contained" color="primary">
          Make Transaction
        </Button>
      </div>
      <h4>
        Your Transaction Key Data on redis/ipfs should be here 
        <br />
        <h2>{transactionData}</h2>
      </h4>
    </form>
  );
};

export default TransactionModal