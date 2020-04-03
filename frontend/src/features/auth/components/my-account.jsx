import React, { Fragment } from 'react';

import ApplicationHeader from '../../shared-components/header';

function MyAccountPage() {
  return (
    <Fragment>
      <ApplicationHeader isMyAccount />
      <h1 style={{ margin: 'auto' }}>My account page</h1>
    </Fragment>
  );
}

export default MyAccountPage;
