DROP TABLE IF EXISTS road_name_address;
-- -----------------------------------------------------------------------------------------
-- create
CREATE TABLE road_name_address
(
    id                          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '도로명주소 ID',
    manage_no                   VARCHAR(26) NOT NULL COMMENT '관리번호',
    legal_dong_code             VARCHAR(10) COMMENT '법정동코드',
    sido_name                   VARCHAR(40) COMMENT '시도명',
    sigungu_name                VARCHAR(40) COMMENT '시군구명',
    umd_name                    VARCHAR(40) COMMENT '법정읍면동명',
    ri_name                     VARCHAR(40) COMMENT '법정리명',
    is_mountain                 VARCHAR(1) COMMENT '산여부',
    land_lot_main_no            INT COMMENT '지번본번',
    land_lot_sub_no             INT COMMENT '지번부번',
    road_name_code              VARCHAR(12) NOT NULL COMMENT '도로명코드',
    road_name                   VARCHAR(80) COMMENT '도로명',
    is_underground              VARCHAR(1) NOT NULL COMMENT '지하여부',
    building_main_no            INT NOT NULL COMMENT '건물본번',
    building_sub_no             INT NOT NULL COMMENT '건물부번',
    adm_dong_code               VARCHAR(60) COMMENT '행정동코드',
    adm_dong_name               VARCHAR(60) COMMENT '행정동명',
    basic_district_no           VARCHAR(5) COMMENT '기초구역번호',
    before_road_name_address    VARCHAR(400) COMMENT '이전도로명주소',
    effect_start_date           VARCHAR(8) COMMENT '효력발생일',
    is_multi                    VARCHAR(1) COMMENT '공동주택구분',
    update_reason_code          VARCHAR(2) COMMENT '이동사유코드',
    building_name               VARCHAR(400) COMMENT '건축물대장 건물명',
    sigungu_building_name       VARCHAR(400) COMMENT '시군구용 건물명',
    remark                      VARCHAR(200) COMMENT '비고',
    x_pos                       DECIMAL(15,6) COMMENT 'X좌표',
    y_pos                       DECIMAL(15,6) COMMENT 'Y좌표'
) COMMENT = '도로명주소';
-- -----------------------------------------------------------------------------------------
-- constraint
ALTER TABLE road_name_address
    ADD CONSTRAINT uq_road_name_addr_domain_key UNIQUE(manage_no, road_name_code, is_underground, building_main_no, building_sub_no);

-- -----------------------------------------------------------------------------------------
-- index
CREATE INDEX idx_rna_road_match
    ON road_name_address (
                          road_name_code,
                          is_underground,
                          building_main_no,
                          building_sub_no
        );

CREATE INDEX idx_rna_land_match
    ON road_name_address (
                          legal_dong_code,
                          is_mountain,
                          land_lot_main_no,
                          land_lot_sub_no
        );