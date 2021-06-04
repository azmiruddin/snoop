import React, {useState, useEffect} from 'react';
import {Avatar, Card, CardContent, Grid, makeStyles, Menu, MenuItem, Typography} from '@material-ui/core';

import ListIcon from '@material-ui/icons/List';
import AddIcon from '@material-ui/icons/Add';

import Modal from "react-modal";
import ChannelModal from './channelModal'
import ListModalOpen from './listModal'

const useStyles = makeStyles((theme) => ({
    card: {
        backgroundColor: theme.palette.primary.main,
        color: '#fff',
        overflow: 'hidden',
        position: 'relative',
        '&:after': {
            content: '""',
            position: 'absolute',
            width: '210px',
            height: '210px',
            // backgroundColor: theme.palette.primary.dark,
            borderRadius: '50%',
            top: '-85px',
            right: '-95px',
            [theme.breakpoints.down('xs')]: {
                top: '-105px',
                right: '-140px'
            }
        },
        '&:before': {
            content: '""',
            position: 'absolute',
            width: '210px',
            height: '210px',
            // backgroundColor: theme.palette.primary.dark,
            borderRadius: '50%',
            top: '-125px',
            right: '-15px',
            opacity: 0.7,
            [theme.breakpoints.down('xs')]: {
                top: '-155px',
                right: '-70px'
            }
        }
    },
    content: {
        padding: '20px !important'
    },
    avatar: {
        ...theme.typography.commonAvatar,
        ...theme.typography.largeAvatar,
        backgroundColor: theme.palette.primary.dark,
        color: '#fff',
        marginTop: '15px'
    },
    avatarRight: {
        ...theme.typography.commonAvatar,
        ...theme.typography.mediumAvatar,
        backgroundColor: theme.palette.primary.main,
        color: theme.palette.primary[200],
        zIndex: 1
    },
    cardHeading: {
        // fontSize: '2.125rem',
        fontSize: '1.7rem',
        fontWeight: 500,
        marginRight: '8px',
        marginTop: '20px',
        marginBottom: '5px'
    },
    subHeading: {
        fontSize: '1.5rem',
        fontWeight: 500,
        color: theme.palette.primary[200]
    },
    avatarCricle: {
        ...theme.typography.smallAvatar,
        cursor: 'pointer',
        backgroundColor: theme.palette.primary[200],
        color: theme.palette.primary.dark
    },
    circleIcon: {
        transform: 'rotate3d(1, 1, 1, 45deg)'
    },
    menuItem: {
        marginRight: '14px',
        fontSize: '1.25rem'
    }
}));




const TotalChannel = () => {
    const classes = useStyles();
    const [channelAddress, setChannelAddress] = useState('')
    
    const url = 'http://localhost:8085/mediatorApi/checkAddress'
    //const url = 'http://35.246.148.192:8085/mediatorApi/checkAddress'
    
    useEffect(() => {
      loadChannelAddress()
    }, [])

    const loadChannelAddress = async () => {
        await fetch(url)
        .then(response => response.json())
        .then(datalist => setChannelAddress(datalist.tokenAddress))
    }

    //modal section
    const [isOpen, setIsOpen] = useState(false)
    const [openList, setOpenList] = useState(false)

    const [channelKeyIndex, setChannelKeyIndex] = useState('')

    const toggleModal = () => {
        setIsOpen(!isOpen);
    }

    const listModal = () => {
        setOpenList(!openList)
    }

    return (
        <React.Fragment>
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
                <ChannelModal />
            </Modal>

            {/* List Channel Modal */}
            <Modal
                isOpen={openList}
                onRequestClose={listModal}
                contentLabel="Dialog"
                style={{
                    content:{
                        width:'90%',
                        margin: 'auto',
                        borderRadius: '15px',
                        height: '70%'
                    }
                }}
                appElement={document.getElementById('app')}
            >
                 <ListModalOpen />
            </Modal>
        
            <Card className={classes.card}>
                <CardContent className={classes.content}>
                    <Grid container direction="column">
                        <Grid item>
                            <Grid container justifyContent="space-between">
                                <Typography className={classes.subHeading}>Channels</Typography>
                            </Grid>
                            <Avatar className={classes.avatarCricle} onClick={toggleModal}>
                                <AddIcon fontSize="inherit" />    
                            </Avatar>
                            
                            {/* <Modals>
                                <Avatar className={classes.avatarCricle}>
                                    <AddIcon fontSize="inherit" />    
                                </Avatar>
                            </Modals> */}
                        
                        </Grid>
                        <Grid item>
                            <Grid container alignItems="flex-start">
                                <Grid item>                                    
                                    <Avatar variant="rounded" className={classes.avatar} onClick={listModal}>
                                        <ListIcon fontSize="inherit" />
                                    </Avatar>                                    
                                </Grid>
                                <Grid item>
                                    <Typography className={classes.cardHeading}></Typography>
                                </Grid>
                                <Grid item>
                                    <Typography className={classes.cardHeading}>Channel Name</Typography>
                                </Grid>
                            </Grid>
                        </Grid>                   
                    </Grid>
                </CardContent>
            </Card>
        </React.Fragment>
    );
};

export default TotalChannel;
