## Build and run it with Docker

* Pre-requirement: Docker already installed locally
* Download the project.
* If needed unzip it.
* Open a terminal (or cmd) window and go to the folder with the pom.xml (the root of the project)
* If on LINUX run:
  ```
  ./build_with_docker.sh  
  ```
  and then:
  ```
  ./run_with_docker.sh  
  ```
Make sure the scripts have execution rights.
* If on OTHER OPERATING SYSTEMS simply use the Docker commands you find in the .sh scripts

## Build and run with local JDK/Maven

* Pre-requirement: JDK and Maven installed locally
* Download the project.
* If needed unzip it.
* Open a terminal (or cmd) window and go to the folder with the pom.xml
  ```
  mvn spring-boot:run -DskipTests
  ```

## Run test cases
* With Docker
```
docker run -it --rm --name my-maven-project -v "$(pwd)":/usr/src/mymaven -w /usr/src/mymaven maven:3-adoptopenjdk-11-openj9 mvn test
```
* With local JDK/Maven
```
mvn test
```