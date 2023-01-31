# RentMe

<!-- ABOUT THE PROJECT -->
## About The Project

Rent me is an app for maintaining reservations of residential facilities both short and long term!

### Requirements
You need to have a Java SDK installed and and set the JAVA_HOME and MAVEN_HOME environment variable to point to a valid Java SDK and Maven.

For testing an application use POSTMAN!

### Example request body for MonthSummaryReport:
{
    "dateFrom": "2023-02-01",
    "dateTo": "2023-03-01",
    "landLords" :[1,3]
}

### Project details
Project is based on Java17 and Spring Boot 3.0. Database is H2 in-memory due to fast accessability and 'just a review' usecase.
