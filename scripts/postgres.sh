#!/bin/bash

docker rm -f -v oauth-server-db

docker network create jarand-net || true

docker run -dt --name oauth-server-db -p 5432:5432 --network jarand-net \
  -e POSTGRES_DB=oauth-server-db \
  -e POSTGRES_USER=oauth-server-dbo \
  -e POSTGRES_PASSWORD=postgres \
  postgres:14
