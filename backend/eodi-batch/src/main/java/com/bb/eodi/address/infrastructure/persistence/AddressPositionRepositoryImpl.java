package com.bb.eodi.address.infrastructure.persistence;

import com.bb.eodi.address.domain.entity.AddressPosition;
import com.bb.eodi.address.domain.repository.AddressPositionRepository;
import com.bb.eodi.address.infrastructure.persistence.jdbc.AddressPositionJdbcRepository;
import com.bb.eodi.address.infrastructure.persistence.jpa.AddressPositionJpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 주소 좌표정보 Repository 구현체
 */
@Repository
public class AddressPositionRepositoryImpl implements AddressPositionRepository {

    private final AddressPositionJdbcRepository addressPositionJdbcRepository;
    private final AddressPositionJpaRepository addressPositionJpaRepository;

    public AddressPositionRepositoryImpl(JdbcTemplate jdbcTemplate, AddressPositionJpaRepository addressPositionJpaRepository) {
        this.addressPositionJdbcRepository = new AddressPositionJdbcRepository(jdbcTemplate);
        this.addressPositionJpaRepository = addressPositionJpaRepository;
    }

    @Override
    public void insertBatch(List<? extends AddressPosition> entities) {
        addressPositionJdbcRepository.insertBatch(entities);
    }
}
