## Endpoints

- You can find the endpoints for this service [here](https://github.com/MasovicHaris/event4u/wiki/User-Management-service-endpoints).

## Running locally

### Setting up database

- You must setup your MYSQL database and remember the connection parameters

### Setting up firebase

- In order to use firebase service, you need to add a `json` file called `firebase.json` to the root folder of this service.
- To find your firebase config file go to the firebase web page and into your project settings you can generate a new private key configuration file in `.json` format.
- Also you need define the `STORAGE_BUCKET`, `FIREBASE_DATABASE_URL` and `FIREBASE_STORAGE_URL`, in the .env file, which you can find also on the same site.
- After that you have to go to the google storage site, and add a public user role to make your bucket public so anyone can see the uploaded images via links.

### In the end

- You must have node (`>= 10` version) as well as npm installed (`>= 6` version)
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
EUREKA_HOST_BASE_URL=localhost
BACKEND_HOST_BASE_URL=localhost
STORAGE_BUCKET=your_storage_bucket
FIREBASE_DATABASE_URL=your_firebase_url
FIREBASE_STORAGE_URL=https://storage.googleapis.com
```

- After that, in order to install the dependencies, you need to run `npm install` or `npm ci`.
- Finally to start the app hit `npm start`

## Running tests

- In order to run tests the initial db setup, if you haven't done it already, is required (`INITIAL_DB_SETUP`)
- Open a second terminal (all services must be active) and type `npm test`
- Note:
  - Tests for saving uploaded images were not added due to saving memory space on the firebase service
  - Due to the written endpoint flows (prerequisites, orm, handling responses), tests are written in a way that provides a 100% assurance regarding that endpoints are working or not
