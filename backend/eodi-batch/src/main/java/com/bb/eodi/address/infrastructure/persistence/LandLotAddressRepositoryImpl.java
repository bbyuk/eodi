package com.bb.eodi.address.infrastructure.persistence;

import com.bb.eodi.address.domain.dto.LandLotAddressFindQuery;
import com.bb.eodi.address.domain.entity.LandLotAddress;
import com.bb.eodi.address.domain.entity.QLandLotAddress;
import com.bb.eodi.address.infrastructure.persistence.jdbc.LandLotAddressJdbcRepository;
import com.bb.eodi.address.domain.repository.LandLotAddressRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 지번주소 Repository 구현체
 */
@Repository
public class LandLotAddressRepositoryImpl implements LandLotAddressRepository {

    private final LandLotAddressJdbcRepository landLotAddressJdbcRepository;
    private final JPAQueryFactory queryFactory;

    public LandLotAddressRepositoryImpl(JdbcTemplate jdbcTemplate, JPAQueryFactory queryFactory) {
        this.landLotAddressJdbcRepository = new LandLotAddressJdbcRepository(jdbcTemplate);
        this.queryFactory = queryFactory;
    }


    /**
     * 지번 주소 배치 insert
     * @param entities insert 대상 entity chunk
     */
    @Override
    public void insertBatch(List<? extends LandLotAddress> entities) {
        landLotAddressJdbcRepository.insertBatch(entities);
    }

    /**
     * 지번주소 조회
     * @param query 조회 쿼리 파라미터
     * @return 지번 주소
     */
    @Override
    public List<LandLotAddress> findLandLotAddress(LandLotAddressFindQuery query) {
        QLandLotAddress landLotAddress = QLandLotAddress.landLotAddress;

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

        return queryFactory.selectFrom(landLotAddress)
                .where(condition)
                .fetch();
    }
}
