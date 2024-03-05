# TV Program Plan REST API

This is a RESTful API for retrieving TV program plan frequency-related data. It provides endpoints to retrieve information about the most frequent TV shows and frequency-ordered TV shows.

## Getting Started

To consume this API, you can use any HTTP client library or tools such as cURL or Postman.

### Base URL

The base URL for accessing the API is:
http://localhost:8080/api

## Starting the Server as a Spring Boot Application

To run the server as a Spring Boot application, follow these steps:

1. **Clone the Repository**: Start by cloning this repository to your local machine then navigating to the Project Directory using the following commands:

   ```bash
   git clone <repository_url>
   cd <project_directory>

2. Build the Project: Before running the server, you need to build the project. Execute the following Maven command:
   ```bash
   mvn clean install

3. Run the Application: After successfully building the project, you can start the server by running the following command:

    ```bash
    mvn spring-boot:run

This command will start the Spring Boot application. Once the server is up and running, you will see log messages indicating that the application has started.

4. Accessing the Endpoints: By default, the server will run on http://localhost:8080. 
   You can access the endpoints defined in the application by navigating to this URL in your web browser or using tools like Postman.

5. Shutting Down the Server: To stop the server, you can simply press Ctrl + C in the terminal where the server is running. 
This will gracefully shut down the Spring Boot application.

## Endpoints

### Retrieve Most Frequent TV Shows

#### GET /mostFrequantShows/{date}

Retrieve the most frequent TV shows airing on the specified date.

**Parameters:**
- `date`: The date for which the most frequent TV shows are to be retrieved. Format: `YYYY-MM-DD`.

**Example:**
GET /mostFrequantShows/2024-03-15


#### GET /mostFrequantShows

Retrieve the most frequent TV shows without specifying the date.

**Example:**
GET /mostFrequantShows


### Retrieve Frequency-Ordered TV Shows

#### GET /frequencyOrderedShows/{date}

Retrieve the TV shows ordered by frequency for the specified date.

**Parameters:**
- `date`: The date for which the TV shows are to be retrieved. Format: `YYYY-MM-DD`.

**Example:**
GET /frequencyOrderedShows/2024-03-15


#### GET /frequencyOrderedShows

Retrieve the TV shows ordered by frequency without specifying the date.

**Example:**
GET /frequencyOrderedShows


### Retrieve Most Frequent TV Shows By Type

#### GET /mostFrequantShowsOrderedShowsByType/{date}/{type}

Retrieve the most frequent TV shows of a specific type airing on the specified date.

**Parameters:**
- `date`: The date for which the most frequent TV shows are to be retrieved. Format: `YYYY-MM-DD`.
- `type`: The type of TV shows to retrieve. Possible values: `TvShow`, `Series`, `Movie`.

**Example:**
GET /mostFrequantShowsOrderedShowsByType/2024-03-15/TvShow


#### GET /mostFrequantShowsOrderedShowsByType/{type}

Retrieve the most frequent TV shows of a specific type without specifying the date.

**Parameters:**
- `type`: The type of TV shows to retrieve. Possible values: `TvShow`, `Series`, `Movie`.

**Example:**
GET /mostFrequantShowsOrderedShowsByType/TvShow


### Retrieve Frequency-Ordered TV Shows By Type

#### GET /frequencyOrderedShowsByType/{date}/{type}

Retrieve the TV shows of a specific type ordered by frequency for the specified date.

**Parameters:**
- `date`: The date for which the TV shows are to be retrieved. Format: `YYYY-MM-DD`.
- `type`: The type of TV shows to retrieve. Possible values: `TvShow`, `Series`, `Movie`.

**Example:**
GET /frequencyOrderedShowsByType/2024-03-15/TvShow


#### GET /frequencyOrderedShowsByType/{type}

Retrieve the TV shows of a specific type ordered by frequency without specifying the date.

**Parameters:**
- `type`: The type of TV shows to retrieve. Possible values: `TvShow`, `Series`, `Movie`.

**Example:**
GET /frequencyOrderedShowsByType/TvShow


## Response Format

All endpoints return data in JSON format. The response contains an array of `FrequencyResponse` objects representing TV shows and their frequencies.

```json
[
  {
    "title": "Show A",
    "frequency": 10
  },
  {
    "title": "Show B",
    "frequency": 8
  },
  ...
]


