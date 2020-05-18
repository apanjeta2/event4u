import SignUp from '../../features/auth/components/signup';
import Login from '../../features/auth/components/login';
import NotFound from './not-found';
import CategoriesPage from '../../features/events-page/components/categories-page';
import EventsPage from '../../features/events-page/components/events-page';
import EventsInfoPage from '../../features/events-page/components/event-info-page';
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
    component: CategoriesPage,
    exact: true,
  },
  {
    path: '/events/:idCategory',
    component: EventsPage,
    exact: true,
  },
  {
    path: '/event-info/:idEvent',
    component: EventsInfoPage,
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
