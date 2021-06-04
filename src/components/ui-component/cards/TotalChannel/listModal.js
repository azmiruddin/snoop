
import React, {useState, useEffect} from 'react';
import { makeStyles } from '@material-ui/core';
import TableChannel from '../../table';

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

const ListModalOpen = () => {
  const classes = useStyles();

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
  }

  return (
    <TableChannel />
  );
};

export default ListModalOpen