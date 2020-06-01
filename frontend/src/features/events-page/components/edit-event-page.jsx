import 'date-fns';
import React, { Fragment, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useParams } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { makeStyles } from '@material-ui/core/styles';
import { useFormik } from 'formik';
import { useHistory } from 'react-router-dom';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Grid from '@material-ui/core/Grid';
import Container from '@material-ui/core/Container';
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';
import InputLabel from '@material-ui/core/InputLabel';
import DateFnsUtils from '@date-io/date-fns';
import { KeyboardDatePicker, MuiPickersUtilsProvider } from '@material-ui/pickers';
import styled from 'styled-components';
import * as Yup from 'yup';

import { handleGetCategories, handleGetLocations, handleUpdateEvent, setSelectedDate, handleGetEvent } from '../actions/events-page-actions';

import Button from '../../shared-components/button';
import ApplicationHeader from '../../shared-components/header';

const Header = styled.h1`
  text-align: center;
`;

const useStyles = makeStyles(theme => ({
  paper: {
    marginTop: theme.spacing(8),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  form: {
    width: '100%',
    marginTop: theme.spacing(3),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
  formControl: {
    margin: theme.spacing(1),
    minWidth: 120,
  },
  selectEmpty: {
    marginTop: theme.spacing(2),
  },
  spinner: {
    marginTop: '40px',
    display: 'flex',
    justifyContent: 'center',
  },
  spinnerIcon: {
    position: 'absolute',
    right: '100px',
    top: '100px',
    padding: '5px',
    height: '40px',
    width: '40px',
  },
}));

const RedButton = styled(Button)`
  background-color: red;
  margin-top: 10px;
  margin-bottom: 30px;
`;

function NewEvent() {
  const { t } = useTranslation();
  const classes = useStyles();
  const dispatch = useDispatch();
  const history = useHistory();
  const params = useParams();
  const idEvent = params.idEvent;

  useEffect(() => {
    dispatch(handleGetCategories());
  }, [dispatch]);

  useEffect(() => {
    dispatch(handleGetLocations());
  }, [dispatch]);

  useEffect(() => {
    dispatch(handleGetEvent(idEvent));
  }, [dispatch, idEvent]);

  const validationSchema = Yup.object().shape({
    title: Yup.string().required(t('EVENTS.TITLE_IS_REQUIRED')),
    address: Yup.string().required(t('EVENTS.ADDRESS_IS_REQUIRED')),
    category: Yup.string().required(t('EVENTS.CATEGORY_IS_REQUIRED')),
    location: Yup.string().required(t('EVENTS.LOCATION_IS_REQUIRED')),
  });

  const selectedDate = useSelector(state => state.events.selectedDate);

  const handleDateChange = date => {
    dispatch(setSelectedDate(date));
  };

  const handleClose = () => {
    history.push('/my-events');
  };

  const categories = useSelector(state => state.events.categories);
  const locations = useSelector(state => state.events.locations);
  const event = useSelector(state => state.events.eventInfo);

  const { values, errors, touched, handleChange, handleBlur, handleSubmit, resetForm } = useFormik({
    enableReinitialize: true,
    initialValues: {
      title: event ? event.title : '',
      address: event ? event.address : '',
      date: event ? event.date : '',
      description: event ? event.description : '',
      category: event && event.category ? event.category.id : '',
      location: event && event.location ? event.location.id : '',
    },
    validationSchema,
    onSubmit: values => {
      values.date = selectedDate;
      dispatch(handleUpdateEvent(values, idEvent));
      resetForm({});
    },
  });

  const fields = [
    { value: values.title, label: 'EVENTS.TITLE', name: 'title', error: touched.title && errors.title },
    { value: values.address, label: 'EVENTS.ADDRESS', name: 'address', error: touched.address && errors.address },
    { value: values.date, label: 'EVENTS.DATE', name: 'date', date: true },
    { value: values.description, label: 'EVENTS.DESCRIPTION', name: 'description', textArea: true, error: touched.description && errors.description },
    { value: values.category, label: 'EVENTS.CATEGORY', name: 'category', categories: true, error: touched.category && errors.category },
    { value: values.location, label: 'EVENTS.LOCATION', name: 'location', locations: true, error: touched.location && errors.location },
  ];

  function MenuItems(items, isCategories) {
    if (isCategories)
      return items.map(item => (
        <MenuItem value={item.id} key={item.id}>
          {item.name}
        </MenuItem>
      ));
    return items.map(item => (
      <MenuItem value={item.id} key={item.id}>
        {item.city}, {item.country}
      </MenuItem>
    ));
  }

  function FormField(field) {
    if (field.date) {
      return (
        <Grid key={field.name} item xs={12}>
          <MuiPickersUtilsProvider utils={DateFnsUtils}>
            <KeyboardDatePicker
              disableToolbar
              variant="inline"
              inputVariant="outlined"
              format="MM/dd/yyyy"
              margin="normal"
              id="date-picker-inline"
              label={t(field.label)}
              value={selectedDate}
              onChange={handleDateChange}
              KeyboardButtonProps={{
                'aria-label': 'change date',
              }}
            />
          </MuiPickersUtilsProvider>
        </Grid>
      );
    } else if (field.categories) {
      return (
        <Grid key={field.name} item xs={12}>
          <InputLabel htmlFor={field.name}>{t('EVENTS.CATEGORY')}</InputLabel>
          <Select
            disabled
            value={event && event.category ? event.category.id : ''}
            id={field.name}
            onChange={handleChange}
            onBlur={handleBlur}
            label={t(field.label)}
            error={Boolean(field.error)}
            variant="outlined"
            name={field.name}
            fullWidth
          >
            {MenuItems(categories, true)}
          </Select>
        </Grid>
      );
    } else if (field.locations) {
      return (
        <Grid key={field.name} item xs={12}>
          <InputLabel htmlFor={field.name}>{t('EVENTS.LOCATION')}</InputLabel>
          <Select
            disabled
            value={event && event.location ? event.location.id : ''}
            id={field.name}
            onChange={handleChange}
            onBlur={handleBlur}
            label={t(field.label)}
            error={Boolean(field.error)}
            variant="outlined"
            name={field.name}
            fullWidth
          >
            {MenuItems(locations, false)}
          </Select>
        </Grid>
      );
    } else {
      return (
        <Grid key={field.name} item xs={12}>
          <TextField
            value={field.value}
            onChange={handleChange}
            onBlur={handleBlur}
            label={t(field.label)}
            error={Boolean(field.error)}
            helperText={field.error}
            variant="outlined"
            name={field.name}
            fullWidth
            multiline={field.textArea || false}
          />
        </Grid>
      );
    }
  }

  return (
    <Fragment>
      <ApplicationHeader />
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <div className={classes.paper}>
          <Header>{t('EVENTS.UPDATE_EVENT_TITILE')}</Header>
          <form className={classes.form} onSubmit={handleSubmit} noValidate>
            <Grid container spacing={2}>
              {fields.map(field => FormField(field))}
              <Button type="submit" fullWidth variant="contained" color="primary" className={classes.submit} xs={6}>
                {t('EVENTS.UPDATE_EVENT')}
              </Button>
              <RedButton fullWidth type="button" variant="contained" color="primary" onClick={() => handleClose()} xs={6}>
                {t('EVENTS.CANCEL')}
              </RedButton>
            </Grid>
          </form>
        </div>
      </Container>
    </Fragment>
  );
}

export default NewEvent;
