import React, {useState, useEffect} from 'react';
import {Avatar, Card, CardContent, List, ListItem, ListItemAvatar, ListItemText, makeStyles, Typography} from '@material-ui/core';
    
import TransferWithinAStationIcon from '@material-ui/icons/TransferWithinAStation';
import AccountBalanceIcon from '@material-ui/icons/AccountBalance';

const useStyles = makeStyles((theme) => ({
    card: {
        backgroundColor: theme.palette.primary.dark,
        color: theme.palette.primary.light,
        overflow: 'hidden',
        position: 'relative',
        '&:after': {
            content: '""',
            position: 'absolute',
            width: '210px',
            height: '210px',
            background: 'linear-gradient(210.04deg, #90CAF9 -50.94%, rgba(144, 202, 249, 0) 83.49%)',
            borderRadius: '50%',
            top: '-30px',
            right: '-180px'
        },
        '&:before': {
            content: '""',
            position: 'absolute',
            width: '210px',
            height: '210px',
            background: 'linear-gradient(140.9deg, #90CAF9 -14.02%, rgba(144, 202, 249, 0) 77.58%)',
            borderRadius: '50%',
            top: '-160px',
            right: '-130px'
        }
    },
    content: {
        padding: '10px !important'
    },
    avatar: {
        ...theme.typography.commonAvatar,
        ...theme.typography.largeAvatar,
        backgroundColor: theme.palette.primary[800],
        color: '#fff'
    },
    primary: {
        color: '#fff'
    },
    secondary: {
        color: theme.palette.primary.light,
        marginTop: '5px'
    },
    padding: {
        paddingTop: 0,
        paddingBottom: 0
    }
}));

const RootAccount = () => {
    const classes = useStyles();
    const [coinbase, setCoinbase] = useState()
    const [balance, setBalance] = useState()
    //const url = "http://localhost:8085/mediatorApi/init"
    const url = "http://35.246.148.192:8085/mediatorApi/init"
    

    useEffect(() => {
      loadProfile();
      loadProfileBalance();
    }, [])

    const loadProfile = async () => {
        await fetch(url)
        .then(response => response.json())
        .then(datalist => setCoinbase(datalist.ethCoinbaseAddress))
    }

    const loadProfileBalance = async () => {
        await fetch(url)
        .then(response => response.json())
        .then(datalist => setBalance(datalist.balanceEthCoinbaseAddress))
    }

    //Convert
    const bal = balance / 1000000000000000000

    return (
        <Card className={classes.card}>
            <CardContent className={classes.content}>
                <List className={classes.padding}>
                    <ListItem alignItems="center" disableGutters className={classes.padding}>
                        <ListItemAvatar>
                            <Avatar variant="rounded" className={classes.avatar}>
                                <AccountBalanceIcon fontSize="inherit" />
                            </Avatar>
                        </ListItemAvatar>
                        <ListItemText
                            className={classes.padding}
                            primary={
                                <Typography variant="h4" className={classes.primary}>
                                    Root Account ({bal} ETH)
                                </Typography>
                            }
                            secondary={
                                <Typography variant="subtitle2" className={classes.secondary}>
                                    {coinbase}
                                </Typography>
                            }
                        />
                    </ListItem>
                </List>
            </CardContent>
        </Card>
    );
};

export default RootAccount;
