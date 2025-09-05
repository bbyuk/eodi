-- 공통: 문자셋
SET NAMES utf8mb4;
SET time_zone = '+09:00';

/* --------------------------------------------------------------------------------------- */
-- create

CREATE TABLE sale
(
    id                  BIGINT  AUTO_INCREMENT PRIMARY KEY COMMENT '매매 ID',
    legal_dong_id       BIGINT  NOT NULL COMMENT '법정동 ID',
    road_name           VARCHAR(50) COMMENT '도로명',
    contract_date       DATE    COMMENT '계약일',
    price               INT COMMENT '거래금액',
    trade_method_type   ENUM('D', 'A', 'O') NOT NULL DEFAULT 'O' COMMENT '거래방법',
    cancel_date         DATE    COMMENT '해제사유 발생일',
    build_year          INTEGER COMMENT '건축년도',
    net_leasable_area   DECIMAL(10, 4) NOT NULL COMMENT '전용면적',
    site_area           DECIMAL(10, 4) COMMENT '대지면적',
    total_floor_area    DECIMAL(10, 4) COMMENT '연면적',
    buyer               VARCHAR(50) COMMENT '매수자',
    seller              VARCHAR(50) COMMENT '매도자',
    housing_type        ENUM('AP', 'MH', 'DT', 'MU', 'OF', 'PR', 'DR', 'O') NOT NULL DEFAULT 'O' COMMENT '주택유형',
    registration_date   DATE    COMMENT '등기일자',
    target_name         VARCHAR(100) NOT NULL COMMENT '대상명',
    dong                VARCHAR(10) COMMENT '동',
    floor               INTEGER COMMENT '층'
) COMMENT = '매매'

/* --------------------------------------------------------------------------------------- */
-- constraint

ALTER TABLE sale
    ADD CONSTRAINT fk_sale__legal_dong_id
        FOREIGN KEY (legal_dong_id)
            REFERENCES legal_dong(id)
            ON UPDATE RESTRICT
            ON DELETE RESTRICT;

/* --------------------------------------------------------------------------------------- */
-- index

CREATE INDEX idx_sale__ldi ON sale(legal_dong_id);
CREATE INDEX idx_sale__tn ON sale(target_name);
CREATE INDEX idx_sale__cd ON sale(contract_date);
CREATE INDEX idx_sale__ht ON sale(housing_type);
CREATE INDEX idx_sale__p ON sale(price);
CREATE INDEX idx_sale__nla ON sale(net_leasable_area);
