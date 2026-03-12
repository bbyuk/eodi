package com.bb.eodi.ops.infrastructure.persistence;

import com.bb.eodi.ops.domain.entity.ReferenceVersion;
import com.bb.eodi.ops.domain.repository.ReferenceVersionRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 기준정보 버전 Repository 구현체
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class ReferenceVersionRepositoryImpl implements ReferenceVersionRepository {

    private final JPAQueryFactory queryFactory;
    private final ReferenceVersionMapper referenceVersionMapper;

    /**
     * 대상 목록 중 최신 버전을 조회한다.
     *
     * @param targetNames 대상명 목록
     * @return
     */
    @Override
    public Optional<LocalDate> findLastUpdateDate(List<String> targetNames) {

        QReferenceVersionJpaEntity referenceVersion = QReferenceVersionJpaEntity.referenceVersionJpaEntity;

        return Optional.ofNullable(
                queryFactory.select(referenceVersion.effectiveDate.min())
                        .from(referenceVersion)
                        .where(referenceVersion.targetName.in(targetNames))
                        .fetchOne()
        );
    }
}
