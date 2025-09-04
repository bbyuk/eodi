package com.bb.eodi.infrastructure.legaldong.repository;

import com.bb.eodi.domain.legaldong.entity.Sigungu;
import com.bb.eodi.domain.legaldong.repository.LegalDongSigunguRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 법정동코드 시/군/구 Spring Data Jdbc Repository
 */
@Repository
public interface LegalDongSigunguJdbcRepository extends LegalDongSigunguRepository, CrudRepository<Sigungu, Long> {
}
