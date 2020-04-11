## Endpoints

- You can find the endpoints for this service [here](https://github.com/MasovicHaris/event4u/wiki/User-Management-service---endpoints).

## Running locally

- You must have node (`>= 10` version) as well as npm installed (`>= 6` version)
- You must setup your MYSQL database and remember the connection parameters
- All other services (`eureka-service`, `events-service`, `notification-service`) must be started
- In order to run `user-management-service` locally you must define these env variables in an .env file:

```
DB_USERNAME=your_db_username
DB_PASSWORD=your_password
DB_HOST=your_db_host
DB_PORT=your_port
DATABASE=your_database_name
JWT_SECRET=your_secret
JWT_EXPIRE_TIME_TOKEN=1h
INITIAL_DB_SETUP=true // SET 'true' for first time running, afterwards you can remove it
PORT=your_port
FULL_BASE_URL=your_base_url
FRONTEND_URL=frontend_url
EUREKA_HOST_BASE_URL=localhost
BACKEND_HOST_BASE_URL=localhost
```

- After that, in order to install the dependencies, you need to run `npm install` or `npm ci`.
- Finally to start the app hit `npm start`

## Running tests

- In order to run tests the initial db setup, if you haven't done it already, it is required (see `INITIAL_DB_SETUP` env variable above!)
- Open a second terminal (all services must be active) and type `npm test`
- Note: Due to the written endpoint flows (prerequisites, orm, handling responses), tests are written in a way that provides a 100% assurance regarding that endpoints are working or not 
