package com.bb.eodi.legaldong.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * 법정동 JpaRepository
 */
public interface LegalDongJpaRepository extends JpaRepository<LegalDongJpaEntity, Long> {

    /**
     * 법정동 코드로 법정동 조회
     *      법정동 서열 오름차순 정렬
     * @param code 법정동 코드
     * @return 법정동
     */
    @Query("""
            select      ld
            from        LegalDongJpaEntity ld
            where       ld.code = :code
            and         ld.active = true
            order by    ld.legalDongOrder asc
            """)
    Optional<LegalDongJpaEntity> findByCode(@Param("code") String code);
}
