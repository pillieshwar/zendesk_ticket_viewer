# zendesk_ticket_viewer

Clone repository and follow the steps below to run the application:

## Table of contents
* [Technologies]
* [Frontend Setup]
* [Backend Setup]


## Technologies
Project is created with:
* Java version: 11
* ReactJs version: 17.0.2
* Spring Boot: 2.6.0
* Maven: 3.6.3
* Junit 5
* Mokito: 4.1
	
## Frontend Setup

* Require npm.
* Goto FRONTEND folder
```
$ cd ../FRONTEND
$ npm install
$ npm start
```
* Application will run on port 3000

## Backend Setup

* Require maven
```
$ cd ../target
$ java -jar demo-0.0.1-SNAPSHOT.jar
```

## Functionality Implemented

* Connected to Zendesk API
* Requested all tickets from Account
* Displayed all the tickets on UI in form of table list
* On Clicking the eye view, individual ticket details are displayed
* Implemented pagination when more than 25 tickets are returned


* Implemented Junit 5 + Mokito Unit test cases
* Error handling when login credentials are incorrect
* Friendly messages are displayed in the backend console
* Tried to maintain code consistency
* Adhered to common coding standards
* Maintained project structure
	
![zendesk_1](https://user-images.githubusercontent.com/21291386/143809544-349c9913-175c-4e8a-9d6f-0da7187cf6e4.png)

![zendesk_2](https://user-images.githubusercontent.com/21291386/143809567-a2fba3be-add0-486a-8345-6e7ee0ff5af6.png)

