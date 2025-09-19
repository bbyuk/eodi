-- 공통: 문자셋
SET NAMES utf8mb4;
SET time_zone = '+09:00';

DROP TABLE IF EXISTS real_estate_lease;

-- -----------------------------------------------------------------------------------------
-- create
CREATE TABLE real_estate_lease
(
    id                      BIGINT  AUTO_INCREMENT PRIMARY KEY COMMENT '임대차 실거래가 ID',
    region_id               BIGINT  NOT NULL COMMENT '대상지역 법정동 ID',
    legal_dong_name         VARCHAR(50) COMMENT '법정동 명',
    contract_date           DATE    COMMENT '계약일',
    contract_start_month    DATE    COMMENT '계약시작 월',
    contract_end_month      DATE    COMMENT '계약종료 월',
    deposit                 BIGINT  COMMENT '보증금',
    monthly_rent            INT     COMMENT '월세',
    previous_deposit        BIGINT     COMMENT '이전 계약 보증금',
    previous_monthly_rent   INT     COMMENT '이전 계약 월세',
    build_year              INTEGER COMMENT '건축년도',
    net_leasable_area       DECIMAL(10, 4) NOT NULL COMMENT '전용면적',
    housing_type            ENUM('AP', 'MH', 'DT', 'MU', 'OF', 'O') NOT NULL DEFAULT 'O' COMMENT '주택유형',
    target_name             VARCHAR(100) NOT NULL COMMENT '대상명',
    floor                   INTEGER COMMENT '층',
    created_at              TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at              TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시'
) COMMENT = '부동산 임대차 실거래가';
-- -----------------------------------------------------------------------------------------
-- constraint
ALTER TABLE real_estate_lease
    ADD CONSTRAINT fk_real_estate_lease__region_id
        FOREIGN KEY (region_id)
            REFERENCES legal_dong(id)
            ON UPDATE RESTRICT
            ON DELETE RESTRICT;
-- -----------------------------------------------------------------------------------------
-- index
CREATE INDEX idx_real_estate_lease__ri ON real_estate_lease(region_id);