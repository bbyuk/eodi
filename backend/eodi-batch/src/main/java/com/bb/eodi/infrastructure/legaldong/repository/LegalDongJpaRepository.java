package com.bb.eodi.infrastructure.legaldong.repository;

import com.bb.eodi.domain.legaldong.entity.LegalDong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 법정동코드 Spring Data Jdbc Repository
 */
@Repository
public interface LegalDongJpaRepository extends JpaRepository<LegalDong, Long> {

    @Query("""
            select  ld
            from    LegalDong ld
            where   ld.code = :code
            """)
    Optional<LegalDong> findByCode(@Param("code") String code);
}