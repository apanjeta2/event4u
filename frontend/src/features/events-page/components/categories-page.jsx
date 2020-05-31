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

import { handleGetCategories, handleCategoryClicked } from '../actions/events-page-actions';

import ApplicationHeader from '../../shared-components/header';

const Container = styled(MaterialContainer)`
  background-color: #fff;
`;

const useStyles = makeStyles({
  card: {
    background: '#f2f5f9',
  },
});

function CategoriesPage() {
  const classes = useStyles();
  const { t } = useTranslation();
  const dispatch = useDispatch();
  const history = useHistory();

  const categories = useSelector(state => state.events.categories);

  useEffect(() => {
    dispatch(handleGetCategories());
  }, [dispatch]);

  const categoryClicked = category => {
    dispatch(handleCategoryClicked(category));
    history.push(`/events/${category.id}`);
  };

  const categoryItems = categories.map(category => (
    <Grid item xs={12} sm={6} lg={3} key={category.id}>
      <Card className={classes.card}>
        <CardContent>
          <Typography component="h5" variant="h5">
            {category.name}
          </Typography>
        </CardContent>
        <CardActions>
          <Button size="small" color="primary" onClick={() => categoryClicked(category)}>
            {t('EVENTS.CATEGORIES_GET_EVENTS_BUTTON')}
          </Button>
        </CardActions>
      </Card>
    </Grid>
  ));

  return (
    <Fragment>
      <ApplicationHeader />
      <Container component="main">
        <h1>{t('EVENTS.CATEGORIES_TITLE')}</h1>
        <h2>{t('EVENTS.CATEGORIES_SUBTITLE')}</h2>
        <Grid container spacing={3}>
          {categoryItems}
        </Grid>
      </Container>
    </Fragment>
  );
}

export default CategoriesPage;
