
# Notification routes

### 1. **Get all notifications**

Vraća sve notifikacije svih usera.

- **URL**

  /notifications

- **Method:**

  `GET`

- **URL Params**

  None

- **Data Params**

  None

- **Success Response:**

  - **Code:** 200 OK <br />
    **Content:**
    ```
    [
    {
        "notificationId": 1,
        "message": "Podsjetnik za događaj 1",
        "date": "2020-03-22T22:02:32.367+0000",
        "isRead": false,
        "eventId": 1,
        "userId": 12
    },
    {
        "notificationId": 2,
        "message": "Podsjetnik za događaj 2",
        "date": "2020-03-22T22:02:32.405+0000",
        "isRead": false,
        "eventId": 1,
        "userId": 122
    },
    {
        "notificationId": 3,
        "message": "Podsjetnik za događaj 3",
        "date": "2020-03-22T22:02:32.415+0000",
        "isRead": false,
        "eventId": 1,
        "userId": 123
    }
    ]
    ```

- **Error Response:**

  - **Code:** 500 SERVER ERROR <br />
    **Content:** 
    ```
    {
        "status": "INTERNAL_SERVER_ERROR",
        "message": "EntityRepresentationModel not found!",
        "errors": [
        "Error occurred"
        ]
    }
    ```

- **Sample Call:**

  ```
  curl http://localhost:8080/notifications

  ```

### 2. **Get notification by id**

Vraća notifikaciju na osnovu njegovog id-a

- **URL**

  /notifications/getById/:id

- **Method:**

  `GET`

- **URL Params**

  **Required:**

  `id=[long]`

- **Data Params**

  None

- **Success Response:**

  - **Code:** 200 OK <br />
    **Content:**
    ```
    {
    "notificationId": 2,
    "message": "Podsjetnik za događaj 2",
    "date": "2020-03-22T22:29:34.309+0000",
    "isRead": false,
    "eventId": 1,
    "userId": 122
    }
    ```
   

- **Error Response:**

  - **Code:** 404 NOT FOUND <br />
    **Content:** 
    ```
    {
        "message": "Element not found"
    }
    ```
    or
     ```
        {
            "status": "BAD_REQUEST",
            "message": "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"\"2\"\"",
            "errors": [
                "id should be of type java.lang.Long"
            ]
        }
      ```

- **Sample Call:**

  ```
  curl http://localhost:8080/notifications/getById/2

  ```

### 3. **Get notification by user id**

Vraća sve notifikacije za jednog usera na osnovu id-a usera

- **URL**

  /notifications/getByUserId/:id

- **Method:**

  `GET`

- **URL Params**

  **Required:**

  `id=[long]`

- **Data Params**

  None

- **Success Response:**

  - **Code:** 200 OK <br />
    **Content:**
    ```
    [
    {
        "notificationId": 2,
        "message": "Podsjetnik za događaj 2",
        "date": "2020-03-22T22:29:34.309+0000",
        "isRead": false,
        "eventId": 1,
        "userId": 122
    }
    ]
    ```

- **Error Response:**

  - **Code:** 404 NOT FOUND <br />
    **Content:** 
    ```
        {
            "message": "Element not found"
        }
    ```
    or
    ```
    {
        "status": "BAD_REQUEST",
        "message": "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"\"2\"\"",
        "errors": [
            "id should be of type java.lang.Long"
        ]
    }
    ```

- **Sample Call:**

  ```
  curl http://localhost:8080/notifications/getByUserId/122

  ```
### 4. **Get read notification by user id**

Vraća sve notifikacije za jednog usera na osnovu id-a usera koje su pročitane

- **URL**

  /notifications/getByUserIdRead/:id

- **Method:**

  `GET`

- **URL Params**

  **Required:**

  `id=[long]`

- **Data Params**

  None

- **Success Response:**

  - **Code:** 200 OK <br />
    **Content:**
    ```
    [
    {
        "notificationId": 2,
        "message": "Podsjetnik za događaj 2",
        "date": "2020-03-22T22:29:34.309+0000",
        "isRead": true,
        "eventId": 1,
        "userId": 122
    }
    ]
    ```

- **Error Response:**

  - **Code:** 404 NOT FOUND <br />
    **Content:** 
    ```
            {
                "message": "Element not found"
            }
      ```
       or
      ```
        {
            "status": "BAD_REQUEST",
            "message": "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"\"2\"\"",
            "errors": [
                "id should be of type java.lang.Long"
            ]
        }
   ```

- **Sample Call:**

  ```
  curl http://localhost:8080/notifications/getByUserIdRead/122

  ```

### 5. **Get notification that have not been read by user id**

Vraća sve notifikacije za jednog usera na osnovu id-a usera koje nisu pročitane

- **URL**

  /notifications/getByUserIdNotRead/:id

- **Method:**

  `GET`

- **URL Params**

  **Required:**

  `id=[long]`

- **Data Params**

  None

- **Success Response:**

  - **Code:** 200 OK <br />
    **Content:**
    ```
    [
    {
        "notificationId": 2,
        "message": "Podsjetnik za događaj 2",
        "date": "2020-03-22T22:29:34.309+0000",
        "isRead": false,
        "eventId": 1,
        "userId": 122
    }
    ]
    ```

- **Error Response:**

  - **Code:** 404 NOT FOUND <br />
    **Content:** 
    ```
            {
                "message": "Element not found"
            }
      ```
        or
      ```
        {
            "status": "BAD_REQUEST",
            "message": "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"\"2\"\"",
            "errors": [
                "id should be of type java.lang.Long"
            ]
        }
      ```

- **Sample Call:**

  ```
  curl http://localhost:8080/notifications/getByUserIdNotRead/122

  ```

### 6. **Get notification by event id**

Vraća sve notifikacije na osnovu id-a eventa

- **URL**

  /notifications/getByEvent/:id

- **Method:**

  `GET`

- **URL Params**

  **Required:**

  `id=[long]`

- **Data Params**

  None

- **Success Response:**

  - **Code:** 200 OK <br />
    **Content:**
    ```
    [
    {
        "notificationId": 2,
        "message": "Podsjetnik za događaj 2",
        "date": "2020-03-22T22:29:34.309+0000",
        "isRead": true,
        "eventId": 1,
        "userId": 122
    }
    ]
    ```

- **Error Response:**

  - **Code:** 404 NOT FOUND <br />
    **Content:** 
    ```
            {
                "message": "Element not found"
            }
      ```
        or
    ```
        {
            "status": "BAD_REQUEST",
            "message": "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"\"2\"\"",
            "errors": [
                "id should be of type java.lang.Long"
            ]
        }
     ```

- **Sample Call:**

  ```
  curl http://localhost:8080/notifications/getByEventId/1

  ```

### 7. **Delete notification by id**

Briše notifikaciju na osnovu id-a

- **URL**

  /notifications/:id

- **Method:**

  `DELETE`

- **URL Params**

  **Required:**

  `id=[long]`

- **Data Params**

  None

- **Success Response:**

  - **Code:** 200 OK <br />
    **Content:**
    ```
    {
        "message": "Successful deletion of the notification with id: 1"
    }
    ```

- **Error Response:**

  - **Code:** 400 BAD REQUEST <br />
    **Content:** 
    ```
    {
        "message": "Error deleting notifications with id: 13"
    }
    ```
    
- **Sample Call:**

  ```
  curl -X DELETE http://localhost:8080/notifications/2

  ```
### 8. **Delete all notifications of one user**

Briše sve notifikacije za jednog user-a na osnovu njegovog id-a

- **URL**

  /notifications/deleteByUserId/:id

- **Method:**

  `DELETE`

- **URL Params**

  **Required:**

  `id=[long]`

- **Data Params**

  None

- **Success Response:**

  - **Code:** 200 OK <br />
    **Content:**
    ```
        {
            "message": "Successful deletion of the notification with id: 1"
        }
    ```

- **Error Response:**

  - **Code:** 400 BAD REQUEST <br />
    **Content:** 
     ```
     {
         "message": "Error deleting notifications with id: 13"
     }
     ```

- **Sample Call:**

  ```
  curl -X DELETE http://localhost:8080/notifications/deleteByUserId/2

  ```

### 9. **Add notification**

Briše sve notifikacije za jednog user-a na osnovu njegovog id-a

- **URL**

  /notifications

- **Method:**

  `POST`

- **URL Params**

  **Required:**

  `userId=[long]`
  `eventId=[long]`
  `message=[string]`
  `date=[message]`
  `isRead=[boolean]`

- **Data Params**

  None

- **Success Response:**

  - **Code:** 200 OK <br />
    **Content:**
    ```{
    "notificationId": 4,
    "message": "\"Nova poruka\"",
    "date": "2021-02-21T23:00:00.000+0000",
    "isRead": false,
    "eventId": 1,
    "userId": 122
    }
    ```

- **Error Response:**

  - **Code:** 400 BAD REQUEST <br />
    **Content:** 
 ```
    {
        "status": "BAD_REQUEST",
        "message": "Required boolean parameter 'isRead' is not present",
        "errors": [
            "isRead parameter is missing"
        ]
    }
```
or
```
{
    "status": "BAD_REQUEST",
    "message": "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"\"23\"\"",
    "errors": [
        "userId should be of type java.lang.Long"
    ]
}
```

- **Sample Call:**

  ```
  curl --data "userId=122&eventId=2&message="eee"&date=20/06/2020&isRead=false" http://localhost:8080/notifications
  ```


# Event routes

### 1. **Get all events**

Vraća sve evente.

- **URL**

  /events

- **Method:**

  `GET`

- **URL Params**

  None

- **Data Params**

  None

- **Success Response:**

  - **Code:** 200 OK <br />
    **Content:**
    ```
    [
    {
        "eventId": 1
    },
    {
        "eventId": 2
    },
    {
        "eventId": 3
    }
    ]
    ```

- **Error Response:**

  - **Code:** 500 SERVER ERROR <br />
    **Content:** 
 ```
     {
         "status": "INTERNAL_SERVER_ERROR",
         "message": "EntityRepresentationModel not found!",
         "errors": [
         "Error occurred"
         ]
     }
  ```

- **Sample Call:**

  ```
  curl http://localhost:8080/events

  ```

### 2. **Get event by id**

Vraća event na osnovu njegovog id-a

- **URL**

  /events/getById/:id

- **Method:**

  `GET`

- **URL Params**

  **Required:**

  `id=[long]`

- **Data Params**

  None

- **Success Response:**

  - **Code:** 200 OK <br />
    **Content:**
    ```
    {
    "eventId": 1
    }
    ```

- **Error Response:**

  - **Code:** 404 NOT FOUND <br />
    **Content:** 
    ```
    {
        "message": "Element not found"
    }
    ```
    or
    ```
    {
        "status": "BAD_REQUEST",
        "message": "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"\"99\"\"",
        "errors": [
            "id should be of type java.lang.Long"
        ]
    }
    ```

- **Sample Call:**

  ```
  curl http://localhost:8080/events/getById/22

  ```

### 3. **Add event**

Omogucava dodavanje novog eventa u bazu

- **URL**

  /users

- **Method:**

  `POST`

- **URL Params**

  None

- **Data Params**

  - **Type:** JSON <br />
    **Data:** `{ "id" : "1" }`

- **Success Response:**

  - **Code:** 200 OK <br />
    **Content:**
    ```
   {
    "eventId": 2
   }
    ```

- **Error Response:**

  - **Code:** 500 SERVER ERROR <br />
    **Content:** `{
    "timestamp": "2020-03-22T22:05:18.043+0000",
    "status": 500,
    "error": "Internal Server Error",
    "message": "For input string: \"id=6\"",
    "path": "/events"
}`

- **Sample Call:**

  ```curl -H "Content-Type: application/json" -X POST -d 15 http://localhost:8080/events

  ```


# User routes

### 1. **Get all users**

Vraća sve usere.

- **URL**

  /users

- **Method:**

  `GET`

- **URL Params**

  None

- **Data Params**

  None

- **Success Response:**

  - **Code:** 200 OK <br />
    **Content:**
    ```
    [
    {
        "userId": 12
    },
    {
        "userId": 23
    },
    {
        "userId": 122
    },
    {
        "userId": 123
    }
    ]
    ```

- **Error Response:**

  - **Code:** 500 SERVER ERROR <br />
    **Content:** 
 ```
      {
          "status": "INTERNAL_SERVER_ERROR",
          "message": "EntityRepresentationModel not found!",
          "errors": [
          "Error occurred"
          ]
      }
  ```

- **Sample Call:**

  ```
  curl http://localhost:8080/users

  ```

### 2. **Get user by id**

Vraća korisnika na osnovu njegovog id-a

- **URL**

  /users/getById/:id

- **Method:**

  `GET`

- **URL Params**

  **Required:**

  `id=[long]`

- **Data Params**

  None

- **Success Response:**

  - **Code:** 200 OK <br />
    **Content:**
    ```
    {
    "userId": 1
    }
    ```

- **Error Response:**

  - **Code:** 404 NOT FOUND <br />
    **Content:** 
 ```
     {
         "message": "Element not found"
     }
 ```
  or
  ```
     {
         "status": "BAD_REQUEST",
         "message": "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"\"99\"\"",
         "errors": [
             "id should be of type java.lang.Long"
         ]
     }
  ```

- **Sample Call:**

  ```
  http://localhost:8080/users/getById/22

  ```

### 3. **Add user**

Omogucava dodavanje user-a u bazu

- **URL**

  /users

- **Method:**

  `POST`

- **URL Params**

  None

- **Data Params**

  - **Type:** JSON <br />
    **Data:** `{ "id" : "1" }`

- **Success Response:**

  - **Code:** 200 OK <br />
    **Content:**
    ```
    {
    "userId": 2
    }
    ```

- **Error Response:**

  - **Code:** 500 SERVER ERROR <br />
    **Content:** `{
    "timestamp": "2020-03-22T21:45:54.653+0000",
    "status": 500,
    "error": "Internal Server Error",
    "message": "For input string: \"id=1\"",
    "path": "/users"
  }`

- **Sample Call:**

  ```
  curl -H "Content-Type: application/json" -X POST -d 5 http://localhost:8080/users

  ```

