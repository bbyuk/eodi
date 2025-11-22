DROP TABLE IF EXISTS building_address;
-- -----------------------------------------------------------------------------------------
-- create
CREATE TABLE building_address
(
    id                          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '건물주소ID',
    legal_dong_code             VARCHAR(10)             COMMENT '법정동코드',
    sido_name                   VARCHAR(40)             COMMENT '시도명',
    sigungu_name                VARCHAR(40)             COMMENT '시군구명',
    legal_umd_name              VARCHAR(40)             COMMENT '법정읍면동명',
    legal_ri_name               VARCHAR(40)             COMMENT '법정리명',
    is_mountain                 VARCHAR(1)              COMMENT '산여부',
    land_lot_main_no            INT                     COMMENT '지번본번(번지)',
    land_lot_sub_no             INT                     COMMENT '지번부번(호)',
    road_name_code              VARCHAR(12)             COMMENT '도로명코드',
    road_name                   VARCHAR(80)             COMMENT '도로명',
    is_underground              VARCHAR(1)              COMMENT '지하여부',
    building_main_no            INT                     COMMENT '건물본번',
    building_sub_no             INT                     COMMENT '건물부번',
    building_name               VARCHAR(40)             COMMENT '건축물대장건물명',
    building_normalized_name    VARCHAR(40)             COMMENT '정규화건물명',
    building_name_detail        VARCHAR(100)            COMMENT '상세건물명',
    building_manage_no          VARCHAR(25)             COMMENT '건물관리번호',
    umd_seq                     VARCHAR(2)              COMMENT '읍면동일련번호',
    adm_dong_code               VARCHAR(10)             COMMENT '행정동코드',
    adm_dong_name               VARCHAR(20)             COMMENT '행정동명',
    zip_no                      VARCHAR(5)              COMMENT '우편번호',
    zip_no_seq                  VARCHAR(3)              COMMENT '우편번호일련번호',
    change_reason_code          VARCHAR(2)              COMMENT '이동사유코드',
    announcement_date           VARCHAR(8)              COMMENT '고시일자',
    sigungu_building_name       VARCHAR(40)             COMMENT '시군구용건물명',
    is_complex                  VARCHAR(1)              COMMENT '공동주택여부',
    basic_district_no           VARCHAR(5)              COMMENT '기초구역번호',
    has_detail_address          VARCHAR(1)              COMMENT '상세주소여부',
    remark_1                    VARCHAR(15)             COMMENT '비고1',
    remark_2                    VARCHAR(15)             COMMENT '비고2'
) COMMENT = '건물주소';
-- -----------------------------------------------------------------------------------------
-- constraint
ALTER TABLE building_address
    ADD CONSTRAINT uq_building_addr_domain_key UNIQUE(building_manage_no);