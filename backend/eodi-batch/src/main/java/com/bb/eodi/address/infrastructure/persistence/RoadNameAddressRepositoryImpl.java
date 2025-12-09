package com.bb.eodi.address.infrastructure.persistence;

import com.bb.eodi.address.domain.dto.RoadNameAddressQueryParameter;
import com.bb.eodi.address.domain.entity.QLandLotAddress;
import com.bb.eodi.address.domain.entity.QRoadNameAddress;
import com.bb.eodi.address.domain.entity.RoadNameAddress;
import com.bb.eodi.address.domain.repository.RoadNameAddressRepository;
import com.bb.eodi.address.infrastructure.persistence.jdbc.RoadNameAddressJdbcRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
    private final JPAQueryFactory queryFactory;

    @Override
    public void insertBatch(List<? extends RoadNameAddress> items) {
        roadNameAddressJdbcRepository.insertBatch(items);
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
    public void batchUpdateAdditionalInfo(Collection<? extends RoadNameAddress> items) {
        roadNameAddressJdbcRepository.batchUpdateAdditionalInfo(items);
    }

    @Override
    public List<RoadNameAddress> findWithLandLot(RoadNameAddressQueryParameter parameter) {
        QRoadNameAddress roadNameAddress = QRoadNameAddress.roadNameAddress;
        QLandLotAddress landLotAddress = QLandLotAddress.landLotAddress;

        BooleanBuilder condition = new BooleanBuilder();

        if (parameter.getLegalDongCode() != null) {
            condition.and(landLotAddress.legalDongCode.eq(parameter.getLegalDongCode()));
        }

        if (parameter.getLandLotMainNo() != null) {
            condition.and(landLotAddress.landLotMainNo.eq(parameter.getLandLotSubNo()));
        }

        if (parameter.getLandLotSubNo() != null) {
            condition.and(landLotAddress.landLotSubNo.eq(parameter.getLandLotSubNo()));
        }

        condition.and(landLotAddress.isRepresentative.eq("1"));

        return queryFactory.select(roadNameAddress)
                .from(roadNameAddress)
                .join(landLotAddress)
                .on(roadNameAddress.manageNo.eq(landLotAddress.manageNo))
                .where(condition)
                .fetch();
    }
}
