# event4u

event4u is a web appliaction for managing/creating/attending events

# Services

- user-managment-service

In order to run `user-management-service` you must define these env variables:

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
FULL_BASE_URL=http://localhost:4000
```

- events     
- notifications
- frontend
