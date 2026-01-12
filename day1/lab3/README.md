<p align="center">
<img src="https://www.docker.com/wp-content/uploads/2022/03/Moby-logo.png" width="420" />
</p>

## Run Java Spring Boot App in a Container

This lab demonstrates how to containerize and run a Java Spring Boot application using Docker. The steps include cloning the application, writing a Dockerfile, building a Docker image, running a container, and testing the application.

---

## Step 1: Clone the Application Code

Clone the source code from GitHub:

```bash
git clone https://github.com/Ibrahim-Adel15/Docker-1.git
cd Docker-1
```

---

## Step 2: Write Dockerfile

Create a `Dockerfile` with the following content:

```dockerfile
FROM maven:3.8.5-openjdk-17
WORKDIR /lab3
COPY . .
RUN mvn package
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080

```

---

## Step 3: Build Docker Image

Build the Docker image with the tag `lab3`:

```bash
docker build -t lab3 .
```

![Build Image](Screenshots/build.png)

---

## Step 4: Run Container

Run a container named `container1` from the `lab3` image:

```bash
docker run -d -p 8089:8080 --name container1 lab3
```

![Run Container](Screenshots/run.png)

---

## Step 5: Test the Application

Test the application by accessing it in your browser or using curl:

```bash
curl http://localhost:8089
```

Or open your browser and navigate to: `http://localhost:8089`

![Test App](Screenshots/test.png)

---

## Step 6: Stop and Delete the Container

Stop and remove the container:

```bash
docker stop container1
docker rm container1
```

![Stop Container](Screenshots/stop.png)

---

## Summary

The complete Docker workflow for this project:
* Clone the Spring Boot application from GitHub
* Write a Dockerfile with Maven and Java 17 base image
* Build the Docker image
* Run the container and expose port 8080
* Test the application
* Stop and delete the container
