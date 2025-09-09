-- 공통: 문자셋
SET NAMES utf8mb4;
SET time_zone = '+09:00';

/* --------------------------------------------------------------------------------------- */
-- create
DROP TABLE IF EXISTS legal_dong;

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

/* --------------------------------------------------------------------------------------- */
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

/* --------------------------------------------------------------------------------------- */
-- index
CREATE INDEX idx_ld__parent_id         ON legal_dong(parent_id);
CREATE INDEX idx_ld__code              ON legal_dong(code);