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
import IconButton from '@material-ui/core/IconButton';
import DeleteOutlineIcon from '@material-ui/icons/DeleteOutline';
import EditOutlinedIcon from '@material-ui/icons/EditOutlined';

import { handleGetEventsByCreator, handleDeleteEvent } from '../actions/events-page-actions';

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
});

function MyEventsPage() {
  const classes = useStyles();
  const { t } = useTranslation();
  const dispatch = useDispatch();
  const history = useHistory();

  const events = useSelector(state => state.events.myEvents);

  const getDate = date => {
    const d = new Date(date);
    const days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
    return days[d.getDay()] + ', ' + d.getDate() + '.' + (d.getMonth() + 1) + '.' + d.getFullYear() + '.';
  };

  useEffect(() => {
    dispatch(handleGetEventsByCreator());
  }, [dispatch]);

  const editClicked = event => {
    history.push(`/edit-event/${event.id}`);
  };

  const deleteClicked = event => {
    dispatch(handleDeleteEvent(event.id));
  };

  const newEventClicked = () => {
    history.push(`/new-event`);
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
            <Grid item container>
              <Typography color="textSecondary" className={classes.icon_label}>
                <IconButton aria-label="edit" className={classes.icon} onClick={() => editClicked(event)}>
                  <EditOutlinedIcon />
                </IconButton>
                {t('EVENTS.EVENTS_EDIT_BUTTON')}
              </Typography>
              <Typography color="textSecondary" className={classes.icon_label}>
                <IconButton aria-label="delete" className={classes.icon} onClick={() => deleteClicked(event)}>
                  {' '}
                  <DeleteOutlineIcon />
                </IconButton>
                {t('EVENTS.EVENTS_DELETE_BUTTON')}
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
        <h1>{t('EVENTS.MY-EVENTS')}</h1>
        <Button variant="outlined" size="small" color="primary" onClick={() => newEventClicked()}>
          {t('EVENTS.CREATE-EVENT')}
        </Button>
        <h2>{t('EVENTS.MY-EVENTS-ACTIONS')}</h2>
        {events.length !== 0 ? (
          <Grid container spacing={3}>
            {eventsItems}
          </Grid>
        ) : (
          <Typography component="p">{t('EVENTS.MY-EVENTS-NON-CREATED')}</Typography>
        )}
      </Container>
    </Fragment>
  );
}

export default MyEventsPage;
