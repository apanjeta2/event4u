## Run locally
In order to run `user-management-service` locally you must define these env variables in an .env file:

```
DB_USERNAME=your_db_username
DB_PASSWORD=your_password
DB_HOST=your_db_host
DB_PORT=your_port
DATABASE=your_database_name
JWT_SECRET=your_secret
JWT_EXPIRE_TIME_TOKEN=1h
INITIAL_DB_SETUP=true // SET 'true' for first time running (initial migration), afterwards you can remove it.
PORT=4000
FULL_BASE_URL=http://localhost:4000 or your own url
FRONTEND_URL=http://localhost:3000 or your own url
```

After that, you need to run `npm install` or `npm ci`. Finally to start the app hit `npm start`

## Running tests

In order to run tests, open a second terminal (first one must be active) and type `npm test`
