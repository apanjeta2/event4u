import React from 'react';
import { useTranslation } from 'react-i18next';
import { makeStyles } from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import MaterialContainer from '@material-ui/core/Container';
import styled from 'styled-components';

import signUpLogo from '../../../config/images/sign-up-white.png';

import Button from '../../shared-components/button';

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

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <div className={classes.paper}>
        <Image src={signUpLogo} alt="logo" />
        <form className={classes.form} noValidate>
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <TextField variant="outlined" fullWidth id="email" label={t('AUTH.USERNAME')} name="username" autoComplete="username" />
            </Grid>
            <Grid item xs={12}>
              <TextField variant="outlined" fullWidth name="password" label={t('AUTH.PASSWORD')} type="password" id="password" autoComplete="current-password" />
            </Grid>
          </Grid>
          <Button type="submit" fullWidth variant="contained" color="primary" className={classes.submit}>
            {t('AUTH.SIGN_IN')}
          </Button>
          <Grid container justify="flex-end">
            <Grid item>
              <Link href="/signup" variant="body2">
                {t('AUTH.DONT_HAVE_ACCOUNT')}
              </Link>
            </Grid>
          </Grid>
        </form>
      </div>
    </Container>
  );
}
