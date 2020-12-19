<h1 align="center"> Spring Boot Shopping Cart APIs </h1> <br>

## Table of Contents

- [Introduction](#introduction)
- [Technologies] (#technologies)
- [Features](#features)
- [Build Process](#build-process)
- [Testing](#testing)

## Introduction
A Spring Boot application in Java to implement a simple shopping cart and it should provide the shopping cart  REST endpoints.
The user should be able to: 
- View cart : list of all items & total cart price
- Add item to cart : ability to add one or more items of same type
- Remove item from cart
- Checkout : can be done only once

Different users to have separate shopping carts which can be differentiated by userId in HTTP_HEADER.
As this is to demonstrate REST API for shopping cart,for ease of implementation the cart data will not be persisted and products data will be hardcoded within the service.

## Technologies
Install following tools:<br>
- Spring Source Tool Suite 4
- Spring Boot 2.4.1
- Java 8
- Maven 3.6.3

## Features

|   API-NAME	       |  REST METHOD |   ENDPOINT   | REQUEST HEADER  |
|:------------------:|:-------------|:-----------------------:|:--------------:|
| Available Products |      GET     | **/shop/availableProducts** |     |
| View Cart          |      GET     | **/shop/viewCart**       | user-id=testuser1      |
| Add Item           |      POST    | **/shop/addItem/XYZ1**   | user-id=testuser1      |
| Remove Item        |     DELETE   | **/shop/removeItem/XYZ2**| user-id=testuse1      |
| View Cart          |      POST    | **/shop/checkout**       | user-id=testuse1      |

## Build Process
**Using the Maven Plugin**<br>
$ mvn spring-boot:run

**Using Executable Jar**<br>
To create an executable jar run:<br>
$ mvn clean package<br><br>

To run that application, use the java -jar command, as follows:<br>
$ java -jar target/justshopme-shopping-cart-0.0.1-SNAPSHOT.jar<br>
To exit the application, press ctrl-c.

**Using STS**<br>
Start the Spring boot Application using STS Run As "Spring Boot App"

## Testing

**CLI-using CURL**<br>
curl http://localhost:8080/shop/availableProducts<br>
**Output:** [{"id":"XYZ1","name":"Apple iPhone 11","price":999.0},{"id":"XYZ2","name":"Apple iPhone X","price":899.0},{"id":"XYZ3","name":"Apple iWatch","price":499.0},{"id":"XYZ4","name":"Apple iPhone 11 Pro","price":1199.0},{"id":"XYZ5","name":"Apple Macbook Pro","price":1999.0}]<br>

curl -X GET -H "user-id: testuser1" http://localhost:8080/shop/viewCart<br>
**Output:** {"totalPrice":1998.0,"items":{"XYZ1":{"count":2,"product":{"id":"XYZ1","name":"Apple iPhone 11","price":999.0}}}}<br>

curl -X POST -H "user-id: testuser1" http://localhost:8080/shop/addItem/XYZ1<br>
**Output:** {"XYZ1":{"count":3,"product":{"id":"XYZ1","name":"Apple iPhone 11","price":999.0}}}<br>

curl -X DELETE -H "user-id: testuser1" http://localhost:8080/shop/addItem/XYZ1<br>
**Output:** {"XYZ1":{"count":2,"product":{"id":"XYZ1","name":"Apple iPhone 11","price":999.0}}}<br>

curl -X POST -H "user-id: testuser1" http://localhost:8080/shop/checkout<br>
**Output:** Cart for testuser1 checked out sucessfully. Thanks for Shopping.

**Using Postman**<br>
