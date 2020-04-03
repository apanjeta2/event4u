import SignUp from '../../features/auth/components/signup';
import Login from '../../features/auth/components/login';
import NotFound from './not-found';
import EventsPage from '../../features/events-page/components/events-page';
import MyAccountPage from '../../features/auth/components/my-account';

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
    component: EventsPage,
    exact: true,
  },
  {
    path: '/my-account',
    component: MyAccountPage,
    exact: true,
    requireAuth: true,
  },
  { component: NotFound },
];

export default routes;
