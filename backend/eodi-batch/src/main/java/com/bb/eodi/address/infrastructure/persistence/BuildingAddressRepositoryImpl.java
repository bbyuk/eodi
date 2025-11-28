package com.bb.eodi.address.infrastructure.persistence;

import com.bb.eodi.address.domain.dto.BuildingAddressFindQuery;
import com.bb.eodi.address.domain.entity.BuildingAddress;
import com.bb.eodi.address.domain.entity.QBuildingAddress;
import com.bb.eodi.address.domain.repository.BuildingAddressRepository;
import com.bb.eodi.address.infrastructure.persistence.jdbc.BuildingAddressJdbcRepository;
import com.bb.eodi.address.infrastructure.persistence.jpa.BuildingAddressJpaRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 건물주소 Repository 구현체
 */
@Repository
public class BuildingAddressRepositoryImpl implements BuildingAddressRepository {

    private final BuildingAddressJdbcRepository buildingAddressJdbcRepository;
    private final BuildingAddressJpaRepository buildingAddressJpaRepository;
    private final JPAQueryFactory queryFactory;


    public BuildingAddressRepositoryImpl(
            JdbcTemplate jdbcTemplate,
            BuildingAddressJpaRepository buildingAddressJpaRepository,
            JPAQueryFactory queryFactory) {
        this.buildingAddressJdbcRepository = new BuildingAddressJdbcRepository(jdbcTemplate);
        this.buildingAddressJpaRepository = buildingAddressJpaRepository;
        this.queryFactory = queryFactory;
    }

    /**
     * 배치 insert 처리
     *
     * Jdbc batch insert 처리
     * @param entities insert 대상 entity 목록
     */
    @Override
    public void batchInsert(List<? extends BuildingAddress> entities) {
        buildingAddressJdbcRepository.batchInsert(entities);
    }

    /**
     * 건물 주소 조회
     * @param query 조회 쿼리 파라미터
     * @return 건물 주소
     */
    @Override
    public List<BuildingAddress> findBuildingAddress(BuildingAddressFindQuery query) {
        QBuildingAddress buildingAddress = QBuildingAddress.buildingAddress;

        BooleanBuilder condition = new BooleanBuilder();

        if (query.getLegalDongCode() != null) {
            condition.and(buildingAddress.legalDongCode.eq(query.getLegalDongCode()));
        }

        if (query.getLandLotMainNo() != null) {
            condition.and(buildingAddress.landLotMainNo.eq(query.getLandLotMainNo()));
        }

        if (query.getLandLotSubNo() != null) {
            condition.and(buildingAddress.landLotSubNo.eq(query.getLandLotSubNo()));
        }

        if (query.getIsMountain() != null) {
            condition.and(buildingAddress.isMountain.eq(query.getIsMountain()));
        }

        return queryFactory
                .selectFrom(buildingAddress)
                .where(condition)
                .fetch();
    }
}
