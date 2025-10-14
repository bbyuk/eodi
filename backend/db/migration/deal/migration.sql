-- FK 확인
SELECT
    CONSTRAINT_NAME,
    COLUMN_NAME,
    REFERENCED_TABLE_NAME,
    REFERENCED_COLUMN_NAME
FROM	INFORMATION_SCHEMA.KEY_COLUMN_USAGE
WHERE 	TABLE_SCHEMA = 'eodi_db'
  AND 	(
            TABLE_NAME = 'real_estate_sell'
        OR
            TABLE_NAME = 'real_estate_lease'
    )
  AND 	REFERENCED_TABLE_NAME IS NOT NULL;

-- FK 끊기
ALTER TABLE real_estate_lease DROP FOREIGN KEY fk_real_estate_lease__region_id;
ALTER TABLE real_estate_sell DROP FOREIGN KEY fk_real_estate_sell__region_id;

-- new 테이블 생성 (작업 완료)

-- 데이터 복사 -> RENAME
INSERT INTO real_estate_sell_new
SELECT * FROM real_estate_sell;

RENAME TABLE real_estate_sell 		TO real_estate_sell_old,
			 real_estate_sell_new 	TO real_estate_sell;


INSERT INTO real_estate_lease_new
SELECT * FROM real_estate_lease;

RENAME TABLE real_estate_lease 		TO real_estate_lease_old,
			 real_estate_lease_new 	TO real_estate_lease;