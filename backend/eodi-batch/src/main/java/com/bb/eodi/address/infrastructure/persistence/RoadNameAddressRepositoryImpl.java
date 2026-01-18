package com.bb.eodi.address.infrastructure.persistence;

import com.bb.eodi.address.domain.dto.AddressPosition;
import com.bb.eodi.address.domain.dto.RoadNameAddressQueryParameter;
import com.bb.eodi.address.domain.entity.QLandLotAddress;
import com.bb.eodi.address.domain.entity.QRoadNameAddress;
import com.bb.eodi.address.domain.entity.RoadNameAddress;
import com.bb.eodi.address.domain.repository.RoadNameAddressRepository;
import com.bb.eodi.address.infrastructure.persistence.jdbc.RoadNameAddressJdbcRepository;
import com.bb.eodi.core.EodiBatchProperties;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

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
    @Transactional
    public void updatePosition(Collection<? extends RoadNameAddress> items) {
        roadNameAddressJdbcRepository.updatePositionBatch(items, eodiBatchProperties.batchSize());
    }

    @Override
    public List<AddressPosition> findAddressPositions(RoadNameAddressQueryParameter parameter) {
        QRoadNameAddress roadNameAddress = QRoadNameAddress.roadNameAddress;

        BooleanBuilder condition = new BooleanBuilder();

        if (parameter.getLegalDongCode() != null) {
            condition.and(roadNameAddress.legalDongCode.eq(parameter.getLegalDongCode()));
        }

        if (parameter.getLandLotMainNo() != null) {
            condition.and(roadNameAddress.landLotMainNo.eq(parameter.getLandLotMainNo()));
        }

        if (parameter.getLandLotSubNo() != null) {
            condition.and(roadNameAddress.landLotSubNo.eq(parameter.getLandLotSubNo()));
        }

        return queryFactory.selectDistinct(
                        Projections.constructor(
                                AddressPosition.class,
                                roadNameAddress.xPos,
                                roadNameAddress.yPos,
                                roadNameAddress.buildingName
                        ))
                .from(roadNameAddress)
                .where(condition)
                .fetch();
    }

    @Override
    public void updateBatch(Collection<? extends RoadNameAddress> items) {
        roadNameAddressJdbcRepository.batchUpdate(items, eodiBatchProperties.batchSize());
    }

    @Override
    public void deleteBatch(Collection<? extends RoadNameAddress> items) {
        roadNameAddressJdbcRepository.batchDelete(items, eodiBatchProperties.batchSize());
    }
}
