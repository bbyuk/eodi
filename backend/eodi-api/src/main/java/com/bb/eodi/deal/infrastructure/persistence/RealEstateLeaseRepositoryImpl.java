package com.bb.eodi.deal.infrastructure.persistence;

import com.bb.eodi.deal.domain.dto.RealEstateLeaseQuery;
import com.bb.eodi.deal.domain.entity.RealEstateLease;
import com.bb.eodi.deal.domain.repository.RealEstateLeaseRepository;
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
 * 부동산 임대차 실거래가 데이터 Repository 구현체
 */
@Repository
@RequiredArgsConstructor
public class RealEstateLeaseRepositoryImpl implements RealEstateLeaseRepository {

    private final JPAQueryFactory queryFactory;
    private final RealEstateLeaseMapper realEstateLeaseMapper;

    @Override
    public Page<RealEstateLease> findBy(RealEstateLeaseQuery query, Pageable pageable) {
        QRealEstateLeaseJpaEntity realEstateLease = QRealEstateLeaseJpaEntity.realEstateLeaseJpaEntity;
        BooleanBuilder condition = new BooleanBuilder();

        if (query.getMaxDeposit() != null) {
            condition.and(realEstateLease.deposit.loe(query.getMaxDeposit()));
        }

        if (query.getMinDeposit() != null) {
            condition.and(realEstateLease.deposit.goe(query.getMinDeposit()));
        }

        if (query.getMaxMonthlyRentFee() != null) {
            condition.and(realEstateLease.monthlyRent.loe(query.getMaxMonthlyRentFee()));
        }

        if (query.getMinMonthlyRentFee() != null) {
            condition.and(realEstateLease.monthlyRent.goe(query.getMinMonthlyRentFee()));
        }

        if (query.getMaxNetLeasableArea() != null) {
            condition.and(realEstateLease.netLeasableArea.loe(query.getMaxNetLeasableArea()));
        }

        if (query.getMinNetLeasableArea() != null) {
            condition.and(realEstateLease.netLeasableArea.goe(query.getMinNetLeasableArea()));
        }

        if (query.getTargetHousingTypes() != null && !query.getTargetHousingTypes().isEmpty()) {
            condition.and(realEstateLease.housingType.in(query.getTargetHousingTypes()));
        }

        if (query.getTargetRegionIds() != null && !query.getTargetRegionIds().isEmpty()) {
            condition.and(realEstateLease.region.id.in(query.getTargetRegionIds()));
        }

        List<RealEstateLease> content = queryFactory
                .select(realEstateLease)
                .from(realEstateLease)
                .where(condition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(realEstateLease.id.desc())
                .fetch()
                .stream()
                .map(realEstateLeaseMapper::toDomain)
                .collect(Collectors.toList());

        Long totalCount = queryFactory
                .select(realEstateLease.count())
                .from(realEstateLease)
                .where(condition)
                .fetchOne();

        return new PageImpl<>(content, pageable, totalCount != null ? totalCount : 0);
    }
}
