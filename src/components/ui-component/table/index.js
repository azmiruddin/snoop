import React, {Fragment} from 'react'
import {Avatar, Card, CardContent, Grid, makeStyles, Menu, MenuItem, Typography} from '@material-ui/core';

import GroupWorkIcon from '@material-ui/icons/GroupWork';

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
  memberList: {
    fontSize: '1.7rem',
    fontWeight: 500,
    marginRight: '8px',
    marginTop: '18px',
    marginBottom: '5px',
    backgroundColor: theme.palette.primary.dark,
    borderRadius: '5px',
    padding: '1px 25px'
  },
  cardHeading: {
      // fontSize: '2.125rem',
      fontSize: '1.7rem',
      fontWeight: 500,
      marginRight: '8px',
      marginTop: '20px',
      marginBottom: '5px'
  },
  cardSpace:{
    fontSize: '1.7rem',
    fontWeight: 500,
    marginRight: '28px',
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

const Members = [
  'Members 1',
  'Members 2',
  'Members 3',
  'Members 4',
]

const TableChannel = () => {
  const classes = useStyles();

  return (
    <Fragment>
      <Card className={classes.card}>
          <CardContent className={classes.content}>
              <Grid container direction="column">
                  <Grid item>
                      <Grid container justifyContent="space-between">
                          <Typography className={classes.subHeading}>Channel Members</Typography>
                      </Grid>    
                  </Grid>
                  {
                    Members.map(address => (
                      <Grid item >
                        <Grid container alignItems="flex-start" >
                            <Grid item>
                              <Avatar variant="rounded" className={classes.avatar}>
                                  <GroupWorkIcon fontSize="inherit" />
                              </Avatar>
                            </Grid>
                            <Grid item>
                                <Typography className={classes.cardSpace}></Typography>
                            </Grid>
                            <Grid item>
                                <Typography className={classes.memberList}>{address}</Typography>
                            </Grid>
                        </Grid>
                    </Grid> 
                    ))
                  }          
              </Grid>
          </CardContent>
        </Card>
    </Fragment>
  )
}

export default TableChannel
