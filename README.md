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
- Spring Source Tool Suite 4
- Spring Boot 2.4.1
- Java 8
- Maven 3.6.3

## API Features

|   API-NAME	       |  REST METHOD |   ENDPOINT   | REQUEST HEADER  |
|:------------------:|:-------------|:-----------------------:|:--------------:|
| Available Products |      GET     | **/shop/availableProducts** |     |
| View Cart          |      GET     | **/shop/viewCart**       | userId=testuser1      |
| Add Item           |      POST    | **/shop/addItem/XYZ1**   | userId=testuser1      |
| Remove Item        |     DELETE   | **/shop/removeItem/XYZ2**| userId=testuse1      |
| View Cart          |      POST    | **/shop/checkout**       | userId=testuse1      |

## Build Process
**Using the Maven Plugin**<br>
$ mvn spring-boot:run

**Using Executable Jar**<br>
To create an executable jar run:<br>
$ mvn clean package<br>
To run that application, use the java -jar command, as follows:

$ java -jar target/shopping-cart-0.0.1-SNAPSHOT.jar<br>
To exit the application, press ctrl-c.

**Using STS**<br>
Start the Spring boot Application using STS Run As "Spring Boot App"

## Testing

