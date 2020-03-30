# Events routes

**Swagger**
* **URL**

  /swagger-ui.html

**Get all cateogries**
----
  API for getting all cateogries

* **URL**

  /events-micro/categories

* **Method:**

  `GET`   

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
    ```
    [
        {
            "id": 1,
            "name": "movies"
        },
        {
            "id": 2,
            "name": "books"
        },
        {
            "id": 3,
            "name": "IT"
        }
    ]
    ```
 **Get category by id**
----
  Returns category (JSON) with given id.

* **URL**

  /events-micro/categories/:id

* **Method:**

  `GET`
  
*  **URL Params**

   **Required:**
 
   `id=[integer]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
    ```
    {
      "id": 1,
      "name": "movies"
    }
    ```
 
* **Error Response:**

  * **Code:** 500 Internal Server Error <br />
    **Content:** 
    ```
    {
        "timestamp": "2020-03-23T12:21:13.984+0000",
        "status": 500,
        "error": "Internal Server Error",
        "message": "No value present",
        "path": "/events-micro/categories/12"
    }
    ```
 **Get number of categories**
----
  Returns number of categories

* **URL**

  /events-micro/categories/count

* **Method:**

  `GET`
  
*  **URL Params**

   None

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `3`
    
**Get all locations**
----
  Returns all locations.

* **URL**

  /events-micro/locations

* **Method:**

  `GET`
  
*  **URL Params**

   None
   
* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
    ```
    [
        {
            "id": 7,
            "coordinates": {
                "x": 0.0,
                "y": 0.0
            },
            "city": "Sarajevo",
            "country": "Bosna i Hercegovina"
        },
        {
            "id": 8,
            "coordinates": {
                "x": 0.0,
                "y": 0.0
            },
            "city": "Mostar",
            "country": "Bosna i Hercegovina"
        },
        {
            "id": 9,
            "coordinates": {
                "x": 0.0,
                "y": 0.0
            },
            "city": "Banja Luka",
            "country": "Bosna i Hercegovina"
        }
    ]
    ```
 **Get location by id**
----
  Returns location of given id.

* **URL**

  /events-micro/locations/:id

* **Method:**

  `GET`
  
*  **URL Params**

   **Required:**
 
   `id=[integer]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
    ```
    {
        "id": 7,
        "coordinates": {
            "x": 0.0,
            "y": 0.0
        },
        "city": "Sarajevo",
        "country": "Bosna i Hercegovina"
    }
    ```
 
* **Error Response:**

  * **Code:** 500 INTERNAL SERVER ERROR <br />
    **Content:** 
    ```
    {
        "timestamp": "2020-03-23T12:31:27.966+0000",
        "status": 500,
        "error": "Internal Server Error",
        "message": "No value present",
        "path": "/events-micro/locations/73"
    }
   ```
**Get all events**
----
  Returns all events,

* **URL**

  /events-micro/events

* **Method:**

  `GET`
  
*  **URL Params**

   None

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
    ```
    [
        {
            "id": 10,
            "title": "LV4 NWT",
            "address": "Zmaja od Bosne bb",
            "date": "2020-03-23",
            "description": "Laboratorijske vježbe iz predmeta NWT",
            "category": {
                "id": 1,
                "name": "movies"
            },
            "creator": {
                "id": 4
            },
            "location": {
                "id": 7,
                "coordinates": {
                    "x": 0.0,
                    "y": 0.0
                },
                "city": "Sarajevo",
                "country": "Bosna i Hercegovina"
            },
            "active": true
        },
        {
            "id": 11,
            "title": "LV4 NWT",
            "address": "Zmaja od Bosne bb",
            "date": "2020-03-30",
            "description": "Laboratorijske vježbe iz predmeta NWT",
            "category": {
                "id": 1,
                "name": "movies"
            },
            "creator": {
                "id": 4
            },
            "location": {
                "id": 7,
                "coordinates": {
                    "x": 0.0,
                    "y": 0.0
                },
                "city": "Sarajevo",
                "country": "Bosna i Hercegovina"
            },
            "active": true
        }
    ]
    ```
 **Get event by id**
----
  Returns event with given id

* **URL**

  /events-micro/events/:id

* **Method:**

  `GET`
  
*  **URL Params**

   **Required:**
 
   `id=[integer]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
      ```
      {
          "id": 11,
          "title": "LV4 NWT",
          "address": "Zmaja od Bosne bb",
          "date": "2020-03-30",
          "description": "Laboratorijske vježbe iz predmeta NWT",
          "category": {
              "id": 1,
              "name": "movies"
          },
          "creator": {
              "id": 4
          },
          "location": {
              "id": 7,
              "coordinates": {
                  "x": 0.0,
                  "y": 0.0
              },
              "city": "Sarajevo",
              "country": "Bosna i Hercegovina"
          },
          "active": true
      }
      ```
 
* **Error Response:**

  * **Code:** 500 Internal Server Error <br />
    **Content:** 
    ```
    {
      "timestamp": "2020-03-23T12:43:48.850+0000",
      "status": 500,
      "error": "Internal Server Error",
      "message": "No value present",
      "path": "/events-micro/events/14"
    }
    ```
    
**Get number of events**
----
  Returns number of events

* **URL**

  /events-micro/events/count

* **Method:**

  `GET`
  
*  **URL Params**
 
  None

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `2`
    
**Get number of interested users**
----
  Returns number of users interested into event with given id

* **URL**

  /events-micro/interestedNum/:id

* **Method:**

  `GET`
  
*  **URL Params**
 
   **Required:**
 
   `id=[integer]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `0`
    
**Get number of users going**
----
  Returns number of users going to event with given id

* **URL**

  /events-micro/goingNum/:id

* **Method:**

  `GET`
  
*  **URL Params**
 
   **Required:**
 
   `id=[integer]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `1`
    
**Get interested users**
----
  Returns users interested into event with given id

* **URL**

  /events-micro/interested/:id

* **Method:**

  `GET`
  
*  **URL Params**
 
   **Required:**
 
   `id=[integer]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
    ```
    [
        {
            "id": 5
        }
    ]
    ```
    
**Get users going**
----
  Returns users going to event with given id

* **URL**

  /events-micro/going/:id

* **Method:**

  `GET`
  
*  **URL Params**
 
   **Required:**
 
   `id=[integer]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
     ```
    [
        {
            "id": 4
        }
    ]
    ```  
    
**Mark event as interested**
----
  Marks the event with given id for logged in user

* **URL**

  /events-micro/interested/:idEvent

* **Method:**

  `POST`
  
*  **URL Params**

   **Required:**
 
   `idEvent=[integer]`

* **Data Params**

  `idUser=[integer]`

* **Success Response:**

  * **Code:** 200 <br />
 
* **Error Response:**

   * **Code:** 500 Internal Server Error <br />
    **Content:** 
    ```
    {
        "timestamp": "2020-03-23T13:04:29.979+0000",
        "status": 500,
        "error": "Internal Server Error",
        "message": "No value present",
        "path": "/events-micro/interested/1"
    }
    ```
    
 **Mark event as going**
----
  Marks the event with given id for logged in user

* **URL**

  /events-micro/going/:idEvent

* **Method:**

  `POST`
  
*  **URL Params**

   **Required:**
 
   `idEvent=[integer]`

* **Data Params**

  `idUser=[integer]`

* **Success Response:**

  * **Code:** 200 <br />
 
* **Error Response:**

   * **Code:** 500 Internal Server Error <br />
    **Content:** 
    ```
    {
        "timestamp": "2020-03-23T13:04:29.979+0000",
        "status": 500,
        "error": "Internal Server Error",
        "message": "No value present",
        "path": "/events-micro/going/1"
    }
    ```
**Add new event**
----
  API for adding new event

* **URL**

  /events-micro/events

* **Method:**

  `POST`
  
*  **URL Params**

    None

* **Data Params**

  **Body:** 
    ```
    {
        "title": "LV5 NWT",
        "address": "Zmaja od Bosne bb",
        "date": "2020-03-21",
        "description": "Laboratorijske vježbe iz predmeta NWT",
        "idCategory":1,
        "idUser":5,
        "idLocation":7,
        "isActive":true
    }
    ```

* **Success Response:**

  * **Code:** 200 <br />
    **Content:**
        ```
    {
        "id": 17,
        "title": "LV5 NWT",
        "address": "Zmaja od Bosne bb",
        "date": "2020-03-21",
        "description": "Laboratorijske vježbe iz predmeta NWT",
        "category": {
            "id": 1,
            "name": "movies"
        },
        "creator": {
            "id": 5
        },
        "location": {
            "id": 7,
            "coordinates": {
                "x": 0.0,
                "y": 0.0
            },
            "city": "Sarajevo",
            "country": "Bosna i Hercegovina"
        },
        "active": true
    }
    ```
 
* **Error Response:**

   * **Code:** 500 Internal Server Error <br />
    **Content:** 
    ```
    {
        "timestamp": "2020-03-23T13:11:13.220+0000",
        "status": 500,
        "error": "Internal Server Error",
        "message": "The given id must not be null!; nested exception is java.lang.IllegalArgumentException: The given id must not be null!",
        "path": "/events-micro/events"
    }
    ```
**Update event**
----
  API for updating event with given id

* **URL**

  /events-micro/events/:id

* **Method:**

  `PUT`
  
*  **URL Params**

    **Required:**
 
   `id=[integer]`

* **Data Params**

  **Body:** 
    ```
   {
        "title": "LV4 NWT",
        "address": "Zmaja od Bosne bb",
        "date": "2020-03-23",
        "description": "Laboratorijske vježbe iz predmeta NWT",
        "idCategory":1,
        "idUser":4,
        "idLocation":7,
        "isActive":false

    }
    ```

* **Success Response:**

  * **Code:** 200 <br />
    **Content:**
    ```
    {
        "id": 10,
        "title": "LV4 NWT",
        "address": "Zmaja od Bosne bb",
        "date": "2020-03-23",
        "description": "Laboratorijske vježbe iz predmeta NWT",
        "category": {
            "id": 1,
            "name": "movies"
        },
        "creator": {
            "id": 4
        },
        "location": {
            "id": 7,
            "coordinates": {
                "x": 0.0,
                "y": 0.0
            },
            "city": "Sarajevo",
            "country": "Bosna i Hercegovina"
        },
        "active": false
    }
    ```
 
* **Error Response:**

   * **Code:** 500 Internal Server Error <br />
    **Content:** 
    ```
    {
        "timestamp": "2020-03-23T13:14:33.906+0000",
        "status": 500,
        "error": "Internal Server Error",
        "message": "No value present",
        "path": "/events-micro/events/12"
    }
    ```
 **Delete event**
----
  API for deleting event with given id

* **URL**

  /events-micro/events/:id

* **Method:**

  `PUT`
  
*  **URL Params**

    **Required:**
 
   `id=[integer]`

* **Data Params**
   
   None

* **Success Response:**

  * **Code:** 200 <br />
    
 
* **Error Response:**

   * **Code:** 500 Internal Server Error <br />
    **Content:** 
    ```
    {
        "timestamp": "2020-03-23T13:16:39.954+0000",
        "status": 500,
        "error": "Internal Server Error",
        "message": "No class com.event4u.eventsservice.model.Event entity with id 18 exists!",
        "path": "/events-micro/events/18"
    }
    ```
