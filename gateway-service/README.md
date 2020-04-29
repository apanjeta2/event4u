## Endpoints

- You can find all the endpoints [here](https://github.com/MasovicHaris/event4u/wiki).

## Running locally

- You must have node (`>= 10` version) as well as npm installed (`>= 6` version)
- All other services must be started
- In order to run `gateway-service` locally you must define these env variables in an .env file:

```
PORT=your_port
FULL_BASE_URL=your_full_base_url
FRONTEND_URL=your_frontend_url
EUREKA_HOST_BASE_URL=localhost
BACKEND_HOST_BASE_URL=localhost
```

- After that, in order to install the dependencies, you need to run `npm install` or `npm ci`.
- Finally to start the app hit `npm start`
