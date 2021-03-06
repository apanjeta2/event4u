import React, { Fragment, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useParams, useHistory } from 'react-router-dom';
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
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
import Link from '@material-ui/core/Link';

import { handleInterestedClicked, handleGoingToClicked, handleGetEvent, handleGetEventLoggedUser } from '../actions/events-page-actions';

import ApplicationHeader from '../../shared-components/header';

import { handleGetUser } from '../../auth/actions/auth-actions';

import { handleGetCreator } from '../actions/events-page-actions';

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
  creator: {
    paddingRight: '10px',
    align: 'center',
    position: 'absolute',
    display: 'block',
    paddingTop: '70px',
  },
}));

function EventsInfoPage() {
  const classes = useStyles();
  const { t } = useTranslation();
  const dispatch = useDispatch();
  const userLoggedIn = useSelector(state => state.auth.userLoggedIn);
  const eventInfo = useSelector(state => state.events.eventInfo);
  const creatorId = useSelector(state => (eventInfo != null ? (userLoggedIn ? eventInfo.event.creator.id : eventInfo.creator.id) : null));
  const params = useParams();
  const eventId = params.idEvent;

  const token = useSelector(state => state.auth.token);
  const creatorOfEvent = useSelector(state => state.events.creatorOfEvent);
  const history = useHistory();

  useEffect(() => {
    if (userLoggedIn) dispatch(handleGetEventLoggedUser(eventId));
    else dispatch(handleGetEvent(eventId));
  }, [dispatch, eventId, userLoggedIn]);

  useEffect(() => {
    if (userLoggedIn) {
      dispatch(handleGetUser(token));
      dispatch(handleGetCreator(creatorId));
    }
  }, [dispatch, token, creatorId, userLoggedIn]);

  const getDate = date => {
    const d = new Date(date);
    const days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
    return days[d.getDay()] + ', ' + d.getDate() + '.' + d.getMonth() + '.' + d.getFullYear() + '.';
  };

  const getLocation = () => {
    if (!eventInfo) return;
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

  const openProfile = id => {
    history.push(`/profile/` + id);
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

  function Creator(eventInfo) {
    if (userLoggedIn) {
      return (
        <Typography color="textSecondary" className={classes.creator}>
          <IconButton aria-label="creator" className={classes.icon}>
            <AccountCircleIcon></AccountCircleIcon>
          </IconButton>
          {t('EVENTS.CREATOR')}
          <Link href="#" onClick={() => openProfile(creatorId)}>
            {creatorOfEvent ? creatorOfEvent.username : creatorId}
          </Link>
        </Typography>
      );
    }
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
        <Grid container spacing={0} direction="column" alignItems="center" justify="center" key={eventInfo ? (userLoggedIn ? eventInfo.event.id : eventInfo.id) : null} className={classes.grid}>
          <Card className={classes.card}>
            <CardContent>
              <Typography className={(classes.title, classes.icon_label)} color="textSecondary" gutterBottom>
                <EventIcon />
                {getDate(eventInfo ? (userLoggedIn ? eventInfo.event.date : eventInfo.date) : null)}
              </Typography>
              <Typography className={(classes.title, classes.icon_label)} color="textSecondary" gutterBottom>
                <AccessTimeIcon />
                {getTime()}
              </Typography>
              <Typography className={(classes.title, classes.icon_label)} color="textSecondary" gutterBottom>
                <LocationOnIcon />
                {getLocation()}
              </Typography>
              {eventInfo ? Creator(eventInfo) : ''}
              <Typography variant="h5" component="h2">
                {eventInfo ? (userLoggedIn ? eventInfo.event.title : eventInfo.title) : null}
              </Typography>
              <Typography component="p">{eventInfo ? (userLoggedIn ? eventInfo.event.description : eventInfo.description) : null}</Typography>
            </CardContent>
            {eventInfo ? ActionsForLoggedUsers(eventInfo) : ''}
          </Card>
        </Grid>
      </Container>
    </Fragment>
  );
}

export default EventsInfoPage;
