DROP TABLE IF EXISTS road_name_address;
-- -----------------------------------------------------------------------------------------
-- create
CREATE TABLE road_name_address
(
    id                      BIGINT      AUTO_INCREMENT PRIMARY KEY COMMENT '도로명주소 ID',
    manage_no               VARCHAR(25) NOT NULL     COMMENT '관리번호',
    road_name_code          VARCHAR(12) NOT NULL     COMMENT '도로명코드',
    umd_seq                 VARCHAR(2)  NOT NULL     COMMENT '읍면동일련번호',
    is_underground          VARCHAR(1)               COMMENT '지하여부',
    building_main_no        INTEGER                  COMMENT '건물본번',
    building_sub_no         INTEGER                  COMMENT '건물부번',
    basic_district_no       VARCHAR(5)               COMMENT '기초구역번호',
    has_detail_address      VARCHAR(1)               COMMENT '상세주소부여 여부'
) COMMENT = '도로명주소';
-- -----------------------------------------------------------------------------------------
-- constraint
ALTER TABLE road_name_address
    ADD CONSTRAINT uq_road_name_addr_domain_key UNIQUE(manage_no);

-- -----------------------------------------------------------------------------------------
-- index
CREATE INDEX idx_road_name_address_pk ON road_name_address(manage_no);
CREATE INDEX idx_road_name_address_fk ON road_name_address(road_name_code, umd_seq);