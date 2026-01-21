package com.bb.eodi.legaldong.infrastructure.persistence;

import com.bb.eodi.legaldong.domain.dto.LegalDongSummaryDto;
import com.bb.eodi.legaldong.domain.entity.LegalDong;
import com.bb.eodi.legaldong.domain.entity.QLegalDong;
import com.bb.eodi.legaldong.domain.repository.LegalDongRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 법정동 배치처리 Repository
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class LegalDongRepositoryImpl implements LegalDongRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<LegalDong> findByCode(String code) {
        QLegalDong ld = QLegalDong.legalDong;

        return Optional.ofNullable(
                queryFactory
                        .selectFrom(ld)
                        .where(
                                ld.code.eq(code)
                                        .and(ld.isActive.eq(true)))
                        .fetchOne()
        );
    }

    @Override
    public Optional<LegalDong> findAnyByCode(String code) {
        QLegalDong ld = QLegalDong.legalDong;
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(ld)
                        .where(
                                ld.code.eq(code)
                        )
                        .fetchOne()
        );
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
        QLegalDong ld = QLegalDong.legalDong;

        return queryFactory.select(Projections.constructor(
                        LegalDongSummaryDto.class,
                        ld.sidoCode,
                        ld.sigunguCode
                ))
                .from(ld)
                .where(ld.isActive.eq(true))
                .fetch();
    }

    @Override
    public Optional<LegalDong> findBySidoCodeAndSigunguCodeAndLegalDongName(String sidoCode, String sigunguCode, String legalDongName) {

        QLegalDong legalDong = QLegalDong.legalDong;
        BooleanBuilder condition = new BooleanBuilder();

        condition.and(legalDong.isActive.isTrue());

        if (sidoCode != null) {
            condition.and(legalDong.sidoCode.eq(sidoCode));
        }

        if (sigunguCode != null) {
            condition.and(legalDong.sigunguCode.eq(sigunguCode));
        }

        if (legalDongName != null) {
            condition.and(legalDong.name.eq(legalDongName));
        }

        return Optional.ofNullable(
                queryFactory.selectFrom(legalDong)
                        .where(condition)
                        .fetchOne()
        );
    }
}
