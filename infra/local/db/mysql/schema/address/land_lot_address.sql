DROP TABLE IF EXISTS land_lot_address;
-- -----------------------------------------------------------------------------------------
-- create
CREATE TABLE land_lot_address
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '지번주소ID',
    manage_no           VARCHAR(26) NOT NULL              COMMENT '관리번호',
    legal_dong_code     VARCHAR(10) NOT NULL              COMMENT '법정동코드',
    sido_name           VARCHAR(40)                       COMMENT '시도명',
    sigungu_name        VARCHAR(40)                       COMMENT '시군구명',
    umd_name            VARCHAR(40)                       COMMENT '법정읍면동명',
    ri_name             VARCHAR(40)                       COMMENT '법정리명',
    is_mountain         VARCHAR(1)  NOT NULL              COMMENT '산여부',
    land_lot_main_no    INT         NOT NULL              COMMENT '지번본번(번지)',
    land_lot_sub_no     INT         NOT NULL              COMMENT '지번부번(호)',
    road_name_code      VARCHAR(12) NOT NULL              COMMENT '도로명코드',
    is_underground      VARCHAR(1)                        COMMENT '지하여부',
    building_main_no    INT                               COMMENT '건물본번',
    building_sub_no     INT                               COMMENT '건물부번',
    update_reason_code  VARCHAR(10)                       COMMENT '이동사유코드'
) COMMENT = '지번주소';
-- -----------------------------------------------------------------------------------------
-- constraint
ALTER TABLE land_lot_address
    ADD CONSTRAINT uq_land_lot_address_domain_key UNIQUE(manage_no, legal_dong_code, is_mountain, land_lot_main_no, land_lot_sub_no);
-- -----------------------------------------------------------------------------------------
-- index
CREATE INDEX idx_lla_land_match
    ON land_lot_address (
                         legal_dong_code,
                         is_mountain,
                         land_lot_main_no,
                         land_lot_sub_no
        );