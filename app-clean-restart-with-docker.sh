#!/bin/bash

./mvnw clean spotless:apply package -DskipTests && docker compose down --rmi all --volumes && docker compose build --no-cache && docker compose up
