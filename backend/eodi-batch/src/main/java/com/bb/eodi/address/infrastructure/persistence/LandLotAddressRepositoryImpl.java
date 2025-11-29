package com.bb.eodi.address.infrastructure.persistence;

import com.bb.eodi.address.domain.entity.LandLotAddress;
import com.bb.eodi.address.infrastructure.persistence.jdbc.LandLotAddressJdbcRepository;
import com.bb.eodi.address.domain.repository.LandLotAddressRepository;
import com.bb.eodi.address.infrastructure.persistence.jpa.LandLotAddressJpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 지번주소 Repository 구현체
 */
@Repository
public class LandLotAddressRepositoryImpl implements LandLotAddressRepository {

    private final LandLotAddressJdbcRepository landLotAddressJdbcRepository;
    private final LandLotAddressJpaRepository landLotAddressJpaRepository;

    public LandLotAddressRepositoryImpl(JdbcTemplate jdbcTemplate, LandLotAddressJpaRepository landLotAddressJpaRepository) {
        this.landLotAddressJdbcRepository = new LandLotAddressJdbcRepository(jdbcTemplate);
        this.landLotAddressJpaRepository = landLotAddressJpaRepository;
    }

    @Override
    public void insertBatch(List<? extends LandLotAddress> entities) {

    }
}
