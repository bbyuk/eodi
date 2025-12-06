package com.bb.eodi.address.infrastructure.persistence;

import com.bb.eodi.address.domain.entity.RoadNameAddress;
import com.bb.eodi.address.domain.repository.RoadNameAddressRepository;
import com.bb.eodi.address.infrastructure.persistence.jdbc.RoadNameAddressJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoadNameAddressRepositoryImpl implements RoadNameAddressRepository {

    private final RoadNameAddressJdbcRepository roadNameAddressJdbcRepository;

    @Override
    public void insertBatch(List<? extends RoadNameAddress> items) {
        roadNameAddressJdbcRepository.insertBatch(items);
    }
}
