DROP TABLE IF EXISTS real_estate_lease;

-- -----------------------------------------------------------------------------------------
-- create
CREATE TABLE real_estate_lease
(
    id                      BIGINT  NOT NULL AUTO_INCREMENT COMMENT '임대차 실거래가 ID',
    region_id               BIGINT  NOT NULL COMMENT '대상지역 법정동 ID',
    land_lot_main_no        INT     COMMENT '지번본번',
    land_lot_sub_no         INT     COMMENT '지번부번',
    legal_dong_name         VARCHAR(50) COMMENT '법정동 명',
    contract_date           DATE    COMMENT '계약일',
    contract_start_month    INT     COMMENT '계약시작 월',
    contract_end_month      INT     COMMENT '계약종료 월',
    deposit                 INT     COMMENT '보증금',
    monthly_rent            INT     COMMENT '월세',
    previous_deposit        INT     COMMENT '이전 계약 보증금',
    previous_monthly_rent   INT     COMMENT '이전 계약 월세',
    total_floor_area        DECIMAL(10, 4) COMMENT '연면적',
    build_year              INTEGER COMMENT '건축년도',
    net_leasable_area       DECIMAL(10, 4) COMMENT '전용면적',
    housing_type            ENUM('AP', 'MH', 'DT', 'MU', 'OF', 'O') NOT NULL DEFAULT 'O' COMMENT '주택유형',
    target_name             VARCHAR(100) COMMENT '대상명',
    floor                   INTEGER COMMENT '층',
    use_rr_right            TINYINT(1) NOT NULL DEFAULT 0 COMMENT '갱신계약 청구권 사용여부',
    created_at              TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at              TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (id, contract_date)
) COMMENT = '부동산 임대차 실거래가'
PARTITION BY RANGE (YEAR(contract_date)*100 + MONTH(contract_date)) (
    PARTITION p202409 VALUES LESS THAN (202410),
    PARTITION p202410 VALUES LESS THAN (202411),
    PARTITION p202411 VALUES LESS THAN (202412),
    PARTITION p202412 VALUES LESS THAN (202501),
    PARTITION p202501 VALUES LESS THAN (202502),
    PARTITION p202502 VALUES LESS THAN (202503),
    PARTITION p202503 VALUES LESS THAN (202504),
    PARTITION p202504 VALUES LESS THAN (202505),
    PARTITION p202505 VALUES LESS THAN (202506),
    PARTITION p202506 VALUES LESS THAN (202507),
    PARTITION p202507 VALUES LESS THAN (202508),
    PARTITION p202508 VALUES LESS THAN (202509),
    PARTITION p202509 VALUES LESS THAN (202510),
    PARTITION p202510 VALUES LESS THAN (202511),
    PARTITION p202511 VALUES LESS THAN (202512),
    PARTITION p202512 VALUES LESS THAN (202601),
    PARTITION p202601 VALUES LESS THAN (202602),
    PARTITION p202602 VALUES LESS THAN (202603),
    PARTITION p202603 VALUES LESS THAN (202604),
    PARTITION p202604 VALUES LESS THAN (202605),
    PARTITION p202605 VALUES LESS THAN (202606),
    PARTITION p202606 VALUES LESS THAN (202607),
    PARTITION p202607 VALUES LESS THAN (202608),
    PARTITION p202608 VALUES LESS THAN (202609),
    PARTITION p202609 VALUES LESS THAN (202610),
    PARTITION p202610 VALUES LESS THAN (202611),
    PARTITION p202611 VALUES LESS THAN (202612),
    PARTITION p202612 VALUES LESS THAN (202701)
);
-- -----------------------------------------------------------------------------------------
-- index
-- 계약일
CREATE INDEX idx_contract_date ON real_estate_lease(contract_date);
-- 지역 + 기간
CREATE INDEX idx_region_contract_date ON real_estate_lease(region_id, contract_date);
-- 가격 조건
CREATE INDEX idx_price_condition ON real_estate_lease(deposit, monthly_rent, contract_date);