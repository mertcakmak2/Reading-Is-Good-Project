
# Reading-Is-Good

Customers order a book with this project.

Apache Kafka is used for stock consistency in this project.


## Urls

[Swagger](http://localhost:8080/api)

[Kafdrop](http://localhost:9000)

[Jaeger Ui](http://localhost:16686)

[Postman](https://www.getpostman.com/collections/9c58e7c0127a8ab3c119)
## Run Project on Docker

Build.

```bash
  mvn clean package
```

Run project.
```bash
  docker-compose -f docker-compose-prod.yaml up -d --build
```
  
## Tech Stack
![techstack](https://user-images.githubusercontent.com/21373505/203794812-4f43ac44-06b7-46fc-ada7-35484ccdd874.png)
  
## Scenario of Order a Book (Solution of Stock Consistency)
![order-book-scenario](https://user-images.githubusercontent.com/21373505/203789846-58b452b9-d7e4-4f65-88f4-1315590b7f9f.png)

## Http Tracing with Jaeger
![Screenshot_25](https://user-images.githubusercontent.com/21373505/203793098-d61f097e-6858-44bf-823f-24171a64856d.png)

## Credentials

- testuser
- testpassword

  
  
