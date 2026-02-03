package com.bb.eodi.address.infrastructure.persistence;

import com.bb.eodi.address.domain.entity.LandLotAddress;
import com.bb.eodi.address.domain.repository.LandLotAddressRepository;
import com.bb.eodi.address.infrastructure.persistence.jdbc.LandLotAddressJdbcRepository;
import com.bb.eodi.core.EodiBatchProperties;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 지번주소 Repository 구현체
 */
@Repository
@RequiredArgsConstructor
public class LandLotAddressRepositoryImpl implements LandLotAddressRepository {

    private final LandLotAddressJdbcRepository landLotAddressJdbcRepository;
    private final EodiBatchProperties eodiBatchProperties;
    private final JPAQueryFactory queryFactory;

    /**
     * 지번 주소 배치 insert
     *
     * @param entities insert 대상 entity chunk
     */
    @Override
    public void insertBatch(List<? extends LandLotAddress> entities) {
        landLotAddressJdbcRepository.insertBatch(entities, eodiBatchProperties.batchSize());
    }

    @Override
    public void updateBatch(List<? extends LandLotAddress> entities) {
        landLotAddressJdbcRepository.updateBatch(entities, eodiBatchProperties.batchSize());
    }

    @Override
    public void deleteBatch(List<LandLotAddress> entities) {
        landLotAddressJdbcRepository.deleteBatch(entities, eodiBatchProperties.batchSize());
    }
}
