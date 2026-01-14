package com.bb.eodi.ops.infrastructure.persistence;

import com.bb.eodi.ops.domain.entity.QReferenceVersion;
import com.bb.eodi.ops.domain.entity.ReferenceVersion;
import com.bb.eodi.ops.domain.repository.ReferenceVersionRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 기준정보 버전 Repository 구현체
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ReferenceVersionRepositoryImpl implements ReferenceVersionRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 기준대상명으로 기준정보버전 조회
     *
     * @param targetName 기준대상명
     * @return 기준정보버전 Optional
     */
    @Override
    public Optional<ReferenceVersion> findByTargetName(String targetName) {
        QReferenceVersion referenceVersion = QReferenceVersion.referenceVersion;

        return Optional.ofNullable(
                queryFactory.selectFrom(referenceVersion)
                        .where(referenceVersion.targetName.eq(targetName))
                        .fetchOne());
    }
}
