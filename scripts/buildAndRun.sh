#!/bin/bash

docker build -t oauth-server:local .

docker rm -f -v oauth-server

docker network create jarand-net || true

docker run -dt --name oauth-server -p 8080:8080 --network jarand-net \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://oauth-server-db:5432/oauth-server-db \
  -e SPRING_DATASOURCE_USERNAME=oauth-server-dbo \
  -e SPRING_DATASOURCE_PASSWORD=postgres \
  oauth-server:local
