package com.bb.eodi.infrastructure.legaldong.repository;

import com.bb.eodi.domain.legaldong.dto.LegalDongSummaryDto;
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
            select  distinct new com.bb.eodi.domain.legaldong.dto.LegalDongSummaryDto(
                            ld.sidoCode,
                            ld.sigunguCode
                    )
            from    LegalDong ld
            """)
    List<LegalDongSummaryDto> findAllSummary();

    // 이름으로 대상 legaldong을 찾고 대상 legaldong code의 앞 5자리 + 0 5자리인 코드를 갖고 있는
    @Query(value = """
            SELECT
                    ld_1.id,
                    ld_1.code,
                    ld_1.sido_code,
                    ld_1.sigungu_code,
                    ld_1.dong_code,
                    ld_1.name,
                    ld_1.legal_dong_order,
                    ld_1.parent_id,
                    ld_1.is_active,
                    ld_1.created_at,
                    ld_1.updated_at
            FROM    legal_dong ld_1
            WHERE   ld_1.code =
                        (
                            SELECT  RPAD(SUBSTR(ld_2.code, 1, 5), 10, '0')
                            FROM    legal_dong ld_2
                            WHERE   REPLACE(ld_2.name, ' ', '') = REPLACE(:name, ' ', '')
                            LIMIT   1
                        )
            """, nativeQuery = true)
    Optional<LegalDong> findTopSigunguCodeByName(@Param("name") String name);
}