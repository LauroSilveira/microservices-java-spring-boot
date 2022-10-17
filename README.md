# Microservice repository 

This is a repository of microserivoces built with Spring boot, 
Eureka Service Register, Eureka Service discovery,  Circuit breaker Resilience4J, API Gateway, OpeFegin and Mysql as databse.

## Summary
* [Microservice repository](#microservice-repository)
    * [Summary](#summary)
    * [About](#about)
    * [Achitecture Diagram](#achitecture-diagram)
    * [Techinologies](#techinologies)
    * [How to run](#how-to-run)
      * [Example of Requests](#example-of-requests)
    * [Load Balance](#load-balance)
    * [Development Status](#development-status)
    * [Future Development](#future-development)
    * [Contributors](#contributors)

## About
This repository consists is to learn more about microservices with Spring framework.

Specificly in this case was used sinchronous comunication between two microservices, 
Payments and Order Payments using Spring cloud feign, an Api Gateway to do the load balance and Service register.
In the future I will refactor to use assichronous comunication with Apcha kafka.

## Achitecture Diagram
![](data/Arquitecture.drawio-3.drawio.png)

## Techinologies 

- Spring boot 2.7.4
- MapStruct
- Spring Cloud Gateway
- Spring Eureka Register
- Srping Eureka Discorvery
- Resilience4J Circuit breaker
- Flyaway migration database
- Maven 3.8.6
- Java 17


## How to run 
Download all microservices and in your favorite console run:
```
mvn clean install
```
This will install all dependencies necessaries.

After you have to execute the following command in service-discovery, gateway, ms-order and ms-payments.

**Importante: make sure you are at the same directory of pom.xml**

```
mvn spring-boot:run
```
### Example of requests
***Important*** Make sure that you have running Service-discover and Api Gateway before run any curl. 
In order to do the request, I will explain the estructure of url.
All requests have to pass through Api Gateway so the port is `localhost:8082`, if you already have this port in use 
just modify the port in .yml file from Api Gateway. 
After `localhost:8082` just put the of miservice that you want, for e.g. `localhost:8082/ms-order` and finally the resource
`localhost:8082/ms-order/order`

Let`s see some examples of requests bellow:

To Get a Payment by an especific id: 
```js
http://localhost:8082/ms-payments/payments/1
```
To Get all Payments:
```js
http://localhost:8082/ms-payments/payments
```

To create a new Order (method Post):
```js
localhost:8082/ms-order/order
```
Body of request:
```js
{
    "items": [
        {
            "quantity": 1,
            "description": "XBOX Series X"
        },
        {
            "quantity": 1,
            "description": "Macbook Por 16 pulgadas"
        },
        {
            "quantity": 1,
            "description": "Ordernador Gamming"
        }
    ]
}
```

## Load Balance
All microservices are implemented with Spring Cloud that includs load balance.
In orde to see the load balance in action you can use this endpoint:

```js
http://localhost:8082/ms-order/order/port
```
For each request this will show a different port. That is the Load Balance running.

## Development Status 

- [x] Creation of ms-payments and ms-order.
- [x] Creation of Api Gateway.
- [x] Implementation of Spring Eureka service register and service discovery.
- [x] Implementation of fallback circuit breaker with Resilience4j.
- [x] Comunication between microservice with Spring Feign client.

## Future Development

- [ ] Creation of unity tests and integration test.
- [ ] Aplly Atuhentication with JWT token.
- [ ] Assichronous comunication with Apacha kafka.
- [ ] Deploy on cloud using docker.

## Contributors
[@LauroSilveira](https://github.com/LauroSilveira)

Fell free to fork and contribute :wink: