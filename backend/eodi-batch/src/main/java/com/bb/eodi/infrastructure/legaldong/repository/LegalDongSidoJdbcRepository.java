package com.bb.eodi.infrastructure.legaldong.repository;

import com.bb.eodi.domain.legaldong.entity.Sido;
import com.bb.eodi.domain.legaldong.repository.LegalDongSidoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 법정동 코드 시/도 Spring Data Jdbc Repository
 */
@Repository
public interface LegalDongSidoJdbcRepository extends LegalDongSidoRepository, CrudRepository<Sido, Long> {
}
