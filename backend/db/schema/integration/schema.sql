-- legal_dong
-- -----------------------------------------------------------------------------------------
DROP TABLE IF EXISTS legal_dong;

-- create
CREATE TABLE legal_dong
(
    id                  BIGINT          AUTO_INCREMENT PRIMARY KEY COMMENT '법정동 ID',
    code                VARCHAR(15)     NOT NULL COMMENT '법정동 코드',
    sido_code           VARCHAR(2)      NOT NULL COMMENT '시도 코드',
    sigungu_code        VARCHAR(3)      NOT NULL COMMENT '시군구 코드',
    dong_code           VARCHAR(3)      NOT NULL COMMENT '동 코드',
    name                VARCHAR(50)     NOT NULL COMMENT '법정동 명',
    legal_dong_order    INT             NOT NULL COMMENT '법정동 서열',
    parent_id           BIGINT          COMMENT '상위 법정동 ID',
    is_active           TINYINT(1)      NOT NULL DEFAULT 1 CHECK (is_active IN (0,1)) COMMENT '활성여부',
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시'
) COMMENT = '법정동';
-- -----------------------------------------------------------------------------------------
-- constraint
ALTER TABLE legal_dong
    ADD CONSTRAINT fk_ld__parent_id
        FOREIGN KEY (parent_id)
            REFERENCES legal_dong(id)
            ON UPDATE RESTRICT
            ON DELETE RESTRICT;
ALTER TABLE legal_dong
    ADD CONSTRAINT uq_ld__code
        UNIQUE (code);
-- -----------------------------------------------------------------------------------------
-- index
CREATE INDEX idx_ld__parent_id         ON legal_dong(parent_id);
CREATE INDEX idx_ld__code              ON legal_dong(code);
-- -----------------------------------------------------------------------------------------


-- real_estate_sell
-- -----------------------------------------------------------------------------------------
DROP TABLE IF EXISTS real_estate_sell_new;

-- -----------------------------------------------------------------------------------------
-- create
CREATE TABLE real_estate_sell_new
(
    id                      BIGINT  NOT NULL AUTO_INCREMENT COMMENT '매매 실거래가 ID',
    region_id               BIGINT  NOT NULL COMMENT '대상지역 법정동 ID',
    legal_dong_name         VARCHAR(50) COMMENT '법정동 명',
    contract_date           DATE    NOT NULL COMMENT '계약일',
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
    updated_at              TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (id, contract_date)
) COMMENT = '부동산 매매 실거래가'
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
CREATE INDEX idx_contract_date ON real_estate_sell_new(contract_date);
-- 지역 + 기간
CREATE INDEX idx_region_contract_date ON real_estate_sell_new(region_id, contract_date);
-- 가격 - 전용면적
CREATE INDEX idx_price_area ON real_estate_sell_new(price, net_leasable_area, contract_date);
-- 가격 - 주택유형
CREATE INDEX idx_price_housing ON real_estate_sell_new(price, housing_type, contract_date);
-- -----------------------------------------------------------------------------------------

-- real_estate_lease
-- -----------------------------------------------------------------------------------------
DROP TABLE IF EXISTS real_estate_lease_new;

-- -----------------------------------------------------------------------------------------
-- create
CREATE TABLE real_estate_lease_new
(
    id                      BIGINT  NOT NULL AUTO_INCREMENT COMMENT '임대차 실거래가 ID',
    region_id               BIGINT  NOT NULL COMMENT '대상지역 법정동 ID',
    legal_dong_name         VARCHAR(50) COMMENT '법정동 명',
    contract_date           DATE    COMMENT '계약일',
    contract_start_month    INT    COMMENT '계약시작 월',
    contract_end_month      INT    COMMENT '계약종료 월',
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
CREATE INDEX idx_contract_date ON real_estate_lease_new(contract_date);
-- 지역 + 기간
CREATE INDEX idx_region_contract_date ON real_estate_lease_new(region_id, contract_date);
-- 가격 조건
CREATE INDEX idx_price_condition ON real_estate_lease_new(deposit, monthly_rent, contract_date);
-- -----------------------------------------------------------------------------------------

-- Spring Batch
/* --------------------------------------------------------------------------------------- */
DROP TABLE IF EXISTS BATCH_STEP_EXECUTION_CONTEXT;
DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_CONTEXT;
DROP TABLE IF EXISTS BATCH_STEP_EXECUTION;
DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_PARAMS;
DROP TABLE IF EXISTS BATCH_JOB_EXECUTION;
DROP TABLE IF EXISTS BATCH_JOB_INSTANCE;

DROP TABLE IF EXISTS BATCH_STEP_EXECUTION_SEQ;
DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_SEQ;
DROP TABLE IF EXISTS BATCH_JOB_SEQ;

-- -----------------------------------------------------------------------------------------
CREATE TABLE BATCH_JOB_INSTANCE  (
                                     JOB_INSTANCE_ID BIGINT  NOT NULL PRIMARY KEY ,
                                     VERSION BIGINT ,
                                     JOB_NAME VARCHAR(100) NOT NULL,
                                     JOB_KEY VARCHAR(32) NOT NULL,
                                     constraint JOB_INST_UN unique (JOB_NAME, JOB_KEY)
) ENGINE=InnoDB;

CREATE TABLE BATCH_JOB_EXECUTION  (
                                      JOB_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
                                      VERSION BIGINT  ,
                                      JOB_INSTANCE_ID BIGINT NOT NULL,
                                      CREATE_TIME DATETIME(6) NOT NULL,
                                      START_TIME DATETIME(6) DEFAULT NULL ,
                                      END_TIME DATETIME(6) DEFAULT NULL ,
                                      STATUS VARCHAR(10) ,
                                      EXIT_CODE VARCHAR(2500) ,
                                      EXIT_MESSAGE VARCHAR(2500) ,
                                      LAST_UPDATED DATETIME(6),
                                      constraint JOB_INST_EXEC_FK foreign key (JOB_INSTANCE_ID)
                                          references BATCH_JOB_INSTANCE(JOB_INSTANCE_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_JOB_EXECUTION_PARAMS  (
                                             JOB_EXECUTION_ID BIGINT NOT NULL ,
                                             PARAMETER_NAME VARCHAR(100) NOT NULL ,
                                             PARAMETER_TYPE VARCHAR(100) NOT NULL ,
                                             PARAMETER_VALUE VARCHAR(2500) ,
                                             IDENTIFYING CHAR(1) NOT NULL ,
                                             constraint JOB_EXEC_PARAMS_FK foreign key (JOB_EXECUTION_ID)
                                                 references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_STEP_EXECUTION  (
                                       STEP_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
                                       VERSION BIGINT NOT NULL,
                                       STEP_NAME VARCHAR(100) NOT NULL,
                                       JOB_EXECUTION_ID BIGINT NOT NULL,
                                       CREATE_TIME DATETIME(6) NOT NULL,
                                       START_TIME DATETIME(6) DEFAULT NULL ,
                                       END_TIME DATETIME(6) DEFAULT NULL ,
                                       STATUS VARCHAR(10) ,
                                       COMMIT_COUNT BIGINT ,
                                       READ_COUNT BIGINT ,
                                       FILTER_COUNT BIGINT ,
                                       WRITE_COUNT BIGINT ,
                                       READ_SKIP_COUNT BIGINT ,
                                       WRITE_SKIP_COUNT BIGINT ,
                                       PROCESS_SKIP_COUNT BIGINT ,
                                       ROLLBACK_COUNT BIGINT ,
                                       EXIT_CODE VARCHAR(2500) ,
                                       EXIT_MESSAGE VARCHAR(2500) ,
                                       LAST_UPDATED DATETIME(6),
                                       constraint JOB_EXEC_STEP_FK foreign key (JOB_EXECUTION_ID)
                                           references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_STEP_EXECUTION_CONTEXT  (
                                               STEP_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
                                               SHORT_CONTEXT VARCHAR(2500) NOT NULL,
                                               SERIALIZED_CONTEXT TEXT ,
                                               constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
                                                   references BATCH_STEP_EXECUTION(STEP_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_JOB_EXECUTION_CONTEXT  (
                                              JOB_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
                                              SHORT_CONTEXT VARCHAR(2500) NOT NULL,
                                              SERIALIZED_CONTEXT TEXT ,
                                              constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
                                                  references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_STEP_EXECUTION_SEQ (
                                          ID BIGINT NOT NULL,
                                          UNIQUE_KEY CHAR(1) NOT NULL,
                                          constraint UNIQUE_KEY_UN unique (UNIQUE_KEY)
) ENGINE=InnoDB;

INSERT INTO BATCH_STEP_EXECUTION_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_STEP_EXECUTION_SEQ);

CREATE TABLE BATCH_JOB_EXECUTION_SEQ (
                                         ID BIGINT NOT NULL,
                                         UNIQUE_KEY CHAR(1) NOT NULL,
                                         constraint UNIQUE_KEY_UN unique (UNIQUE_KEY)
) ENGINE=InnoDB;

INSERT INTO BATCH_JOB_EXECUTION_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_JOB_EXECUTION_SEQ);

CREATE TABLE BATCH_JOB_SEQ (
                               ID BIGINT NOT NULL,
                               UNIQUE_KEY CHAR(1) NOT NULL,
                               constraint UNIQUE_KEY_UN unique (UNIQUE_KEY)
) ENGINE=InnoDB;

INSERT INTO BATCH_JOB_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_JOB_SEQ);
