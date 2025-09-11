package com.bb.eodi.infrastructure.legaldong.repository;

import com.bb.eodi.domain.legaldong.entity.LegalDong;
import com.bb.eodi.domain.legaldong.repository.LegalDongRepository;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 법정동코드 Spring Data Jdbc Repository
 */
@Repository
public interface LegalDongJdbcRepository extends CrudRepository<LegalDong, Long> {

    @Query("""
            SELECT  *
            FROM    legal_dong ld
            WHERE   ld.code = :code
            """)
    Optional<LegalDong> findByCode(@Param("code") String code);
}