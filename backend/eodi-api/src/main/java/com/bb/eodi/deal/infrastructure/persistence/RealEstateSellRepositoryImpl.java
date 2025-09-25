package com.bb.eodi.deal.infrastructure.persistence;

import com.bb.eodi.deal.domain.dto.RealEstateSellQuery;
import com.bb.eodi.deal.domain.entity.RealEstateSell;
import com.bb.eodi.deal.domain.repository.RealEstateSellRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 부동산 실거래가 데이터 Repository 구현체
 */
@Repository
@RequiredArgsConstructor
public class RealEstateSellRepositoryImpl implements RealEstateSellRepository {

    private final RealEstateSellJpaRepository realEstateSellJpaRepository;
    private final JPAQueryFactory queryFactory;
    private final RealEstateSellMapper mapper;

    @Override
    public List<RealEstateSell> findBy(RealEstateSellQuery query) {
        QRealEstateSellJpaEntity realEstateSell = QRealEstateSellJpaEntity.realEstateSellJpaEntity;
        JPAQuery<RealEstateSellJpaEntity> jpaQuery = queryFactory
                .selectFrom(realEstateSell)
                .where(realEstateSell.price.between(query.getMinPrice(), query.getMaxPrice()));

        if (query.getHousingType() != null) {
            jpaQuery = jpaQuery.where(realEstateSell.housingType.eq(query.getHousingType()));
        }

        return jpaQuery.fetch()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
