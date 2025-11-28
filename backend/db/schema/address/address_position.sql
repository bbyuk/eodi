DROP TABLE IF EXISTS address_position;
-- -----------------------------------------------------------------------------------------
-- create
CREATE TABLE address_position
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '주소위치정보ID',
    sigungu_code        VARCHAR(5)      COMMENT '시군구코드',
    entrance_seq        VARCHAR(10)     COMMENT '출입구일련번호',
    legal_dong_code     VARCHAR(10)     COMMENT '법정동코드',
    sido_name           VARCHAR(40)     COMMENT '시도명',
    sigungu_name        VARCHAR(40)     COMMENT '시군구명',
    umd_name            VARCHAR(40)     COMMENT '읍면동명',
    road_name_code      VARCHAR(12)     COMMENT '도로명코드',
    road_name           VARCHAR(80)     COMMENT '도로명',
    is_underground      VARCHAR(1)      COMMENT '지하여부',
    building_main_no    INT             COMMENT '건물본번',
    building_sub_no     INT             COMMENT '건물부번',
    building_name       VARCHAR(40)     COMMENT '건물명',
    zip_no              VARCHAR(5)      COMMENT '우편번호',
    building_type       VARCHAR(100)    COMMENT '건물용도분류',
    is_building_group   VARCHAR(1)      COMMENT '건물군여부',
    x_pos               DECIMAL(15, 6)  COMMENT 'X좌표',
    y_pos               DECIMAL(15, 6)  COMMENT 'Y좌표'
) COMMENT = '주소위치정보';
-- -----------------------------------------------------------------------------------------
-- constraint
ALTER TABLE address_position
    ADD CONSTRAINT uq_building_addr_domain_key UNIQUE(
                                                      road_name_code,
                                                      is_underground,
                                                      building_main_no,
                                                      building_sub_no,
                                                      legal_dong_code
                                                     )
-- -----------------------------------------------------------------------------------------
-- index
CREATE INDEX idx_address_position_key ON address_position(road_name_code, is_underground, land_lot_main_no, land_lot_sub_no, legal_dong_code);