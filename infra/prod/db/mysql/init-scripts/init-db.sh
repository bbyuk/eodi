#!/bin/bash
set -euo pipefail

# env 로드
set -a
source /opt/eodi/env/db.env
set +a

# 필수 값 체크
: "${MYSQL_DATABASE:?}"
: "${MYSQL_USER:?}"
: "${MYSQL_PASSWORD:?}"
: "${MYSQL_ROOT_PASSWORD:?}"

MYSQL="mysql -u ${MYSQL_ROOT_USER:-root} -p${MYSQL_ROOT_PASSWORD}"

# 00-init.sql 치환 실행
sed \
  -e "s/__MYSQL_DATABASE__/${MYSQL_DATABASE}/g" \
  -e "s/__MYSQL_USER__/${MYSQL_USER}/g" \
  -e "s/__MYSQL_PASSWORD__/${MYSQL_PASSWORD}/g" \
  /opt/eodi/mysql/init-scripts/00-init.sql \
| $MYSQL

# 10-schema.sql 치환 실행
sed \
  -e "s/__MYSQL_DATABASE__/${MYSQL_DATABASE}/g" \
  /opt/eodi/mysql/init-scripts/10-schema.sql \
| $MYSQL

echo "eodi DB init completed"
