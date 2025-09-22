DROP TABLE IF EXISTS real_estate_sell;

-- -----------------------------------------------------------------------------------------
-- create
CREATE TABLE real_estate_sell
(
    id                      BIGINT  AUTO_INCREMENT PRIMARY KEY COMMENT '매매 실거래가 ID',
    region_id               BIGINT  NOT NULL COMMENT '대상지역 법정동 ID',
    legal_dong_name         VARCHAR(50) COMMENT '법정동 명',
    contract_date           DATE    COMMENT '계약일',
    price                   BIGINT COMMENT '거래금액',
    trade_method_type       ENUM('D', 'A', 'O') NOT NULL DEFAULT 'O' COMMENT '거래방법',
    cancel_date             DATE    COMMENT '해제사유 발생일',
    build_year              INTEGER COMMENT '건축년도',
    net_leasable_area       DECIMAL(10, 4) COMMENT '전용면적',
    land_area               DECIMAL(10, 4) COMMENT '대지면적',
    total_floor_area        DECIMAL(10, 4) COMMENT '연면적',
    buyer                   VARCHAR(50) COMMENT '매수자',
    seller                  VARCHAR(50) COMMENT '매도자',
    housing_type            ENUM('AP', 'MH', 'DT', 'MU', 'OF', 'PR', 'OR', 'O') NOT NULL DEFAULT 'O' COMMENT '주택유형',
    date_of_registration    DATE    COMMENT '등기일자',
    target_name             VARCHAR(100) COMMENT '대상명',
    building_dong           VARCHAR(50) COMMENT '건물 동',
    floor                   INTEGER COMMENT '층',
    is_land_lease           TINYINT(1) NOT NULL DEFAULT 0 COMMENT '토지임대부 여부',
    created_at              TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at              TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시'
) COMMENT = '부동산 매매 실거래가';
-- -----------------------------------------------------------------------------------------
-- constraint
ALTER TABLE real_estate_sell
    ADD CONSTRAINT fk_real_estate_sell__region_id
        FOREIGN KEY (region_id)
            REFERENCES legal_dong(id)
            ON UPDATE RESTRICT
            ON DELETE RESTRICT;
-- -----------------------------------------------------------------------------------------
-- index
CREATE INDEX idx_real_estate_sell__ri ON real_estate_sell(region_id);
