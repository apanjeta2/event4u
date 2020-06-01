import React, { Fragment, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useHistory, useParams } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import styled from 'styled-components';
import MaterialContainer from '@material-ui/core/Container';
import { makeStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import CardActions from '@material-ui/core/CardActions';
import Grid from '@material-ui/core/Grid';
import CheckCircleOutlineIcon from '@material-ui/icons/CheckCircleOutline';
import StarBorderIcon from '@material-ui/icons/StarBorder';
import StarIcon from '@material-ui/icons/Star';
import IconButton from '@material-ui/core/IconButton';
import CheckCircleIcon from '@material-ui/icons/CheckCircle';

import { handleGetEventsByCategory, handleEventClicked, handleInterestedClicked, handleGoingToClicked, handleGetEventsByCategoryLoggedUser, handleGetCategory } from '../actions/events-page-actions';

import ApplicationHeader from '../../shared-components/header';

const Container = styled(MaterialContainer)`
  background-color: #fff;
`;

const useStyles = makeStyles({
  card: {
    background: '#f2f5f9',
    height: '300px',
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'space-between',
  },
  icon: {
    padding: '2px',
  },
  icon_label: {
    paddingRight: '3px',
  },
  icon_label_bold: {
    paddingRight: '3px',
    textDecoration: 'underline',
    fontWeight: 'bold',
  },
});

function EventsPage() {
  const classes = useStyles();
  const { t } = useTranslation();
  const dispatch = useDispatch();
  const history = useHistory();

  const params = useParams();
  const categoryId = params.idCategory;
  const category = useSelector(state => (state.events.category ? state.events.category.name : null));
  const userLoggedIn = useSelector(state => state.auth.userLoggedIn);
  const events = useSelector(state => state.events.events);

  useEffect(() => {
    if (!category) dispatch(handleGetCategory(categoryId));
  }, [dispatch, categoryId]);

  useEffect(() => {
    if (userLoggedIn) dispatch(handleGetEventsByCategoryLoggedUser(categoryId));
    else dispatch(handleGetEventsByCategory(categoryId));
  }, [dispatch, categoryId]);

  const eventClicked = eventInfo => {
    dispatch(handleEventClicked(eventInfo));
    history.push(`/event-info/${userLoggedIn ? eventInfo.event.id : eventInfo.id}`);
  };

  const interestedClicked = event => {
    dispatch(handleInterestedClicked(event));
  };

  const goingToClicked = event => {
    dispatch(handleGoingToClicked(event));
  };

  const getDate = date => {
    const d = new Date(date);
    const days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
    return days[d.getDay()] + ', ' + d.getDate() + '.' + d.getMonth() + '.' + d.getFullYear() + '.';
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
        <Grid item container>
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
        </Grid>
      );
    }
  }

  function Description(eventInfo) {
    let description = '';
    if (userLoggedIn) {
      description = eventInfo.event.description;
    } else {
      description = eventInfo.description;
    }
    if (description && description.length > 300) {
      description = description.substr(0, 300) + '...';
    }
    return <Typography component="p">{description}</Typography>;
  }

  const eventsItems = events.map(eventInfo => (
    <Grid item xs={12} sm={6} lg={3} key={userLoggedIn ? eventInfo.event.id : eventInfo.id}>
      <Card className={classes.card}>
        <CardContent>
          <Typography component="h5" variant="h5">
            {userLoggedIn ? eventInfo.event.title : eventInfo.title}
          </Typography>
          <Typography color="textSecondary">{getDate(userLoggedIn ? eventInfo.event.date : eventInfo.date)}</Typography>
          {Description(eventInfo)}
        </CardContent>
        <CardActions disableSpacing>
          <Grid container>
            <Grid item>
              <Button size="small" color="primary" onClick={() => eventClicked(eventInfo)}>
                {t('EVENTS.EVENTS_GET_INFO_BUTTON')}
              </Button>
            </Grid>
            {ActionsForLoggedUsers(eventInfo)}
          </Grid>
        </CardActions>
      </Card>
    </Grid>
  ));

  return (
    <Fragment>
      <ApplicationHeader />
      <Container component="main">
        <h1>{category}</h1>
        <h2>{t('EVENTS.EVENTS_TITLE')}</h2>
        {Array.isArray(events) && events.length ? (
          <Grid container spacing={3}>
            {eventsItems}
          </Grid>
        ) : (
          <Typography component="p">{t('EVENTS.EVENTS_NOT_FOUND')}</Typography>
        )}
      </Container>
    </Fragment>
  );
}

export default EventsPage;
