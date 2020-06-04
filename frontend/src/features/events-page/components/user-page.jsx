import React, { Fragment, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useParams } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import styled from 'styled-components';
import MaterialContainer from '@material-ui/core/Container';
import { makeStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';

import ApplicationHeader from '../../shared-components/header';

import { handleGetCreator, handleSubscribeTo, handleGetSubscribers } from '../actions/events-page-actions';

const Container = styled(MaterialContainer)`
  background-color: #fff;
`;

const useStyles = makeStyles(theme => ({
  card: {
    background: 'white',
    width: '50%',
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'space-between',
    align: 'right',
    paddingTop: '10px',
  },
  title: {
    fontSize: 20,
    fontWeight: 'bold',
    paddingBottom: '10px',
  },
  info: {
    fontSize: 14,
  },
  grid: {
    minHeight: '90vh',
  },
  imageOuterDiv: {
    width: '100%',
    marginTop: '30px',
    backgroundColor: '#fff',
  },
  imageInnerDiv: {
    padding: '10px',
    display: 'flex',
    justifyContent: 'center',
  },
  image: {
    borderRadius: '100%',
    height: '90%',
    width: '90%',
    maxHeight: '290px',
    maxWidth: '290px',
    minHeight: '150px',
    minWidth: '150px',
    objectFit: 'cover',
    border: '1px solid black',
  },
  buttonSub: {
    padding: '10px',
    display: 'flex',
    align: 'right',
    justifyContent: 'right',
  },
}));

function EventsInfoPage() {
  const classes = useStyles();
  const { t } = useTranslation();
  const dispatch = useDispatch();
  const userLoggedIn = useSelector(state => state.auth.userLoggedIn);
  const userLoggedInId = useSelector(state => state.auth.profile.id);
  const mySubscribers = useSelector(state => state.events.mySubscribers);
  const params = useParams();
  const userId = params.idUser;

  const creatorOfEvent = useSelector(state => state.events.creatorOfEvent);

  useEffect(() => {
    if (!creatorOfEvent) {
      dispatch(handleGetCreator(userId));
    }
    dispatch(handleGetSubscribers(creatorOfEvent.id));
  }, [dispatch, userId, creatorOfEvent]);

  function subscribeClicked() {
    dispatch(handleSubscribeTo(userId));
    dispatch(handleGetSubscribers(creatorOfEvent.id));
  }

  if (userLoggedIn) {
    return (
      <Fragment>
        <ApplicationHeader />
        <Container component="main">
          <Grid container spacing={0} direction="column" alignItems="center" justify="center">
            <div className={classes.imageOuterDiv}>
              <div className={classes.imageInnerDiv}>
                <img className={classes.image} src={creatorOfEvent.picture} alt="ProfileImage" />{' '}
              </div>
            </div>
            <Card className={classes.card}>
              <CardContent>
                <Typography className={classes.title}>{creatorOfEvent.name + ' ' + creatorOfEvent.surname}</Typography>
                <Typography className={classes.info} color="textSecondary" gutterBottom>
                  {t('AUTH.USERNAME') + ': '}
                  {creatorOfEvent.username}
                </Typography>
                <Typography className={classes.info} color="textSecondary" gutterBottom>
                  {t('AUTH.DESCRIPTION') + ': '}
                  {creatorOfEvent.description}
                </Typography>
              </CardContent>
            </Card>
            {!mySubscribers.includes(userLoggedInId) && userLoggedInId !== creatorOfEvent.id ? (
              <div className={classes.buttonSub}>
                <Button variant="contained" color="primary" onClick={() => subscribeClicked()}>
                  Subscribe
                </Button>
              </div>
            ) : (
              <div className={classes.buttonSub}>
                <Button variant="contained" color="primary" disabled>
                  Subscribe
                </Button>
              </div>
            )}
          </Grid>
        </Container>
      </Fragment>
    );
  }
}

export default EventsInfoPage;
