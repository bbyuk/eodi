CREATE DATABASE IF NOT EXISTS eodi_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
GRANT ALL PRIVILEGES ON eodi_db.* TO 'eodi_app'@'%';