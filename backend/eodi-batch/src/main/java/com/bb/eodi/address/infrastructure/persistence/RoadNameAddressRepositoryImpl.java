package com.bb.eodi.address.infrastructure.persistence;

import com.bb.eodi.address.domain.dto.AddressPosition;
import com.bb.eodi.address.domain.dto.AddressPositionMappingParameter;
import com.bb.eodi.address.domain.dto.RoadNameAddressQueryParameter;
import com.bb.eodi.address.domain.entity.QLandLotAddress;
import com.bb.eodi.address.domain.entity.QRoadNameAddress;
import com.bb.eodi.address.domain.entity.RoadNameAddress;
import com.bb.eodi.address.domain.repository.RoadNameAddressRepository;
import com.bb.eodi.address.infrastructure.persistence.jdbc.RoadNameAddressJdbcRepository;
import com.bb.eodi.core.EodiBatchProperties;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 도로명주소 Repository 구현체
 */
@Repository
@RequiredArgsConstructor
public class RoadNameAddressRepositoryImpl implements RoadNameAddressRepository {

    private final RoadNameAddressJdbcRepository roadNameAddressJdbcRepository;
    private final EodiBatchProperties eodiBatchProperties;
    private final JPAQueryFactory queryFactory;

    @Override
    public void insertBatch(List<? extends RoadNameAddress> items) {
        roadNameAddressJdbcRepository.insertBatch(items, eodiBatchProperties.batchSize());
    }

    @Override
    public Optional<RoadNameAddress> findByManageNo(String addressManageNo) {
        QRoadNameAddress roadNameAddress = QRoadNameAddress.roadNameAddress;

        return Optional.ofNullable(
                queryFactory.selectFrom(roadNameAddress)
                        .where(roadNameAddress.manageNo.eq(addressManageNo))
                        .fetchOne()
        );
    }

    @Override
    public List<RoadNameAddress> findAllByManageNoList(List<String> addressManageNoList) {
        QRoadNameAddress roadNameAddress = QRoadNameAddress.roadNameAddress;

        return queryFactory.selectFrom(roadNameAddress)
                .where(roadNameAddress.manageNo.in(addressManageNoList))
                .fetch();
    }

    @Override
    @Transactional
    public void batchUpdateAdditionalInfo(Collection<? extends RoadNameAddress> items) {
        roadNameAddressJdbcRepository.batchUpdateAdditionalInfo(items, eodiBatchProperties.batchSize());
    }

    @Override
    @Cacheable(cacheNames = "roadNameAddressJoinedWithLandLot", key = "#parameter.legalDongCode + ':' + #parameter.landLotMainNo + ':' + #parameter.landLotSubNo")
    public List<RoadNameAddress> findWithLandLot(RoadNameAddressQueryParameter parameter) {
        QRoadNameAddress roadNameAddress = QRoadNameAddress.roadNameAddress;
        QLandLotAddress landLotAddress = QLandLotAddress.landLotAddress;

        BooleanBuilder condition = new BooleanBuilder();

        if (parameter.getLegalDongCode() != null) {
            condition.and(landLotAddress.legalDongCode.eq(parameter.getLegalDongCode()));
        }

        if (parameter.getLandLotMainNo() != null) {
            condition.and(landLotAddress.landLotMainNo.eq(parameter.getLandLotMainNo()));
        }

        if (parameter.getLandLotSubNo() != null) {
            condition.and(landLotAddress.landLotSubNo.eq(parameter.getLandLotSubNo()));
        }

        return queryFactory.select(roadNameAddress)
                .from(roadNameAddress)
                .join(landLotAddress)
                .on(roadNameAddress.manageNo.eq(landLotAddress.manageNo))
                .where(condition)
                .fetch();
    }

    @Override
    @Transactional
    public void updatePosition(Collection<? extends AddressPositionMappingParameter> items) {
        QRoadNameAddress roadNameAddress = QRoadNameAddress.roadNameAddress;
        QLandLotAddress landLotAddress = QLandLotAddress.landLotAddress;

        List<AddressPositionMappingParameter> unmappedList = new ArrayList<>();

        items.stream()
                .forEach(item -> {
                    long updated = queryFactory.update(roadNameAddress)
                            .set(roadNameAddress.xPos, item.getXPos())
                            .set(roadNameAddress.yPos, item.getYPos())
                            .where(roadNameAddress.manageNo.in(
                                                    JPAExpressions
                                                            .select(landLotAddress.manageNo)
                                                            .from(landLotAddress)
                                                            .where(landLotAddress.legalDongCode.in(item.getLegalDongCodes()))
                                            )
                                            .and(roadNameAddress.roadNameCode.eq(item.getRoadNameCode()))
                                            .and(roadNameAddress.buildingMainNo.eq(item.getBuildingMainNo()))
                                            .and(roadNameAddress.buildingSubNo.eq(item.getBuildingSubNo()))
                                            .and(roadNameAddress.isUnderground.eq(item.getIsUnderground()))
                            )
                            .execute();

                    // TODO 테스트 이후 제거 필요
                    if (updated == 0) {
                        unmappedList.add(item);
                    }
                });

        if (!unmappedList.isEmpty()) {
            roadNameAddressJdbcRepository.batchInsertUnmapped(unmappedList, eodiBatchProperties.batchSize());
        }
    }

    @Override
    public List<AddressPosition> findAddressPositions(RoadNameAddressQueryParameter parameter) {
        QRoadNameAddress roadNameAddress = QRoadNameAddress.roadNameAddress;
        QLandLotAddress landLotAddress = QLandLotAddress.landLotAddress;

        BooleanBuilder condition = new BooleanBuilder();

        if (parameter.getLegalDongCode() != null) {
            condition.and(landLotAddress.legalDongCode.eq(parameter.getLegalDongCode()));
        }

        if (parameter.getLandLotMainNo() != null) {
            condition.and(landLotAddress.landLotMainNo.eq(parameter.getLandLotMainNo()));
        }

        if (parameter.getLandLotSubNo() != null) {
            condition.and(landLotAddress.landLotSubNo.eq(parameter.getLandLotSubNo()));
        }


        return queryFactory.selectDistinct(
                        Projections.constructor(
                                AddressPosition.class,
                                roadNameAddress.xPos,
                                roadNameAddress.yPos,
                                roadNameAddress.buildingName
                        ))
                .from(roadNameAddress)
                .join(landLotAddress)
                .on(roadNameAddress.manageNo.eq(landLotAddress.manageNo))
                .where(condition)
                .fetch();
    }
}
