package com.bb.eodi.legaldong.infrastructure.persistence.jpa;

import com.bb.eodi.legaldong.domain.dto.LegalDongSummaryDto;
import com.bb.eodi.legaldong.domain.entity.LegalDong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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
            and     ld.isActive = true
            """)
    Optional<LegalDong> findByCode(@Param("code") String code);

    @Query("""
            select  distinct new com.bb.eodi.legaldong.domain.dto.LegalDongSummaryDto(
                            ld.sidoCode,
                            ld.sigunguCode
                    )
            from    LegalDong ld
            where   ld.isActive = true
            """)
    List<LegalDongSummaryDto> findAllSummary();
}