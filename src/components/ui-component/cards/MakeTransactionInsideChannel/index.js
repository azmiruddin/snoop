import React, { Fragment, useState } from 'react';
import {Avatar, Card, CardContent, List, ListItem, ListItemAvatar, ListItemText, makeStyles, ModalRoot, Typography} from '@material-ui/core';

import TransferWithinAStationIcon from '@material-ui/icons/TransferWithinAStation';

import Modal from "react-modal";
import ChannelModal from './TransactionModal'
import StateChannelModal from '../../../StateChannel/ModalStateChannel/TransferModal';
import TransactionModal from './TransactionModal';


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
        padding: '16px !important'
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

const MakeTransactionInsideChannel = () => {
    const classes = useStyles();

    //modal section
    const [isOpen, setIsOpen] = useState(false)

    const toggleModal = () => {
        setIsOpen(!isOpen);
    }

    return (
        <Fragment>  
            <Modal 
                isOpen={isOpen}
                onRequestClose={toggleModal}
                contentLabel="Dialog"
                style={{
                    content:{
                        width:'40%',
                        margin: 'auto',
                        borderRadius: '15px',
                        height: '70%'
                    }
                }}
                appElement={document.getElementById('app')}
            >
                <TransactionModal />
                {/* <StateChannelModal /> */}
            </Modal>
            <Card className={classes.card}>
                <CardContent className={classes.content}>
                    <List className={classes.padding}>
                        <ListItem alignItems="center" disableGutters className={classes.padding}>
                            <ListItemAvatar>
                                <Avatar variant="rounded" className={classes.avatar} onClick={toggleModal}>
                                    <TransferWithinAStationIcon fontSize="inherit" />
                                </Avatar>
                            </ListItemAvatar>
                            <ListItemText
                                className={classes.padding}
                                primary={
                                    <Typography variant="h4" className={classes.primary}>
                                        Make a Transaction
                                    </Typography>
                                }
                                secondary={
                                    <Typography variant="subtitle2" className={classes.secondary}>
                                        Try to make a transaction by using token
                                    </Typography>
                                }
                            />
                        </ListItem>
                    </List>
                </CardContent>
            </Card>
        </Fragment>
        
    );
};

export default MakeTransactionInsideChannel;
