package com.bb.eodi.address.infrastructure.persistence;

import com.bb.eodi.address.domain.entity.RoadNameAddress;
import com.bb.eodi.address.domain.repository.RoadNameAddressRepository;
import com.bb.eodi.address.infrastructure.persistence.jdbc.RoadNameCodeJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 도로명코드 Repository 구현체
 */
@Repository
@RequiredArgsConstructor
public class RoadNameCodeRepositoryImpl implements RoadNameAddressRepository {

    private final RoadNameCodeJdbcRepository roadNameCodeJdbcRepository;

    @Override
    public void insertBatch(List<? extends RoadNameAddress> items) {

    }
}
