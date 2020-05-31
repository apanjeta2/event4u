import React, { Fragment } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useParams } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import styled from 'styled-components';
import MaterialContainer from '@material-ui/core/Container';
import { makeStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import AccessTimeIcon from '@material-ui/icons/AccessTime';
import LocationOnIcon from '@material-ui/icons/LocationOn';
import EventIcon from '@material-ui/icons/Event';
import CheckCircleOutlineIcon from '@material-ui/icons/CheckCircleOutline';
import StarBorderIcon from '@material-ui/icons/StarBorder';
import IconButton from '@material-ui/core/IconButton';
import StarIcon from '@material-ui/icons/Star';
import CheckCircleIcon from '@material-ui/icons/CheckCircle';

import { handleInterestedClicked, handleGoingToClicked } from '../actions/events-page-actions';

import ApplicationHeader from '../../shared-components/header';

const Container = styled(MaterialContainer)`
  background-color: #fff;
`;

const useStyles = makeStyles(theme => ({
  card: {
    background: '#f2f5f9',
    minHeight: '50vh',
    minWidth: '60vh',
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'space-between',
  },
  title: {
    fontSize: 14,
  },
  icon: {
    padding: '2px',
  },
  icon_label: {
    paddingRight: '10px',
  },
  icon_label_bold: {
    paddingRight: '10px',
    textDecoration: 'underline',
    fontWeight: 'bold',
  },
  grid: {
    minHeight: '90vh',
  },
}));

function EventsInfoPage() {
  const classes = useStyles();
  const { t } = useTranslation();
  const dispatch = useDispatch();
  const userLoggedIn = useSelector(state => state.auth.userLoggedIn);
  const params = useParams();
  const eventId = params.idEvent;

  const eventInfo = useSelector(state => state.events.eventInfo);

  const getDate = date => {
    const d = new Date(date);
    const days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
    return days[d.getDay()] + ', ' + d.getDate() + '.' + d.getMonth() + '.' + d.getFullYear() + '.';
  };

  const getLocation = () => {
    if (userLoggedIn === true) {
      return eventInfo.event.location.city + ', ' + eventInfo.event.address;
    }
    return eventInfo.location.city + ', ' + eventInfo.address;
  };

  const getTime = () => {
    return t('EVENTS.TIME_NOT_AVAILABLE');
  };

  const interestedClicked = event => {
    dispatch(handleInterestedClicked(event));
  };

  const goingToClicked = event => {
    dispatch(handleGoingToClicked(event));
  };

  function InterestedIcon(event) {
    if (event.marked && !event.going) {
      return <StarIcon />;
    }
    return <StarBorderIcon />;
  }

  function GoingIcon(event) {
    if (event.marked && event.going) {
      return <CheckCircleIcon />;
    }
    return <CheckCircleOutlineIcon />;
  }

  function ActionsForLoggedUsers(event) {
    if (userLoggedIn) {
      return (
        <CardActions disableSpacing>
          <Typography color="textSecondary" className={event.marked && !event.going ? classes.icon_label_bold : classes.icon_label}>
            <IconButton aria-label="mark as interested" onClick={() => interestedClicked(event)} className={classes.icon}>
              {InterestedIcon(event)}
            </IconButton>
            {t('EVENTS.EVENTS_INTERESTED_BUTTON')}
          </Typography>
          <Typography color="textSecondary" className={event.marked && event.going ? classes.icon_label_bold : classes.icon_label}>
            <IconButton aria-label="mark as going" onClick={() => goingToClicked(event)} className={classes.icon}>
              {GoingIcon(event)}
            </IconButton>
            {t('EVENTS.EVENTS_GOING_BUTTON')}
          </Typography>
        </CardActions>
      );
    }
  }

  return (
    <Fragment>
      <ApplicationHeader />
      <Container component="main">
        <Grid container spacing={0} direction="column" alignItems="center" justify="center" key={userLoggedIn ? eventInfo.event.id : eventInfo.id} className={classes.grid}>
          <Card className={classes.card}>
            <CardContent>
              <Typography className={(classes.title, classes.icon_label)} color="textSecondary" gutterBottom>
                <EventIcon />
                {getDate(userLoggedIn ? eventInfo.event.date : eventInfo.date)}
              </Typography>
              <Typography className={(classes.title, classes.icon_label)} color="textSecondary" gutterBottom>
                <AccessTimeIcon />
                {getTime()}
              </Typography>
              <Typography className={(classes.title, classes.icon_label)} color="textSecondary" gutterBottom>
                <LocationOnIcon />
                {getLocation()}
              </Typography>
              <Typography variant="h5" component="h2">
                {userLoggedIn ? eventInfo.event.title : eventInfo.title}
              </Typography>
              <Typography component="p">{userLoggedIn ? eventInfo.event.description : eventInfo.description}</Typography>
            </CardContent>
            {ActionsForLoggedUsers(eventInfo)}
          </Card>
        </Grid>
      </Container>
    </Fragment>
  );
}

export default EventsInfoPage;
