package com.bb.eodi.ops.infrastructure.persistence;

import com.bb.eodi.ops.domain.entity.ReferenceVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

/**
 * 기준정보버전 JPA Repository
 */
public interface ReferenceVersionJpaRepository extends JpaRepository<ReferenceVersion, Long> {


    /**
     * 대상 기준정보버전의 effective_date를 기준정보대상명을 조건으로 수정한다.
     * @param value 기준정보버전 effective_date
     * @param referenceVersionName 기준정보대상명
     */
    @Modifying
    @Query("""
            update ReferenceVersion rv
            set    rv.effectiveDate = :value
            where  rv.targetName = :referenceVersionName
            """)
    void updateEffectiveDateByReferenceVersionName(
            @Param("value") LocalDate value, @Param("referenceVersionName") String referenceVersionName);
}
