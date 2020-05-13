import React, { Fragment, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useHistory } from 'react-router-dom';
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
import IconButton from '@material-ui/core/IconButton';

import { handleGetEventsByCategory, handleEventClicked } from '../actions/events-page-actions';

import ApplicationHeader from '../../shared-components/header';

const Container = styled(MaterialContainer)`
  background-color: #fff;
`;

const useStyles = makeStyles({
  card: {
    background: '#dbe5f0',
  },
  icon: {
    padding: '2px',
  },
  icon_label: {
    paddingRight: '10px',
  },
});

function EventsPage() {
  const classes = useStyles();
  const { t } = useTranslation();
  const dispatch = useDispatch();
  const history = useHistory();

  const events = useSelector(state => state.events.events);
  const categoryId = useSelector(state => state.events.category.id);

  useEffect(() => {
    dispatch(handleGetEventsByCategory(categoryId));
  }, [dispatch, categoryId]);

  /*useEffect(()=> {
    navigator.geolocation.getCurrentPosition(function(position) {
      console.log("Latitude is :", position.coords.latitude);
      console.log("Longitude is :", position.coords.longitude);
    });
  })*/

  const eventClicked = event => {
    dispatch(handleEventClicked(event));
    history.push('/event-info');
  };

  const getDate = date => {
    const d = new Date(date);
    const days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
    return days[d.getDay()] + ', ' + d.getDate() + '.' + d.getMonth() + '.' + d.getFullYear() + '.';
  };

  const eventsItems = events.map(event => (
    <Grid item xs={12} sm={6} lg={3} key={event.id}>
      <Card className={classes.card}>
        <CardContent>
          <Typography component="h5" variant="h5">
            {event.title}
          </Typography>
          <Typography color="textSecondary">{getDate(event.date)}</Typography>
          <Typography component="p">{event.description}</Typography>
        </CardContent>
        <CardActions disableSpacing>
          <Grid container>
            <Grid item>
              <Button size="small" color="primary" onClick={() => eventClicked(event)}>
                {t('EVENTS.EVENTS_GET_INFO_BUTTON')}
              </Button>
            </Grid>
            <Grid item container>
              <Typography color="textSecondary" className={classes.icon_label}>
                <IconButton aria-label="mark as interested" className={classes.icon}>
                  <StarBorderIcon />
                </IconButton>
                {t('EVENTS.EVENTS_INTERESTED_BUTTON')}
              </Typography>
              <Typography color="textSecondary" className={classes.icon_label}>
                <IconButton aria-label="mark as going" className={classes.icon}>
                  <CheckCircleOutlineIcon />
                </IconButton>
                {t('EVENTS.EVENTS_GOING_BUTTON')}
              </Typography>
            </Grid>
          </Grid>
        </CardActions>
      </Card>
    </Grid>
  ));

  return (
    <Fragment>
      <ApplicationHeader />
      <Container component="main">
        <h1>{t('EVENTS.EVENTS_TITLE')}</h1>
        <Grid container spacing={3}>
          {eventsItems}
        </Grid>
      </Container>
    </Fragment>
  );
}

export default EventsPage;
