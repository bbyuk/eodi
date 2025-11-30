package com.bb.eodi.address.infrastructure.persistence;

import com.bb.eodi.address.domain.dto.AddressPositionFindQuery;
import com.bb.eodi.address.domain.entity.AddressPosition;
import com.bb.eodi.address.domain.entity.QAddressPosition;
import com.bb.eodi.address.domain.repository.AddressPositionRepository;
import com.bb.eodi.address.infrastructure.persistence.jdbc.AddressPositionJdbcRepository;
import com.bb.eodi.address.infrastructure.persistence.jpa.AddressPositionJpaRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 주소 좌표정보 Repository 구현체
 */
@Repository
public class AddressPositionRepositoryImpl implements AddressPositionRepository {

    private final AddressPositionJdbcRepository addressPositionJdbcRepository;
    private final AddressPositionJpaRepository addressPositionJpaRepository;
    private final JPAQueryFactory queryFactory;

    public AddressPositionRepositoryImpl(
            JdbcTemplate jdbcTemplate,
            AddressPositionJpaRepository addressPositionJpaRepository,
            JPAQueryFactory queryFactory) {
        this.addressPositionJdbcRepository = new AddressPositionJdbcRepository(jdbcTemplate);
        this.addressPositionJpaRepository = addressPositionJpaRepository;
        this.queryFactory = queryFactory;
    }

    @Override
    public void insertBatch(List<? extends AddressPosition> entities) {
        addressPositionJdbcRepository.insertBatch(entities);
    }

    @Override
    public Optional<AddressPosition> findAddressPosition(AddressPositionFindQuery query) {
        QAddressPosition addressPosition = QAddressPosition.addressPosition;
        BooleanBuilder condition = new BooleanBuilder();

        condition
                .and(addressPosition.roadNameCode.eq(query.getRoadNameCode()))
                .and(addressPosition.legalDongCode.eq(query.getLegalDongCode()))
                .and(addressPosition.isUnderground.eq(query.getIsUnderground()))
                .and(addressPosition.buildingMainNo.eq(query.getBuildingMainNo()))
                .and(addressPosition.buildingSubNo.eq(query.getBuildingSubNo()));

        return Optional.ofNullable(
                queryFactory.selectFrom(addressPosition)
                .where(condition)
                .fetchOne()
        );
    }
}
