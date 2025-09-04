package com.bb.eodi.infrastructure.legaldong.repository;

import com.bb.eodi.domain.legaldong.entity.LegalDong;
import com.bb.eodi.domain.legaldong.repository.LegalDongRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 법정동코드 Spring Data Jdbc Repository
 */
@Repository
public interface LegalDongJdbcRepository extends LegalDongRepository, CrudRepository<Long, LegalDong> {
}
