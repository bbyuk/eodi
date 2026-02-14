package com.bb.eodi.legaldong.infrastructure.persistence;

import com.bb.eodi.legaldong.domain.query.LegalDongFindQuery;
import com.bb.eodi.legaldong.domain.read.LegalDongWithParentName;
import com.bb.eodi.legaldong.domain.read.QLegalDongWithParentName;
import com.bb.eodi.legaldong.domain.repository.LegalDongQueryRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 법정동 QueryRepository 구현체
 */
@Repository
@RequiredArgsConstructor
public class LegalDongQueryRepositoryImpl implements LegalDongQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final LegalDongMapper mapper;

    @Override
    public List<LegalDongWithParentName> findBy(LegalDongFindQuery query) {
        QLegalDongJpaEntity ld = QLegalDongJpaEntity.legalDongJpaEntity;

        final BooleanBuilder commonCondition = new BooleanBuilder()
                .and(ld.active.isTrue());
        final OrderSpecifier<String> commonOrder = ld.code.asc();


        return switch (query.scope()) {
            case SIDO -> queryFactory.select(new QLegalDongWithParentName(
                            ld.id,
                            ld.code,
                            ld.sidoCode,
                            ld.sigunguCode,
                            ld.dongCode,
                            ld.name,
                            ld.legalDongOrder,
                            ld.parentId,
                            Expressions.nullExpression(String.class),
                            ld.active
                    ))
                    .from(ld)
                    .where(commonCondition
                            .and(ld.parentId.isNull()))
                    .orderBy(commonOrder)
                    .fetch();
            case SIGUNGU -> {
                QLegalDongJpaEntity parent = new QLegalDongJpaEntity("parent");

                yield queryFactory.select(
                        new QLegalDongWithParentName(
                                ld.id,
                                ld.code,
                                ld.sidoCode,
                                ld.sigunguCode,
                                ld.dongCode,
                                ld.name,
                                ld.legalDongOrder,
                                ld.parentId,
                                parent.name,
                                ld.active
                        ))
                        .from(ld)
                        .join(parent)
                        .on(
                                parent.id.eq(ld.parentId)
                                        .and(parent.code.eq(query.baseLegalDongCode()))
                        )
                        .where(commonCondition)
                        .orderBy(commonOrder)
                        .fetch();
            }
        };
    }
}
