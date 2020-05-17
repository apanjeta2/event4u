## Running locally

- You must have node (`>= 10` version) as well as npm installed (`>= 6` version)
- In order to run frontend locally you must define these env variables in an .env file:

```
REACT_APP_BACKEND=http://localhost:4200/aggregator
REACT_APP_RABBIT_URL=localhost
```

- After that you need to run `npm install` or `npm ci`.
- Finally to start the app hit `npm start`
