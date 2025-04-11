# APIAprilFiniance2025 Iulian Peca.

## System requirments: 

The code is developed using:
- Java version: 21.0.6 
- Linux Ubuntu 24.04.2 LTS
- Spring Boot 3.4.4 
- Apache Maven 3.9.9 
- maven-3.9.9 
- Agnostic of IDE (I used eclipse Version: 2024-09 (4.33.0) Build id: 20240905-0614)
- Postman (to check the rest API) 

The testing is done using Junit5 and test, building is with maven.  


Normally, the development can be done with the H2 in-memory database. 
However, to make the development/test set-up closer to the real world usage, I replaced the H2 with docker test contianer. 

**To be able to run the unit testing, please have docker install on the vm:** 

```bash
> docker version
Client: Docker Engine - Community
 Version:           27.3.1
 API version:       1.47
 Go version:        go1.22.7
 Git commit:        ce12230
 Built:             Fri Sep 20 11:41:00 2024
 OS/Arch:           linux/amd64
 Context:           default
```

Steps to run and play with the application:

```bash
#clone the repository
> clone from https://github.com/pkiulian/APIAprilFiniance2025
```

```bash
#navigate to the right location
> cd APIAprilFiniance2025/exchange/
```

```bash
# build the application
> mvn clean install 
```

```bash
# shuts down and cleans up everything started by docker-compose up
> docker-compose down --volumes
```

```bash
# check and make sure there is no other docker container running
> docker ps
```

```bash
# build images if needed and run (in background) containers definied in docker-compose.yml
> docker-compose up -d --build
```

Now the application is ready for manual testing.

Open Postman and insert two accounts: 

```bash
POST: http://localhost:8080/api/finance/createAccount
Body raw: 
{
  "ownerId": 1,
  "currency": "EUR",
  "balance": 1000.0
}
```
```bash
POST: http://localhost:8080/api/finance/createAccount
Body raw: 
{
  "ownerId": 2,
  "currency": "EUR",
  "balance": 2000.0
}
```
Received Code for the above should be 201 (Created)

Using Postman execute one transfer: 
```bash
POST: http://localhost:8080/api/finance/transfer
Body:
{
  "debitAccountId": 1,
  "creditAccountId": 2,
  "amount": 155.0,
  "debitCurrency": "EUR",
  "creditCurrency": "EUR"
}
```

Now Check on browser: http://localhost:8080/api/finance/account/1 it should look like this: 
```bash
accountId	1
ownerId	1
currency	"EUR"
balance	845.0
version	1
```
and http://localhost:8080/api/finance/account/2:
```bash
accountId	2
ownerId	2
currency	"EUR"
balance	2155.0
version	1
```





