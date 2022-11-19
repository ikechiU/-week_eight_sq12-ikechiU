# ActivityTracker RESTful Web Service

[![Java Build](https://img.shields.io/badge/Java-Spring%20Boot-orange)](https://spring.io/projects/spring-boot) [![Spring JPA](https://img.shields.io/badge/Spring-JPA-blue)](https://spring.io/projects/spring-data-jpa) [![Model Mapper ](https://img.shields.io/badge/ModelMapper-%20passing-green)](http://modelmapper.org/) [![Postgres](https://img.shields.io/badge/Postgres-%20SQL-blue)](https://www.postgresql.org/) [![Swagger](https://img.shields.io/badge/Swagger-passing-green)](https://swagger.io/)

## Activity Tracker Problem Statement

Jane is a student who is bad with time management. She needs an application where she can log the activities she needs
to do for the day and the time allocated to complete these activities. To help Jane, you are to build an activity
tracking application (API) where she can have all the functionalities she wants.

### User Story

* Login
* Create a task
* A task should be made up of:
*  Title
*  Description
*  Status
*  Created At
*  Updated At
*  Completed At
* A user should be able to view all tasks.
* A user should be able to view a particular task
* A user should be able to view all pending tasks
* A user should be able to view all tasks done
* A user should be able to view all in progress tasks
* A user should be able to move a task to pending task, therefore marking the field below of a task as incomplete.
* A user should be able to move a task to done task, therefore marking the field below of a task as done.
*  Done
* A user should be able to edit the following fields of a task
*  Title
*  Description
*  Delete a task

## Libraries

* [Spring Boot Web](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web)
* [Model Mapper](http://modelmapper.org/)
* [Jackson dataformat](https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-xml)
* [Spring JPA](https://spring.io/projects/spring-data-jpa)
* [Postgres](https://www.postgresql.org/)
* [Swagger](https://swagger.io/)

## Other projects

https://github.com/ikechiU?tab=repositories

## Author

Ikechi Ucheagwu

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.5/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.5/maven-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#using.devtools)
* [Validation](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#io.validation)

### Guides

The following guides illustrate how to use some features concretely:

* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)

