#!/bin/bash
docker build -t book-service . --build-arg JAR_FILE=target/book-service-0.0.1-SNAPSHOT.jar
docker run --rm book-service