#!/usr/bin/env bash

docker network inspect eodi-frontend-net >/dev/null 2>&1 || \
  docker network create eodi-frontend-net

docker network inspect eodi-backend-net >/dev/null 2>&1 || \
  docker network create eodi-backend-net