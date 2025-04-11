# APIAprilFiniance2025 Iulian Peca.

## System requirments: 

The code is developed using:
- Java version: 21.0.6 
- Linux Ubuntu 24.04.2 LTS
- Spring Boot 3.4.4 
- Apache Maven 3.9.9 
- Agnostic of IDE (I used eclipse Version: 2024-09 (4.33.0) Build id: 20240905-0614)

The testing is done using Junit5 and test, building is with maven.  


Normally, the development can be done with the H2 in-memory database. 
However, to make the development/test set-up closer to the real world usage, I replaced the H2 with docker test contianer. 

To be able to run the unit testing, please have docker install on the vm: 

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
> git clone git@github.com:pkiulian/APIAprilFiniance2025.git
```

```bash
# build the application
> mvn clean install 
```

