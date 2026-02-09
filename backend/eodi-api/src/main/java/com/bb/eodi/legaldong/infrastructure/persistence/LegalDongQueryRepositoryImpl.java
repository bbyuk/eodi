package com.bb.eodi.legaldong.infrastructure.persistence;

import com.bb.eodi.legaldong.domain.entity.LegalDong;
import com.bb.eodi.legaldong.domain.query.LegalDongFindQuery;
import com.bb.eodi.legaldong.domain.read.LegalDongQueryRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 법정동 QueryRepository 구현체
 */
@Repository
@RequiredArgsConstructor
public class LegalDongQueryRepositoryImpl implements LegalDongQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final LegalDongMapper mapper;

    @Override
    public List<LegalDong> findBy(LegalDongFindQuery query) {
        QLegalDongJpaEntity ld = QLegalDongJpaEntity.legalDongJpaEntity;

        final BooleanBuilder commonCondition = new BooleanBuilder()
                .and(ld.active.isTrue());
        final OrderSpecifier<Integer> commonOrder = ld.legalDongOrder.asc();


        return switch (query.scope()) {
            case SIDO -> queryFactory.selectFrom(ld)
                    .where(commonCondition
                            .and(ld.parentId.isNull()))
                    .orderBy(commonOrder)
                    .stream().map(mapper::toDomain)
                    .collect(Collectors.toList());
            case SIGUNGU -> {
                QLegalDongJpaEntity parent = new QLegalDongJpaEntity("parent");

                yield queryFactory.select(ld)
                    .from(ld)
                    .join(parent)
                        .on(
                                parent.id.eq(ld.parentId)
                                        .and(parent.code.eq(query.baseLegalDongCode()))
                        )
                    .where(commonCondition)
                    .orderBy(commonOrder)
                    .stream().map(mapper::toDomain)
                    .collect(Collectors.toList());
            }
            case ALL -> queryFactory.selectFrom(ld)
                    .where(commonCondition)
                    .orderBy(commonOrder)
                    .stream().map(mapper::toDomain)
                    .collect(Collectors.toList());
        };
    }
}
