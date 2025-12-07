DROP TABLE IF EXISTS land_lot_address;
-- -----------------------------------------------------------------------------------------
-- create
CREATE TABLE land_lot_address
(
    id                  BIGINT  AUTO_INCREMENT PRIMARY KEY COMMENT '지번주소ID',
    manage_no           VARCHAR(25)     NOT NULL COMMENT '관리번호',
    seq                 INTEGER         NOT NULL COMMENT '일련번호',
    legal_dong_code     VARCHAR(10)     NOT NULL COMMENT '법정동코드',
    sido_name           VARCHAR(40)              COMMENT '시도명',
    sigungu_name        VARCHAR(40)              COMMENT '시군구명',
    legal_umd_name      VARCHAR(40)              COMMENT '법정읍면동명',
    legal_ri_name       VARCHAR(40)              COMMENT '법정리명',
    is_mountain         VARCHAR(1)               COMMENT '산여부',
    land_lot_main_no    INT                      COMMENT '지번본번(번지)',
    land_lot_sub_no     INT                      COMMENT '지번부번(호)',
    is_representative   VARCHAR(1)               COMMENT '대표여부'
) COMMENT = '지번주소';
-- -----------------------------------------------------------------------------------------
-- constraint
ALTER TABLE land_lot_address
    ADD CONSTRAINT uq_land_lot_address_domain_key UNIQUE(manage_no, seq);
-- -----------------------------------------------------------------------------------------
-- index
CREATE INDEX idx_land_lot_address_pk ON land_lot_address(manage_no, seq);
CREATE INDEX idx_land_lot_address_apm ON land_lot_address(legal_dong_code, land_lot_main_no, land_lot_sub_no, is_mountain);

