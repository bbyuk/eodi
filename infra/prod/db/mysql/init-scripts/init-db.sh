#!/bin/bash
set -euo pipefail

# =========================
# env 로드 (host)
# =========================
set -a
source /opt/eodi/env/db.env
set +a

# =========================
# 필수 값 체크
# =========================
: "${MYSQL_DATABASE:?}"
: "${MYSQL_USER:?}"
: "${MYSQL_PASSWORD:?}"
: "${MYSQL_ROOT_PASSWORD:?}"

# =========================
# docker compose 설정
# =========================
COMPOSE_DIR=/opt/eodi/compose
MYSQL_SERVICE=mysql

cd "$COMPOSE_DIR"

# =========================
# 00-init.sql
# =========================
sed \
  -e "s/__MYSQL_DATABASE__/${MYSQL_DATABASE}/g" \
  -e "s/__MYSQL_USER__/${MYSQL_USER}/g" \
  -e "s/__MYSQL_PASSWORD__/${MYSQL_PASSWORD}/g" \
  /opt/eodi/mysql/init-scripts/00-init.sql \
| docker compose exec -T ${MYSQL_SERVICE} \
    mysql -u root -p"${MYSQL_ROOT_PASSWORD}"

# =========================
# 10-schema.sql
# =========================
sed \
  -e "s/__MYSQL_DATABASE__/${MYSQL_DATABASE}/g" \
  /opt/eodi/mysql/init-scripts/10-schema.sql \
| docker compose exec -T ${MYSQL_SERVICE} \
    mysql -u root -p"${MYSQL_ROOT_PASSWORD}"

echo "eodi DB init completed"
