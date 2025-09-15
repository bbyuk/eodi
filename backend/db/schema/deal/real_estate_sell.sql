-- 공통: 문자셋
SET NAMES utf8mb4;
SET time_zone = '+09:00';

DROP TABLE IF EXISTS real_estate_sell;

-- -----------------------------------------------------------------------------------------
-- create
CREATE TABLE real_estate_sell
(
    id                      BIGINT  AUTO_INCREMENT PRIMARY KEY COMMENT '매매 ID',
    region_id               BIGINT  NOT NULL COMMENT '대상지역 법정동 ID',
    legal_dong_name         VARCHAR(50) COMMENT '법정동 명',
    contract_date           DATE    COMMENT '계약일',
    price                   INT COMMENT '거래금액',
    trade_method_type       ENUM('D', 'A', 'O') NOT NULL DEFAULT 'O' COMMENT '거래방법',
    cancel_date             DATE    COMMENT '해제사유 발생일',
    build_year              INTEGER COMMENT '건축년도',
    net_leasable_area       DECIMAL(10, 4) NOT NULL COMMENT '전용면적',
    land_area               DECIMAL(10, 4) COMMENT '대지면적',
    total_floor_area        DECIMAL(10, 4) COMMENT '연면적',
    buyer                   VARCHAR(50) COMMENT '매수자',
    real_estate_seller                  VARCHAR(50) COMMENT '매도자',
    housing_type            ENUM('AP', 'MH', 'DT', 'MU', 'OF', 'PR', 'DR', 'O') NOT NULL DEFAULT 'O' COMMENT '주택유형',
    date_of_registration    DATE    COMMENT '등기일자',
    target_name             VARCHAR(100) NOT NULL COMMENT '대상명',
    building_dong           VARCHAR(10) COMMENT '동',
    floor                   INTEGER COMMENT '층',
    is_land_lease           TINYINT(1) NOT NULL DEFAULT 0 COMMENT '활성 여부',
    created_at              TIMESTAMP COMMENT '생성일시',
    updated_at              TIMESTAMP COMMENT '수정일시'
) COMMENT = '매매';
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
CREATE INDEX idx_real_estate_sell__tn ON real_estate_sell(target_name);
CREATE INDEX idx_real_estate_sell__cd ON real_estate_sell(contract_date);
CREATE INDEX idx_real_estate_sell__ht ON real_estate_sell(housing_type);
CREATE INDEX idx_real_estate_sell__p ON real_estate_sell(price);
CREATE INDEX idx_real_estate_sell__nla ON real_estate_sell(net_leasable_area);
