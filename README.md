myRetail Services for products

## Overview
This is an implementation of restservices to get, add and update price details of products available. 
myRetail services fetch name of the product from third party rest service. The price information of the product is stored on the h2 database to reduce the dependency on external db. For prod environment, an external nosql db can be used by using spring-profiles.
It is built on Spring Boot framework with other relevant dependencies.

### Frameworks and Maven dependencies used :-
* [1] SpringBoot
* [2] Maven
* [3] Swagger
* [4] Liquibase for db changes versioning
* [5] H2 Database to reduce the dependency on external db.
* [6] Apache DBCP for database connection pooling.
* [7] spring-boot-starter-web for developing rest services.
* [8] spring-boot-starter-validation for validation of requests.
* [9] spring-boot-starter-cache for caching product-info obtained from third party service.
* [10] spring-boot-starter-data-jpa for interactions with db.
* [11] spring-boot-starter-test for Unit & Integration Tests.
* [12] spring-boot-devtools for development with live reloading.


### Prerequisites:-
* [1] SpringBoot Tool suite or any IDE framework for ease of use.
* [2] Maven

### How to start application ?
1. Execute mvn spring-boot:run command to build and run app or use samples/myretail-1.0.0.jar which is committed separately. 
2. Run the below command to start app.

java -jar myretail-1.0.0.jar

### Use Swagger UI to test services
Swagger UI is available at http://localhost:8080/swagger-ui.html
Below are the sample request and response for the services,

* [1] To get product details,

  Http Method : Get

  URL : 
  http://localhost:8080/myretail/v1/products/13860427
  
  Response :
  {"id":13860327,"name":"Conan the Barbarian (Blu-ray)","current_price":{"currency_code":"USD","value":3.90}}

* [2] To add price details for the product,

  Http Method : Post

  URL : 
  http://localhost:8080/myretail/v1/products/13860327
  
  Request Body :
  {"currency_code": "USD", "value": 3.80}
  
  Response :
  {"id":13860327,"name":"Conan the Barbarian (Blu-ray)","current_price":{"currency_code":"USD","value":3.90}}

* [3] To get product details,
  
  Http Method : Put
  
  URL : 
  http://localhost:8080/myretail/v1/products/13860427
  
  Request Body :
  {"currency_code": "USD", "value": 3.90}
  
  Response :
  {"id":13860327,"name":"Conan the Barbarian (Blu-ray)","current_price":{"currency_code":"USD","value":3.90}}

    
  
### Screenshots and Standalone application
Screenshots and standalone application can be found inside samples folder.


