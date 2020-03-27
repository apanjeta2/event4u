import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useTranslation } from 'react-i18next';
import { makeStyles } from '@material-ui/core/styles';
import { useFormik } from 'formik';
import { useHistory } from 'react-router-dom';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import MaterialContainer from '@material-ui/core/Container';
import styled from 'styled-components';
import * as Yup from 'yup';

import { handleSignup } from '../actions/auth-actions';

import Button from '../../shared-components/button';

import signUpLogo from '../../../config/images/sign-up-white.png';

const Container = styled(MaterialContainer)`
  background-color: #fff;
`;

const Image = styled.img`
  width: 100%;
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
}));

export default function SignUp() {
  const { t } = useTranslation();
  const classes = useStyles();
  const dispatch = useDispatch();
  const history = useHistory();

  const signupInProgress = useSelector(state => state.auth.signupInProgress);

  const validationSchema = Yup.object().shape({
    name: Yup.string().required(t('AUTH.FIRST_NAME_IS_REQUIRED')),
    surname: Yup.string().required(t('AUTH.LAST_NAME_IS_REQUIRED')),
    username: Yup.string().required(t('AUTH.USERNAME_IS_REQUIRED')),
    password: Yup.string().required(t('AUTH.PASSWORD_IS_REQUIRED')),
  });

  const { values, errors, touched, handleChange, handleBlur, handleSubmit } = useFormik({
    initialValues: {
      name: '',
      surname: '',
      username: '',
      password: '',
    },
    validationSchema,
    onSubmit: values => {
      dispatch(handleSignup(values, history));
    },
  });

  const fields = [
    { value: values.name, label: 'AUTH.FIRST_NAME', error: touched.name && errors.name, name: 'name', grid: true },
    { value: values.surname, label: 'AUTH.LAST_NAME', error: touched.surname && errors.surname, name: 'surname', grid: true },
    { value: values.username, label: 'AUTH.USERNAME', error: touched.username && errors.username, name: 'username' },
    { value: values.password, label: 'AUTH.PASSWORD', error: touched.password && errors.password, name: 'password' },
  ];

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <div className={classes.paper}>
        <Image src={signUpLogo} alt="logo" />
        <form className={classes.form} onSubmit={handleSubmit} noValidate>
          <Grid container spacing={2}>
            {fields.map(field => (
              <Grid key={field.name} item xs={12} sm={field.grid ? 6 : null}>
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
                />
              </Grid>
            ))}
          </Grid>
          <Button type="submit" fullWidth variant="contained" color="primary" loadingInProgress={signupInProgress} className={classes.submit}>
            {t('AUTH.SIGN_UP')}
          </Button>
          <Grid container justify="flex-end">
            <Grid item>
              <Link href="/login" variant="body2">
                {t('AUTH.ALREADY_HAVE_ACCOUNT')}
              </Link>
            </Grid>
          </Grid>
        </form>
      </div>
    </Container>
  );
}
