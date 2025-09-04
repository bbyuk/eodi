-- 공통: 문자셋
SET NAMES utf8mb4;
SET time_zone = '+09:00';

CREATE TABLE legal_dong (
    id          BIGINT      AUTO_INCREMENT PRIMARY KEY,
    code        VARCHAR(3)  NOT NULL,
    name        VARCHAR(50) NOT NULL,
    parent_id   BIGINT      ,
    is_active   TINYINT(1)  NOT NULL DEFAULT 0 CHECK (is_abolished IN (0,1)),
    created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    ---------------------------------------------------------------------------------------

    UNIQUE  (code),
    CONSTRAINT fk_legal_dong_parent_id
        FOREIGN KEY (parent_id)
            REFERENCES legal_dong(id)
            ON UPDATE RESTRICT
            ON DELETE RESTRICT
)