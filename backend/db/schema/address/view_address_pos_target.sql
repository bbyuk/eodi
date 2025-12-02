CREATE VIEW address_pos_target
AS
SELECT DISTINCT road_name_code,
                legal_dong_code,
                is_underground,
                building_main_no,
                building_sub_no
FROM building_address
UNION
SELECT DISTINCT
FROM building_address ba
         JOIN land_lot_address lla
              ON ba.road_name_code = lla.road_name_code
                  AND ba.is_underground = lla.is_underground
                  AND ba.building_main_no = lla