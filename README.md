# 👩‍🏭️Auto-service👨‍🏭️

A RESTful application built using Spring Boot framework.
The program processes orders and services for car maintenance
under the guidance of an administrator.
## ✨Features✨

* Creation of new entities such as: Car, Owner, Product, Master, Service, Order.
* Editing Entity Data.
* Changing Order/Service statuses.
* Calculation of the cost of the Order.
* Calculation and issuance of wages to the Master.

## 🧬Project structure🧬

### N-tier architecture
Project built by using N-tier architecture divides application
into logical layers:
* **Controller**: Separates the UI from the backend logic. The controller has a service component autowired which can help him return the request.
* **Service**: Interacts with data through an autowired repository
  and contains the business logic that move and process data between the data and presentation layers.
* **Repository**: Data access layer. Interacts with the chosen way to persist data.
* **Data tier**: data store/retrieve layer.

### DB structure

## 👩‍💻Technologies👩‍💻
* JDK 17
* Apache Maven
* PostgreSQL
* JPA, Hibernate
* Spring Boot
* Swagger
* Docker

## 🛠Setup guide🛠
Clone this repository.
####
To run this project locally on your computer, you need to make sure you have the following components installed:
* JDK 17 ([installation guide](https://docs.oracle.com/en/java/javase/17/install/overview-jdk-installation.html#GUID-8677A77F-231A-40F7-98B9-1FD0B48C346A))
* Apache Maven ([download here](https://maven.apache.org/download.cgi), [installation guide](https://maven.apache.org/install.html))
* Docker ([installation guide](https://docs.docker.com/desktop/install/windows-install/))

## Application configuration
### Run application in Docker
To run this application open terminal in project directory and run following command in terminal:
```docker-compose up```

Application will start working.
#### You will be connected to default access port: `http://localhost:6868`
####
### Stop the program
To stop the application you need to terminate the currently-running program.
To do so press `Ctrl+C` shortcut on your keyboard.
To completely stop containers and remove containers, networks,
volumes, and images created by `up` run following command in terminal:
```docker-compose down```

## 🚀You are ready to go!🚀
To test application functionality it`s better to use Swagger or [Postman](https://www.postman.com/downloads/),
where you can send API requests to controllers end-points and get responses.  
To test the application in Swagger go to the [url](http://localhost:6868/swagger-ui/index.html#/) 
with SwaggerUI while application is running.
####
**List of available end-points:**
* `POST: /cars` - creates a new car entity and saves it to db;
* `PUT: /cars/{id}` - updates all car fields by its id;
######
* `POST: /masters` - creates a new master entity and saves it to db;
* `PUT: /masters/{id}` - updates all master fields by its id;
* `GET: /masters/{id}/orders` - returns all master orders by its id;
######
* `POST: /orders` - creates a new order entity and saves it to db;
* `PUT: /orders/{id}` - updates all order fields by its id;
* `PUT: /orders/{id}/add-product` - adds product id to product ids list of order by its id;
* `PUT: /orders/{id}/status` - updates order status by its id;
* `GET: /orders/{id}/cost` - calculates final price of order by its id;
######
* `POST: /owners` - creates a new car entity and saves it to db;
* `PUT: /owners/{id}` - updates all owner fields by its id;
* `GET: /owners/{id}/orders` - returns all owner orders by its id;
######
* `POST: /products` - creates a new product entity and saves it to db;
* `PUT: /products/{id}` - updates all product fields by its id;
######
* `POST: /services` - creates a new service entity and saves it to db;
* `PUT: /services/{id}` - updates all service fields by its id;
* `PUT: /services/{id}/status` - updates service status by its id;
