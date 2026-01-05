-- /opt/eodi/mysql/init/10-schema.sql

USE __MYSQL_DATABASE__;

-- address
SOURCE /opt/eodi/mysql/schema/address/land_lot_address.sql;
SOURCE /opt/eodi/mysql/schema/address/road_name_address.sql;
SOURCE /opt/eodi/mysql/schema/address/unmapped.sql;

-- deal
SOURCE /opt/eodi/mysql/schema/deal/real_estate_lease.sql;
SOURCE /opt/eodi/mysql/schema/deal/real_estate_sell.sql;

-- legal_dong
SOURCE /opt/eodi/mysql/schema/legal_dong/legal_dong.sql;
SOURCE /opt/eodi/mysql/schema/legal_dong/legal_dong_adjacency.sql;

-- spring_batch
SOURCE /opt/eodi/mysql/schema/spring_batch/schema-mysql.sql;
