DROP TABLE IF EXISTS land_lot_address;
-- -----------------------------------------------------------------------------------------
-- create
CREATE TABLE land_lot_address
(
    id                  BIGINT  AUTO_INCREMENT PRIMARY KEY COMMENT '지번주소ID',
    legal_dong_code     VARCHAR(10)     NOT NULL COMMENT '법정동코드',
    sido_name           VARCHAR(40)              COMMENT '시도명',
    sigungu_name        VARCHAR(40)              COMMENT '시군구명',
    legal_umd_name      VARCHAR(40)              COMMENT '법정읍면동명',
    legal_ri_name       VARCHAR(40)              COMMENT '법정리명',
    is_mountain         VARCHAR(1)               COMMENT '산여부',
    land_lot_main_no    INT                      COMMENT '지번본번(번지)',
    land_lot_sub_no     INT                      COMMENT '지번부번(호)',
    land_lot_seq        BIGINT                   COMMENT '지번일련번호',
    road_name_code      VARCHAR(12)              COMMENT '도로명코드',
    is_underground      VARCHAR(1)               COMMENT '지하여부',
    building_main_no    INT                      COMMENT '건물본번',
    building_sub_no     INT                      COMMENT '건물부번',
    change_reason_code  VARCHAR(2)               COMMENT '이동사유코드'
) COMMENT = '지번주소';
-- -----------------------------------------------------------------------------------------
-- constraint
ALTER TABLE land_lot_address
    ADD CONSTRAINT uq_land_lot_address_domain_key UNIQUE(road_name_code, is_mountain, building_main_no, building_sub_no, land_lot_seq);