package com.bb.eodi.ops.infrastructure.persistence;

import com.bb.eodi.ops.domain.entity.ReferenceVersion;
import com.bb.eodi.ops.domain.repository.ReferenceVersionRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

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
     * 기준대상명으로 기준정보버전 조회
     *
     * @param targetName 기준대상명
     * @return 기준정보버전 Optional
     */
    @Override
    public Optional<ReferenceVersion> findByTargetName(String targetName) {
        QReferenceVersionJpaEntity referenceVersion = QReferenceVersionJpaEntity.referenceVersionJpaEntity;

        return Optional.ofNullable(
                referenceVersionMapper.toDomain(
                        queryFactory.selectFrom(referenceVersion)
                                .where(referenceVersion.targetName.eq(targetName))
                                .fetchOne()
                )
        );
    }
}
