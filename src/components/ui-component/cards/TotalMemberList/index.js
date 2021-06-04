import React, {useEffect, useState} from 'react';
import {Avatar, Card, CardContent, Grid, makeStyles, Typography} from '@material-ui/core';

import ListIcon from '@material-ui/icons/List';
import AddIcon from '@material-ui/icons/Add';

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
        fontSize: '1.6rem',
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

function truncatedong(str, num) {
    if(str.length <= num){
      return str;  
    }
    return str.slice(0, num) + '...'
  }


const TotalMemberList = () => {
    const classes = useStyles();
    const [friendList, setFriendList] = useState([])

    //const url = "http://localhost:8085/mediatorApi/init"
    const url = "http://35.246.148.192:8085/mediatorApi/init"
    

    useEffect(() => {
        loadFriend()
    }, [])

    const loadFriend = async () => {
        await fetch(url)
        .then(response => response.json())
        .then(datalist => setFriendList(datalist.listEthAccount))
      } 
    
    const addressFriend = friendList.map(data => truncatedong(data, 10))

    const dataFriends = [
        addressFriend[0],
        addressFriend[1],
        addressFriend[2]
    ]

    return (
        <Card className={classes.card}>
            <CardContent className={classes.content}>
                <Grid container direction="column">
                    <Grid item>
                        <Grid container justifyContent="space-between">
                            <Typography className={classes.subHeading}>Members</Typography>
                            
                        </Grid>
                        <Avatar className={classes.avatarCricle}>
                            <AddIcon fontSize="inherit" />    
                        </Avatar>
                    </Grid>
                    <Grid item>
                        { dataFriends.map(friend => (
                            <Grid container alignItems="flex-start">
                                <Grid item>
                                    <Avatar variant="rounded" className={classes.avatar}>
                                        <ListIcon fontSize="inherit" />
                                    </Avatar>
                                </Grid>
                                <Grid item>
                                    <Typography className={classes.cardHeading}></Typography>
                                </Grid>
                                <Grid item>
                                    <Typography className={classes.cardHeading}>{friend}</Typography>
                                </Grid>
                            </Grid>
                        ))}
                    </Grid>
                    {/* <Grid item>
                        <Grid container alignItems="center">
                            
                            <Grid item>
                                <Avatar className={classes.avatarCricle}>
                                    <ArrowDownwardIcon fontSize="inherit" className={classes.circleIcon} />
                                </Avatar>
                            </Grid>
                        </Grid>
                    </Grid> */}
                   
                </Grid>
            </CardContent>
        </Card>
    );
};

export default TotalMemberList;
