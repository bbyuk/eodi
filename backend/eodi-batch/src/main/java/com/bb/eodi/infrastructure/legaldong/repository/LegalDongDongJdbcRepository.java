package com.bb.eodi.infrastructure.legaldong.repository;

import com.bb.eodi.domain.legaldong.entity.Dong;
import com.bb.eodi.domain.legaldong.repository.LegalDongDongRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 법정동코드 동 Spring Data Jdbc Repository
 */
@Repository
public interface LegalDongDongJdbcRepository extends LegalDongDongRepository, CrudRepository<Dong, Long> {
}
