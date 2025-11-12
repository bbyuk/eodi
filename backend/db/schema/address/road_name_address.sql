DROP TABLE IF EXISTS road_name_address;
-- -----------------------------------------------------------------------------------------
-- create
CREATE TABLE road_name_address
(
    id                      BIGINT      AUTO_INCREMENT PRIMARY KEY COMMENT '도로명주소 ID',
    road_name_code          VARCHAR(12) NOT NULL     COMMENT '도로명코드',
    sigungu_code            VARCHAR(5)  NOT NULL     COMMENT '시군구코드',
    road_name_no            VARCHAR(7)  NOT NULL     COMMENT '도로명번호',
    road_name               VARCHAR(80) NOT NULL     COMMENT '도로명',
    eng_road_name           VARCHAR(80)              COMMENT '영문도로명',
    umd_seq                 VARCHAR(2)  NOT NULL     COMMENT '읍면동일련번호',
    sido_name               VARCHAR(40)              COMMENT '시도명',
    sigungu_name            VARCHAR(40)              COMMENT '시군구명',
    umd_gb                  VARCHAR(1)               COMMENT '읍면동구분',
    umd_code                VARCHAR(3)               COMMENT '읍면동코드',
    umd_name                VARCHAR(40)              COMMENT '읍면동명',
    parent_road_name_no     VARCHAR(7)               COMMENT '상위도로명번호',
    parent_road_name        VARCHAR(80)              COMMENT '상위도로명',
    use_yn                  VARCHAR(1)               COMMENT '사용여부',
    change_history_reason   VARCHAR(1)               COMMENT '변경이력사유',
    change_history_info     VARCHAR(14)              COMMENT '변경이력정보',
    eng_sido_name           VARCHAR(40)              COMMENT '영문시도명',
    eng_sigungu_name        VARCHAR(40)              COMMENT '영문시군구명',
    eng_umd_name            VARCHAR(40)              COMMENT '영문읍면동명',
    announcement_date       VARCHAR(8)               COMMENT '고시일자',
    expiration_date         VARCHAR(8)               COMMENT '말소일자'
) COMMENT = '도로명주소';
-- -----------------------------------------------------------------------------------------
-- constraint
ALTER TABLE road_name_address
    ADD CONSTRAINT uq_road_name_addr_rnc_us UNIQUE(road_name_code, umd_seq);