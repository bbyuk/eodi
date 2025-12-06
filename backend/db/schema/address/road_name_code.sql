DROP TABLE IF EXISTS road_name_code;
-- -----------------------------------------------------------------------------------------
-- create
CREATE TABLE road_name_code
(
    id                  BIGINT  AUTO_INCREMENT PRIMARY KEY  COMMENT '지번주소ID',
    road_name_code      VARCHAR(12) NOT NULL                COMMENT '도로명코드',
    road_name           VARCHAR(80)                         COMMENT '도로명',
    eng_road_name       VARCHAR(80)                         COMMENT '도로명로마자',
    umd_seq             VARCHAR(2) NOT NULL                 COMMENT '읍면동일련번호',
    sido_name           VARCHAR(20)                         COMMENT '시도명',
    eng_sido_name       VARCHAR(40)                         COMMENT '시도 로마자',
    umd_name            VARCHAR(20)                         COMMENT '읍면동명',
    eng_umd_name        VARCHAR(40)                         COMMENT '읍면동로마자',
    umd_type            VARCHAR(1)                          COMMENT '읍면동구분',
    umd_code            VARCHAR(3)                          COMMENT '읍면동코드',
    enabled             VARCHAR(1)                          COMMENT '사용여부',
    change_code         VARCHAR(1)                          COMMENT '변경사유',
    entrance_date       VARCHAR(8)                          COMMENT '고시일자',
    revocation_date     VARCHAR(8)                          COMMENT '말소일자'
) COMMENT = '도로명코드';

-- -----------------------------------------------------------------------------------------
-- constraint
ALTER TABLE road_name_code
    ADD CONSTRAINT uq_road_name_code_domain_key UNIQUE(road_name_code, umd_seq);
-- -----------------------------------------------------------------------------------------
-- index
CREATE INDEX idx_road_name_code_pk ON road_name_code(road_name_code, umd_seq);