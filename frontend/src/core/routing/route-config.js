import React from 'react';

import SignUp from '../../features/auth/components/signup';
import Login from '../../features/auth/components/login';
import NotFound from './not-found';

const routes = [
  {
    path: '/signup',
    component: SignUp,
    exact: true,
    requireAuth: false,
  },
  {
    path: '/login',
    component: Login,
    exact: true,
    requireAuth: false,
  },
  {
    path: '/',
    component: <div>default page</div>,
    requireAuth: true,
    routes: [
      {
        path: '/',
        component: <div>default page</div>,
        exact: true,
        requireAuth: true,
      },
      {
        path: '/neki-drugi-page',
        component: <div>hehe</div>,
        exact: true,
        requireAuth: true,
      },
      { component: NotFound },
    ],
  },
];

export default routes;
