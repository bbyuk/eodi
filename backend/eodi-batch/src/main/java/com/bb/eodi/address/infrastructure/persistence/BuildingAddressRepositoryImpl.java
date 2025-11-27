package com.bb.eodi.address.infrastructure.persistence;

import com.bb.eodi.address.domain.entity.BuildingAddress;
import com.bb.eodi.address.domain.repository.BuildingAddressRepository;
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

    public BuildingAddressRepositoryImpl(JdbcTemplate jdbcTemplate, BuildingAddressJpaRepository buildingAddressJpaRepository) {
        this.buildingAddressJdbcRepository = new BuildingAddressJdbcRepository(jdbcTemplate);
        this.buildingAddressJpaRepository = buildingAddressJpaRepository;
    }

    @Override
    public void batchInsert(List<? extends BuildingAddress> entities) {
        buildingAddressJdbcRepository.batchInsert(entities);
    }
}
