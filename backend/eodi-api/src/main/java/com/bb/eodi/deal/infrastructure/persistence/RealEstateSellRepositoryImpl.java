package com.bb.eodi.deal.infrastructure.persistence;

import com.bb.eodi.deal.domain.dto.RealEstateSellQuery;
import com.bb.eodi.deal.domain.entity.RealEstateSell;
import com.bb.eodi.deal.domain.repository.RealEstateSellRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 부동산 실거래가 데이터 Repository 구현체
 */
@Repository
@RequiredArgsConstructor
public class RealEstateSellRepositoryImpl implements RealEstateSellRepository {

    private final JPAQueryFactory queryFactory;
    private final RealEstateSellMapper mapper;

    @Override
    public Page<RealEstateSell> findBy(RealEstateSellQuery query, Pageable pageable) {
        QRealEstateSellJpaEntity realEstateSell = QRealEstateSellJpaEntity.realEstateSellJpaEntity;

        BooleanBuilder condition = new BooleanBuilder();

        if (query.getMaxPrice() != null) {
            condition.and(realEstateSell.price.loe(query.getMaxPrice()));
        }

        if (query.getMinPrice() != null) {
            condition.and(realEstateSell.price.goe(query.getMinPrice()));
        }

        if (query.getEndYearMonth() != null) {
            condition.and(realEstateSell.contractDate.loe(query.getEndYearMonth().atEndOfMonth()));
        }

        if (query.getStartYearMonth() != null) {
            condition.and(realEstateSell.contractDate.goe(query.getStartYearMonth().atDay(1)));
        }

        if (query.getTargetRegionIds() != null && !query.getTargetRegionIds().isEmpty()) {
            condition.and(realEstateSell.region.id.in(query.getTargetRegionIds()));
        }

        if (query.getTargetHousingTypes() != null && !query.getTargetHousingTypes().isEmpty()) {
            condition.and(realEstateSell.housingType.in(query.getTargetHousingTypes()));
        }

        List<RealEstateSell> content = queryFactory
                .selectFrom(realEstateSell)
                .where(condition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(realEstateSell.id.desc())
                .fetch()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        Long total = queryFactory.select(realEstateSell.count())
                .from(realEstateSell)
                .where(condition)
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }
}
