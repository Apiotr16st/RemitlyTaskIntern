# RemitlyTaskIntern  
This repository contains a solution for the Remitly Internship 2025 exercise.  
This application parses `.csv` data into a database and provides basic endpoints for retrieving, adding, and deleting SWIFT codes.  

## Author  
- Piotr Andres  

## Used technologies 
- Java 17
- Spring Boot 3.4.3
- PostgreSQL 
- Gradle
- Docker and Docker Compose

## Prerequisites
- JDK 17 or higher
- Gradle 
- Git
- PostgreSQL
- Docker and Docker Compose

## How to set up
Clone repository and navigate to project directory
```
git clone https://github.com/Apiotr16st/RemitlyTaskIntern.git
cd RemitlyTaskIntern/task
```
Run Docker and start containers with Docker Compose
```
docker compose up -d
```
Check if the containers are running

```
docker ps
```
Build the project
```
./gradlew build
```
Run the project. After that, all endpoint are accesible via ```http://localhost:8080```
```
./gradlew bootRun
```
Or run unit and integration tests
```
./gradlew test
```


## REST API Endpoints


Endpoint 1: Retrieve details of a single SWIFT code whether for a headquarters or branches.
- GET: /v1/swift-codes/{swift-code} 

Response Structure for headquarter swift code:
```js
{
    "address": string,
    "bankName": string,
    "countryISO2": string,
    "countryName": string,
    "isHeadquarter": bool,
    "swiftCode": string,
    "branches": [
        {
        "address": string,
        "bankName": string,
        "countryISO2": string,
        "isHeadquarter": bool,
        "swiftCode": string
        },
        {
        "address": string,
        "bankName": string,
        "countryISO2": string,
        "isHeadquarter": bool,
        "swiftCode": string
        }, ...
    ]
}

```

Response Structure for branch swift code: 

```js
{
    "address": string,
    "bankName": string,
    "countryISO2": string,
    "countryName": string,
    "isHeadquarter": bool,
    "swiftCode": string
}
```

Endpoint 2: Return all SWIFT codes with details for a specific country (both headquarters and branches).
- GET:  /v1/swift-codes/country/{countryISO2code}

Response Structure :
```js
{
    "countryISO2": string,
    "countryName": string,
    "swiftCodes": [
        {
            "address": string,
    		 "bankName": string,
    		 "countryISO2": string,
    		 "isHeadquarter": bool,
    		 "swiftCode": string
        },
        {
            "address": string,
    		 "bankName": string,
    		 "countryISO2": string,
    		 "isHeadquarter": bool,
    		 "swiftCode": string
        }, ...
    ]
}
```

Endpoint 3: Adds new SWIFT code entries to the database for a specific country.
- POST:  /v1/swift-codes

Request Structure :

```js
{
    "address": string,
    "bankName": string,
    "countryISO2": string,
    "countryName": string,
    "isHeadquarter": bool,
    "swiftCode": string,
}
```
Response Structure: 

```js
{
    "message": string,
}
```


Endpoint 4: Deletes swift-code data if swiftCode matches the one in the database.
- DELETE:  /v1/swift-codes/{swift-code}

Response Structure: 

```js
{
    "message": string,
}
```

