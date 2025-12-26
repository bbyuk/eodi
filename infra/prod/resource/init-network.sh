#!/bin/bash
set -euo pipefail

NETWORKS=(
  eodi-frontend-net
)

for NET in "${NETWORKS[@]}"; do
  if docker network inspect "$NET" >/dev/null 2>&1; then
    echo "✔ network already exists: $NET"
  else
    echo "➕ creating network: $NET"
    docker network create "$NET"
  fi
done
