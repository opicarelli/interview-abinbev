# ANHEUSER-BUSCH INBEV: CODE CHALLENGE

## What to do?

Your mission if you choose to accept is to build an API and simple database to handle the products of the ABInBev ecommerce. 

To build the API, the following requirements are mandatory:
 *  ✓ Should be able to save a product considering the fields name, description, price and brand.
 *  ✓ Should be able delete the product.
 *  ✓ Should be able to update the product name.
 *  ✓ Should be able to search a product by name.
 *  ✓ Should be able to search a product by id.
Technology Rules:
 *  ✓ Java 8 (minimum)
 *  ✓ Spring
Bonuses:
 *  ✓ Security: OAUTH/JWT;
 * X Database: NoSQL database;
 *  ✓ Tests
 *  ✓ Docker
 *  ✓ Microservices
 *  ✓ Swagger documentation
 *  ✓ Orderable: create an endpoint that can return an ordered list by name.


## Getting start

This application is based on Spring Boot. Focused on microservice, it can be easily deployed in a docker container.

Requirements
 * JDK 14
 * Maven 3.6

Bonus
 * Docker

### How to build (maven)
```
mvn clean install
```

### Hot to run
```
java -jar target\challenge-0.0.1-SNAPSHOT.jar
```

#### Docker

Before building the docker image run maven step. Then, build the image with:

```
docker build -f src/main/docker/Dockerfile -t opicarelli/abinbevchallenge .
```

Then run the container using:

```
docker run -i --rm -p 8080:8080 opicarelli/abinbevchallenge
```

### Swagger

Access http://localhost:8080/challenge/v1/swagger-ui.html

### Database H2

Access http://localhost:8080/challenge/v1/h2-console

```
connection: jdbc:h2:mem:challenge;DB_CLOSE_ON_EXIT=FALSE
user: sa
password: password
```

### Security

For now, the Authorization to be included in the header of each request is based on a default username (admin) and password (password).

The application is using JWT to secure the application based on Spring Security.

### Microservice health check

Access http://localhost:8080/challenge/v1/actuator

### Tests

It was created an Integration Test called ProductApiIntegrationTest. For this simple case it was not necessary to create some UNIT test to isolate validations.

#### Postman

 1. Import Environment file "ABInBev.postman_environment.json"
 1. Import Collection file "ABInBev Challenge.postman_collection.json"
 1. Run Endpoint /auth to set JWT to environment Authorization
 1. Run any Endpoint as you want

### Know issues

 * Based on principles of container the best way is "the size of your created container, at the end the size will be the same". I would like to say that:
    * Database should be in another container, not in a memory or file inside the container like it is with H2. It was just a PoC.
    * Cache request should be in another container, using Redis for example, not in a Hash Table memory like it is with Caffeine.
 * The authentication could be managed in other ways like:
    * Managed by a repository of user and roles, not using a mock user (admin:password)
    * Or better, managed by a gateway responsible to check authorization

PS: This issues was not be able to be solved in the meantime of developing this challenge.


### Development logs

 * \#1 Setup and mandatory exercise
 * \#2 Orderable endpoint
 * \#3 Docker
 * \#4 Microservice + Cache
 * \#5 Security with JWT

```
$ git log
commit d0f0b73f3e6a8814edae311170d7ba9ea4cae216 (HEAD -> master)
Author: opicarelli <marcos.prado@eldorado.org.br>
Date:   Wed Jul 29 13:08:55 2020 -0300

    #5 Adding JWT with Spring Security

commit 98ccf597fdac90fd50650b55dd527391dee184d3
Author: opicarelli <marcos.prado@eldorado.org.br>
Date:   Tue Jul 28 22:10:00 2020 -0300

    #2 Adding cache caffeine (hash map) strategy

commit 8a2c1376b4d2888b9efd7cf54689d84420d16a8b
Author: opicarelli <marcos.prado@eldorado.org.br>
Date:   Tue Jul 28 21:59:18 2020 -0300

    #4 Helth check for microservices environment

commit 07a8773dbb6766d3207a57af2d84da4af5a6e543
Author: opicarelli <marcos.prado@eldorado.org.br>
Date:   Tue Jul 28 21:55:18 2020 -0300

    #3 Adding Dockerfile

commit dd7841cc69df7bdf15033766e7afeced382d3fd2
Author: opicarelli <marcos.prado@eldorado.org.br>
Date:   Tue Jul 28 21:30:08 2020 -0300

    #2 Search by filter, paginate and order the result

commit 6c11a62437151e5c3eb6de603a4fcbdf22bcc886
Author: opicarelli <marcos.prado@eldorado.org.br>
Date:   Tue Jul 28 17:58:42 2020 -0300

    #1 Product: Service, Repository and Integration Test

commit e8381135815f677b75d29a92151f01de8369b89f
Author: opicarelli <marcos.prado@eldorado.org.br>
Date:   Tue Jul 28 12:57:52 2020 -0300

    #1 API Document and ExceptionHandler

commit 96cfda144783227af6b2a7cb9f640a349e448753
Author: opicarelli <marcos.prado@eldorado.org.br>
Date:   Mon Jul 27 22:37:23 2020 -0300

    #1 Update to Java 14 and API Controller

commit 7a62c762811355d242fea9048d22f31dae179582
Author: opicarelli <marcos.prado@eldorado.org.br>
Date:   Mon Jul 27 21:50:50 2020 -0300

    #1 Open API + Swagger UI

commit 0981d900b1e7386c6d20f46cfbe5c8eda4578635
Author: opicarelli <marcos.prado@eldorado.org.br>
Date:   Mon Jul 27 21:36:11 2020 -0300

    #1 Setup product and brand model

    Starting model with SQL database

commit 2dc2c37439591b56713619a91f8b65495aa340a6
Author: opicarelli <marcos.prado@eldorado.org.br>
Date:   Mon Jul 27 20:29:14 2020 -0300

    #1 Setup project

    Spring boot starters: web, jpa, cache, redis e validation
```