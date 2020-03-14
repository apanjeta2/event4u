import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router } from 'react-router-dom';

import App from './core/App';
// import store from './core/store';

const MOUNT_NODE = document.getElementById('root');

ReactDOM.render(
  <Router>
    <App />
  </Router>,
  MOUNT_NODE
);
