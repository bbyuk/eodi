DROP TABLE IF EXISTS legal_dong_adjacency;

-- -----------------------------------------------------------------------------------------
-- create
CREATE TABLE legal_dong_adjacency
(
    id          BIGINT      AUTO_INCREMENT PRIMARY KEY COMMENT '법정동 인접 ID',
    source_id   BIGINT      NOT NULL    COMMENT '인접 시작 ID',
    target_id   BIGINT      NOT NULL    COMMENT '인접 종료 ID',
    created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시'
)   COMMENT = '법정동 인접 정보';
-- -----------------------------------------------------------------------------------------

-- index
CREATE INDEX idx_legal_dong_adjacency__source_id ON legal_dong_adjacency(source_id);
CREATE INDEX idx_legal_dong_adjacency__target_id ON legal_dong_adjacency(target_id);