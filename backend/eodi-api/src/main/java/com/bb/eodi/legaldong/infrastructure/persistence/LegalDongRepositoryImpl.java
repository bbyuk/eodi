package com.bb.eodi.legaldong.infrastructure.persistence;

import com.bb.eodi.legaldong.domain.entity.LegalDong;
import com.bb.eodi.legaldong.domain.query.LegalDongFindQuery;
import com.bb.eodi.legaldong.domain.query.LegalDongScope;
import com.bb.eodi.legaldong.domain.repository.LegalDongRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 법정동 Repository 구현
 */
@Repository
@RequiredArgsConstructor
public class LegalDongRepositoryImpl implements LegalDongRepository {

    private final JPAQueryFactory queryFactory;
    private final LegalDongMapper mapper;

    @Override
    public Optional<LegalDong> findByCode(String code) {
        QLegalDongJpaEntity legalDong = QLegalDongJpaEntity.legalDongJpaEntity;
        return Optional.of(
                mapper.toDomain(
                        queryFactory.selectFrom(legalDong)
                                .where(legalDong.active.eq(true)
                                        .and(legalDong.code.eq(code)))
                                .fetchOne()
                )
        );
    }

    @Override
    public List<LegalDong> findAll() {
        QLegalDongJpaEntity legalDong = QLegalDongJpaEntity.legalDongJpaEntity;

        return queryFactory.selectFrom(legalDong)
                .where(legalDong.active.eq(true))
                .fetch()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }


}
