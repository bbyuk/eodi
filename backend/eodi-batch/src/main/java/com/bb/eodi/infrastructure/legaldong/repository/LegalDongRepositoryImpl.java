package com.bb.eodi.infrastructure.legaldong.repository;

import com.bb.eodi.domain.legaldong.entity.LegalDong;
import com.bb.eodi.domain.legaldong.repository.LegalDongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.BatchUpdateException;
import java.util.List;

/**
 * 법정동 배치처리 Repository
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class LegalDongRepositoryImpl implements LegalDongRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void mergeBatch(List<? extends LegalDong> data) {
        String sql = """
                INSERT INTO legal_dong (
                    code,
                    sido_code,
                    sigungu_code,
                    dong_code,
                    name,
                    legal_dong_order,
                    is_active
                )
                VALUES (
                    :code,
                    :sidoCode,
                    :sigunguCode,
                    :dongCode,
                    :name,
                    :legalDongOrder,
                    :isActive
                ) 
                AS new
                ON DUPLICATE KEY UPDATE
                    code    = new.code
                """;
        try {
            jdbcTemplate
                    .batchUpdate(sql, data.stream()
                            .map(row -> new MapSqlParameterSource()
                                    .addValue("code", row.getCode())
                                    .addValue("sidoCode", row.getSidoCode())
                                    .addValue("sigunguCode", row.getSigunguCode())
                                    .addValue("dongCode", row.getDongCode())
                                    .addValue("name", row.getName())
                                    .addValue("legalDongOrder", row.getLegalDongOrder())
                                    .addValue("isActive", row.isActive())
                            )
                            .toArray(SqlParameterSource[]::new));
        }
        catch(RuntimeException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}
