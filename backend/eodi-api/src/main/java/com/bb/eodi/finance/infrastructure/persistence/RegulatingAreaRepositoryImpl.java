package com.bb.eodi.finance.infrastructure.persistence;

import com.bb.eodi.finance.domain.entity.RegulatingArea;
import com.bb.eodi.finance.domain.repository.RegulatingAreaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 규제지역 repository 구현
 */
@Repository
@RequiredArgsConstructor
public class RegulatingAreaRepositoryImpl implements RegulatingAreaRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    private final RegulatingAreaMapper regulatingAreaMapper;

    @Override
    public List<RegulatingArea> findAll() {
        QRegulatingAreaJpaEntity regulatingArea = QRegulatingAreaJpaEntity.regulatingAreaJpaEntity;

        return queryFactory.selectFrom(regulatingArea)
                .where(
                        regulatingArea.effectiveStartDate.loe(LocalDate.now())
                                .and(regulatingArea.effectiveEndDate.goe(LocalDate.now()))
                )
                .where()
                .fetch()
                .stream()
                .map(regulatingAreaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void saveAll(Collection<? extends RegulatingArea> entities) {
        int batchSize = 100;
        AtomicInteger count = new AtomicInteger(0);

        entities.stream()
                .map(regulatingAreaMapper::toJpaEntity)
                .forEach(entity -> {
                    entity.audit();

                    em.persist(entity);
                    if (count.incrementAndGet() % batchSize == 0) {
                        em.flush();
                        em.clear();
                    }
                });
    }
}
