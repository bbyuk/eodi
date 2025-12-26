#!/usr/bin/env bash
set -e

VOLUMES=(
  mysql-data
)

for VOLUME in "${VOLUMES[@]}"; do
  docker volume inspect "$VOLUME" >/dev/null 2>&1 || \
    docker volume create "$VOLUME"
done