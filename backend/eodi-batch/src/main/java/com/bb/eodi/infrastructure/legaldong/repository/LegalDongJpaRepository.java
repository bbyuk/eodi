package com.bb.eodi.infrastructure.legaldong.repository;

import com.bb.eodi.domain.legaldong.dto.LegalDongSummaryView;
import com.bb.eodi.domain.legaldong.entity.LegalDong;
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
            """)
    Optional<LegalDong> findByCode(@Param("code") String code);

    @Query("""
            select 
                        ld.id,
                        ld.code,
                        ld.sidoCode,
                        ld.sigunguCode
            from        LegalDong ld
            group by    ld.sidoCode, ld.sigunguCode
            """)
    List<LegalDongSummaryView> findAllSummary();
}