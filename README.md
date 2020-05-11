## event4u

Event4u is a web application that allows users to find the perfect event for themselves. The app allows users to search for events in a variety of categories that might be of interest to them and that are near their location. Users can sign up and create a profile to get other options such as creating events, marking events with going to or interested parties, subscribing to other users and getting notifications, etc.

### Services

- [user-managment-service](https://github.com/MasovicHaris/event4u/tree/master/user-managment-service)
- [events-service](https://github.com/MasovicHaris/event4u/tree/master/events-service)
- [notification-service](https://github.com/MasovicHaris/event4u/tree/master/notification-service)
- [eureka-service](https://github.com/MasovicHaris/event4u/tree/master/eureka-service)
- [system-events-service](https://github.com/MasovicHaris/event4u/tree/master/system-events)
- [gateway-service](https://github.com/MasovicHaris/event4u/tree/master/gateway-service)
- [frontend](https://github.com/MasovicHaris/event4u/tree/master/frontend)

### Running with docker

- To run with docker, you must have docker installed and started
- You need to change the `.env` files accordingly to the services
- READ: If running on `windows` please change all `.sh` files in the repository and change the `EOL` format from `CRLF` to `LF`
- You need to setup these system variables: 
```
EUREKA_BASE_URL=localhost
```

- Finally run:

```
docker-compose up --build
```

### Credits

[Mašović Haris](https://github.com/MasovicHaris), [Šabović Dženana](https://github.com/dsabovic1), [Panjeta Ajla](https://github.com/apanjeta2)

_University of Sarajevo_

_Faculty of Electrical Engineering Sarajevo_

_Department of Computing and Informatics_
