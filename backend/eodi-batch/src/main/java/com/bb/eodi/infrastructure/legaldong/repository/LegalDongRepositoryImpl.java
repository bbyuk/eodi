package com.bb.eodi.infrastructure.legaldong.repository;

import com.bb.eodi.domain.legaldong.dto.LegalDongSummaryDto;
import com.bb.eodi.domain.legaldong.entity.LegalDong;
import com.bb.eodi.domain.legaldong.repository.LegalDongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 법정동 배치처리 Repository
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class LegalDongRepositoryImpl implements LegalDongRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final LegalDongJpaRepository legalDongJpaRepository;
    private final static Map<String, LegalDong> IN_MEMORY_MAP = new HashMap<>();

    @Override
    public Optional<LegalDong> findByCode(String code) {
        return legalDongJpaRepository.findByCode(code);
    }

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

    @Override
    public Optional<LegalDong> findTopSigunguCodeByName(String name) {
        String key = name.replace(" ", "");

        if (IN_MEMORY_MAP.containsKey(key)) {
            return Optional.of(IN_MEMORY_MAP.get(key));
        }

        legalDongJpaRepository.findAll()
                .stream()
                .forEach(legalDong -> IN_MEMORY_MAP.put(legalDong.getName().replace(" ", ""), legalDong));

        return Optional.of(IN_MEMORY_MAP.get(key));
    }

    @Override
    public void mappingParentIdBatch(List<? extends LegalDong> data) {
        String sql = """
                UPDATE  legal_dong ld_sub JOIN legal_dong ld 
                        ON      ld.code     = :parentCode
                        AND     ld_sub.code = :code
                SET     ld_sub.parent_id = ld.id
                """;

        jdbcTemplate.batchUpdate(sql, data.stream()
                .map(row -> {
                            log.debug("running query : \n{}", sql
                                    .replace(":parentCode", "'" + row.getParentCode() + "'")
                                    .replace(":code", "'" + row.getCode() + "'"));
                            if (row.getCode().equals("4159035033")) {
                                System.out.println("row = " + row);
                            }
                            return new MapSqlParameterSource()
                                    .addValue("parentCode", row.getParentCode())
                                    .addValue("code", row.getCode());
                        }
                ).toArray(SqlParameterSource[]::new));
    }

    @Override
    public List<LegalDongSummaryDto> findAllSummary() {
        return legalDongJpaRepository.findAllSummary();
    }
}
