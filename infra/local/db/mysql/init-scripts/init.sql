SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

CREATE DATABASE IF NOT EXISTS eodi_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
GRANT ALL PRIVILEGES ON eodi_db.* TO 'eodi_app'@'%';
USE eodi_db;
-- --------------------------------------------------------

-- address
SOURCE /docker-entrypoint-initdb.d/schema/address/land_lot_address.sql;
SOURCE /docker-entrypoint-initdb.d/schema/address/road_name_address.sql;
SOURCE /docker-entrypoint-initdb.d/schema/address/unmapped.sql;

-- deal
SOURCE /docker-entrypoint-initdb.d/schema/deal/real_estate_lease.sql;
SOURCE /docker-entrypoint-initdb.d/schema/deal/real_estate_sell.sql;

-- legal_dong
SOURCE /docker-entrypoint-initdb.d/schema/legal_dong/legal_dong.sql;
SOURCE /docker-entrypoint-initdb.d/schema/legal_dong/legal_dong_adjacency.sql;

-- spring_batch
SOURCE /docker-entrypoint-initdb.d/schema/spring_batch/schema-mysql.sql;
