# Auth routes

### 1. **Login**

Omogucava user-u da se uloguje sa svojim username-om i passwordom.

- **URL**

  /api/auth/login

- **Method:**

  `POST`

- **URL Params**

  None

- **Data Params**

  **Required:**

  - **Type:** JSON <br />
    **Data:** `{ username : masha, password : mojpw }`

- **Success Response:**

  - **Code:** 200 OK <br />
    **Content:**
    ```
    {
      "user": {
        "id": 1,
        "name": "Haris",
        "surname": "Masovic",
        "username": "masha"
      },
      "token": "random string"
    }
    ```

- **Error Response:**

  - **Code:** 400 BAD REQUEST <br />
    **Content:** `{ error: 'Error with request params. '}`

  OR

  - **Code:** 500 SERVER ERROR <br />
    **Content:** `{ error: 'Error while trying to login. ' }`

- **Sample Call:**

  ```javascript
  $.ajax({
    url: '/api/auth/login',
    body: {
      "username": "masha",
      "password": "mojpw"
    }
    type: 'POST',
    success: function(r) {
      console.log(r);
    },
  });
  ```

### 2. **Signup**

Sluzi za pravljenje novog user-a tj. dodavanje u sistem.

- **URL**

  /api/auth/signup

- **Method:**

  `POST`

- **URL Params**

  None

- **Data Params**

  **Required:**

  - **Type:** JSON <br />
    **Data:**

  ```
    {
      "username": "mashashama",
      "name": "mashonista",
      "surname": "zapratite na instagramu",
      "password": "passwordnovisuperdobar"
    }
  ```

- **Success Response:**

  - **Code:** 200 OK <br />
    **Content:** `{ "username": "Masha", "name": "Haris", "surname": "Masovic" }`

- **Error Response:**

  - **Code:** 400 BAD REQUEST <br />
    **Content:** `{ "error": "Error creating user. " }`

- **Sample Call:**

  ```javascript
  $.ajax({
    url: '/api/auth/signup',
    body: {
      "username": "mashashama",
      "name": "mashonista",
      "surname": "zapratite na instagramu",
      "password": "passwordnovisuperdobar"
    }
    type: 'POST',
    success: function(r) {
      console.log(r);
    },
  });
  ```

### 3. **Check token**

Sluzi za provjeri tokena, da li je validan.

- **URL**

  /api/auth/check-token

- **Method:**

  `GET`

- **URL Params**

  None

- **Data Params**

  None

- **Success Response:**

  - **Code:** 200 OK <br />
    **Content:** `{ "tokenValid": true }`

- **Error Response:**

  - **Code:** 405 NOT AUTHORIZED <br />
    **Content:** `{ "error": true, "message": "Unauthorized access." }`

  OR

  - **Code:** 403 FORBIDDEN <br />
    **Content:** `{ error: true, message: 'No token provided.' }`

* **Sample Call:**

  ```javascript
  $.ajax({
    url: '/api/auth/check-token',
    headers: {
      Authorization : `Bearer ${token}`
    }
    type: 'GET',
    success: function(r) {
      console.log(r);
    },
  });
  ```

# User routes

### 1. **Get all users**

Omogucava dobavljanje svih user-a u sistemu.

- **URL**

  /api/users

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
          "id": 1,
          "username": "masha",
          "name": "Haris",
          "surname": "Masovic",
          "password": "XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=",
          "picture": null,
          "description": null,
          "dateOfRegistration": null,
          "createdAt": "2020-03-22T20:46:51.000Z",
          "updatedAt": "2020-03-22T20:46:51.000Z"
      },
    ...
    ]
    ```

- **Error Response:**

  - **Code:** 500 SERVER ERROR <br />
    **Content:** `{ error: 'Error getting all users. ' }`

- **Sample Call:**

  ```javascript
  $.ajax({
    url: '/api/users',
    headers: {
      Authorization : `Bearer ${token}`
    }
    type: 'GET',
    success: function(r) {
      console.log(r);
    },
  });
  ```

### 2. **Get specific user**

Omogucava user-u da dobije specificnog korisnika.

- **URL**

  /api/users/:username

- **Method:**

  `GET`

- **URL Params**

  **Required:**

  `username=[string]`

- **Data Params**

  None

- **Success Response:**

  - **Code:** 200 OK <br />
    **Content:**
    ```
    {
          "id": 1,
          "username": "masha",
          "name": "Haris",
          "surname": "Masovic",
          "password": "XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=",
          "picture": null,
          "description": null,
          "dateOfRegistration": null,
          "createdAt": "2020-03-22T20:46:51.000Z",
          "updatedAt": "2020-03-22T20:46:51.000Z"
    }
    ```

- **Error Response:**

  - **Code:** 404 SERVER ERROR <br />
    **Content:** `{ error: true, message: 'User not found.', }`

  OR

  - **Code:** 500 SERVER ERROR <br />
    **Content:** `{ error: 'Error getting specific user. ' }`

- **Sample Call:**

  ```javascript
  $.ajax({
    url: '/api/users/masha',
    type: 'GET',
    headers: {
      Authorization : `Bearer ${token}`
    }
    success: function(r) {
      console.log(r);
    },
  });
  ```

### 3. **Update your account**

Omogucava user-u da update-uje svoj profil.

- **URL**

  /api/users/update

- **Method:**

  `PUT`

- **URL Params**

  None

- **Data Params**

  - **Type:** JSON <br />
    **Data:** `{ "name" : "novi instagram name" }`

- **Success Response:**

  - **Code:** 200 OK <br />
    **Content:**
    ```
    {
        "user": {
            "id": 1,
            "name": "novi instagram name",
            "surname": "Masovic",
            "username": "masha"
        },
        "token": "random string"
    }
    ```

- **Error Response:**

  - **Code:** 404 SERVER ERROR <br />
    **Content:** `{ error: true, message: 'User not found.' }`

  OR

  - **Code:** 500 SERVER ERROR <br />
    **Content:** `{ error: 'Error updating user. ' }`

- **Sample Call:**

  ```javascript
  $.ajax({
    url: '/api/users/update',
    type: 'PUT',
    headers: {
      Authorization : `Bearer ${token}`
    }
    success: function(r) {
      console.log(r);
    },
  });
  ```

### 4. **Delete your account**

Omogucava user-u da izbrise svoj profil.

- **URL**

  /api/users/delete

- **Method:**

  `DELETE`

- **URL Params**

  None

- **Data Params**

  None

- **Success Response:**

  - **Code:** 200 OK <br />
    **Content:** `{ message: 'User successfully deleted. ' }`

- **Error Response:**

  - **Code:** 404 SERVER ERROR <br />
    **Content:** `{ error: true, message: 'User not found.' }`

  OR

  - **Code:** 500 SERVER ERROR <br />
    **Content:** `{ error: 'Error deleting user. ' }`

- **Sample Call:**

  ```javascript
  $.ajax({
    url: '/api/users/delete',
    type: 'DELETE',
    headers: {
      Authorization : `Bearer ${token}`
    }
    success: function(r) {
      console.log(r);
    },
  });
  ```
