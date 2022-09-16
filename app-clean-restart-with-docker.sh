#!/bin/bash

docker stop $(docker ps -aq) && docker rm $(docker ps -aq)
./mvnw clean spotless:apply package -DskipTests && docker compose down --rmi all --volumes && docker compose build --no-cache && docker compose up

# this comment is unimportant
