import React, { Fragment, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useHistory } from 'react-router-dom';
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

import ApplicationHeader from '../../shared-components/header';

const Container = styled(MaterialContainer)`
  background-color: #fff;
`;

const useStyles = makeStyles(theme => ({
  card: {
    background: '#dbe5f0',
    minHeight: '50vh',
    minWidth: '60vh',
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
}));

function EventsPage() {
  const classes = useStyles();
  const { t } = useTranslation();
  const dispatch = useDispatch();
  const history = useHistory();

  const getDate = () => {
    const d = new Date(event.date);
    const days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
    return days[d.getDay()] + ', ' + d.getDate() + '.' + d.getMonth() + '.' + d.getFullYear() + '.';
  };

  const getLocation = () => {
    return event.location.city + ', ' + event.address;
  };

  const event = useSelector(state => state.events.event);

  return (
    <Fragment>
      <ApplicationHeader />
      <Container component="main">
        <Grid container spacing={0} direction="column" alignItems="center" justify="center" style={{ minHeight: '90vh' }}>
          <Card className={classes.card}>
            <CardContent>
              <Typography className={(classes.title, classes.icon_label)} color="textSecondary" gutterBottom>
                <EventIcon />
                {getDate()}
              </Typography>
              <Typography className={(classes.title, classes.icon_label)} color="textSecondary" gutterBottom>
                <AccessTimeIcon></AccessTimeIcon>
              </Typography>
              <Typography className={(classes.title, classes.icon_label)} color="textSecondary" gutterBottom>
                <LocationOnIcon />
                {getLocation()}
              </Typography>
              <Typography variant="h5" component="h2">
                {event.title}
              </Typography>
              <Typography component="p">{event.description}</Typography>
            </CardContent>
            <CardActions disableSpacing>
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
            </CardActions>
          </Card>
        </Grid>
      </Container>
    </Fragment>
  );
}

export default EventsPage;
