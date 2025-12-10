package com.bb.eodi.address.infrastructure.persistence;

import com.bb.eodi.address.domain.dto.AddressPositionFindQuery;
import com.bb.eodi.address.domain.dto.RoadNameAddressQueryParameter;
import com.bb.eodi.address.domain.entity.AddressPosition;
import com.bb.eodi.address.domain.entity.QAddressPosition;
import com.bb.eodi.address.domain.entity.QLandLotAddress;
import com.bb.eodi.address.domain.entity.QRoadNameAddress;
import com.bb.eodi.address.domain.repository.AddressPositionRepository;
import com.bb.eodi.address.infrastructure.persistence.jdbc.AddressPositionJdbcRepository;
import com.bb.eodi.address.infrastructure.persistence.jpa.AddressPositionJpaRepository;
import com.bb.eodi.core.EodiBatchProperties;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 주소 좌표정보 Repository 구현체
 */
@Repository
@RequiredArgsConstructor
public class AddressPositionRepositoryImpl implements AddressPositionRepository {

    private final AddressPositionJdbcRepository addressPositionJdbcRepository;
    private final AddressPositionJpaRepository addressPositionJpaRepository;
    private final EodiBatchProperties eodiBatchProperties;
    private final JPAQueryFactory queryFactory;

    public void insertBatch(List<? extends AddressPosition> entities) {
        addressPositionJdbcRepository.insertBatch(entities, eodiBatchProperties.batchSize());
    }

    @Override
    public Optional<AddressPosition> findAddressPosition(AddressPositionFindQuery query) {
        QAddressPosition addressPosition = QAddressPosition.addressPosition;
        BooleanBuilder condition = new BooleanBuilder()
                .and(addressPosition.roadNameCode.eq(query.getRoadNameCode()))
                .and(addressPosition.legalDongCode.eq(query.getLegalDongCode()));

        if (query.getIsUnderground() != null) {
            condition.and(addressPosition.isUnderground.eq(query.getIsUnderground()));
        }

        if (query.getBuildingMainNo() != null) {
            condition.and(addressPosition.buildingMainNo.eq(query.getBuildingMainNo()));
        }

        if (query.getBuildingSubNo() != null) {
            condition.and(addressPosition.buildingSubNo.eq(query.getBuildingSubNo()));
        }

        if (query.getBuildingName() != null) {
            condition.and(addressPosition.buildingName.eq(query.getBuildingName()));
        }


        return Optional.ofNullable(
                queryFactory.selectFrom(addressPosition)
                        .where(condition)
                        .fetchOne()
        );
    }

    @Override
    @Cacheable(cacheNames = "addressPositionWithAddress", key = "#query.legalDongCode + ':' + #query.landLotMainNo + ':' + #query.landLotSubNo")
    public List<AddressPosition> findAddressPositionWithAddress(RoadNameAddressQueryParameter query) {
        QLandLotAddress landLotAddress = QLandLotAddress.landLotAddress;
        QRoadNameAddress roadNameAddress = QRoadNameAddress.roadNameAddress;
        QAddressPosition addressPosition = QAddressPosition.addressPosition;

        BooleanBuilder condition = new BooleanBuilder();

        if (query.getLegalDongCode() != null) {
            condition.and(landLotAddress.legalDongCode.eq(query.getLegalDongCode()));
        }

        if (query.getLandLotMainNo() != null) {
            condition.and(landLotAddress.landLotMainNo.eq(query.getLandLotMainNo()));
        }

        if (query.getLandLotSubNo() != null) {
            condition.and(landLotAddress.landLotSubNo.eq(query.getLandLotSubNo()));
        }


        return queryFactory.select(addressPosition)
                .from(landLotAddress)
                .join(roadNameAddress)
                .on(landLotAddress.manageNo.eq(roadNameAddress.manageNo))
                .join(addressPosition)
                .on(
                        landLotAddress.legalDongCode.eq(addressPosition.legalDongCode)
                                .and(roadNameAddress.roadNameCode.eq(addressPosition.roadNameCode))
                                .and(roadNameAddress.buildingMainNo.eq(addressPosition.buildingMainNo))
                                .and(roadNameAddress.buildingSubNo.eq(addressPosition.buildingSubNo))
                                .and(roadNameAddress.isUnderground.eq(addressPosition.isUnderground))
                )
                .where(condition)
                .fetch();
    }
}
