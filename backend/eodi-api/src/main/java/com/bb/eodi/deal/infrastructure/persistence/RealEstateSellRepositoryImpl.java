package com.bb.eodi.deal.infrastructure.persistence;

import com.bb.eodi.deal.application.contract.mapper.LegalDongInfoMapper;
import com.bb.eodi.deal.application.port.DealLegalDongCachePort;
import com.bb.eodi.deal.domain.entity.RealEstateSell;
import com.bb.eodi.deal.domain.entity.Region;
import com.bb.eodi.deal.domain.query.RealEstateSellQuery;
import com.bb.eodi.deal.domain.query.RegionQuery;
import com.bb.eodi.deal.domain.read.QRegionCandidate;
import com.bb.eodi.deal.domain.read.RegionCandidate;
import com.bb.eodi.deal.domain.repository.RealEstateSellRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 부동산 실거래가 데이터 Repository 구현체
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class RealEstateSellRepositoryImpl implements RealEstateSellRepository {

    private final JPAQueryFactory queryFactory;
    private final RealEstateSellMapper mapper;
    private final DealLegalDongCachePort dealLegalDongCachePort;
    private final LegalDongInfoMapper legalDongInfoMapper;

    private final List<RegionCandidate> cache = new ArrayList<>();

    @PostConstruct
    public void init() {
        refreshRegionCandidate();
    }

    /**
     * 매일 오전 10시에 날짜 기준으로 region candidate refresh
     */
    @Scheduled(cron = "0 0 10 * * *", zone = "Asia/Seoul")
    public void refreshRegionCandidate() {
        /**
         * 초기 RegionCandidate 메모리 적재
         */
        QRealEstateSellJpaEntity realEstateSell = QRealEstateSellJpaEntity.realEstateSellJpaEntity;
        BooleanBuilder dynamicCondition = new BooleanBuilder();

        LocalDate today = LocalDate.now();
        cache.addAll(
                queryFactory
                        .select(
                                new QRegionCandidate(
                                        realEstateSell.regionId,
                                        realEstateSell.price,
                                        realEstateSell.housingType,
                                        realEstateSell.contractDate
                                ))
                        .from(realEstateSell)
                        .where(
                                realEstateSell.contractDate.between(today.minusMonths(3), today)
                                        .and(realEstateSell.cancelDate.isNull())
                                        .and(dynamicCondition)
                        )
                        .fetch()
        );
    }


    @Override
    public Page<RealEstateSell> findBy(RealEstateSellQuery query, Pageable pageable) {
        QRealEstateSellJpaEntity realEstateSell = QRealEstateSellJpaEntity.realEstateSellJpaEntity;

        BooleanBuilder condition = new BooleanBuilder();

        if (query.getMaxPrice() != null) {
            condition.and(realEstateSell.price.loe(query.getMaxPrice()));
        }

        if (query.getMinPrice() != null) {
            condition.and(realEstateSell.price.goe(query.getMinPrice()));
        }

        if (query.getMaxNetLeasableArea() != null) {
            condition.and(realEstateSell.netLeasableArea.loe(query.getMaxNetLeasableArea()));
        }

        if (query.getMinNetLeasableArea() != null) {
            condition.and(realEstateSell.netLeasableArea.goe(query.getMinNetLeasableArea()));
        }

        if (query.getEndYearMonth() != null) {
            condition.and(realEstateSell.contractDate.loe(query.getEndYearMonth().atEndOfMonth()));
        }

        if (query.getStartYearMonth() != null) {
            condition.and(realEstateSell.contractDate.goe(query.getStartYearMonth().atDay(1)));
        }

        if (query.getTargetRegionIds() != null && !query.getTargetRegionIds().isEmpty()) {
            condition.and(realEstateSell.regionId.in(query.getTargetRegionIds()));
        }

        if (query.getTargetHousingTypes() != null && !query.getTargetHousingTypes().isEmpty()) {
            condition.and(realEstateSell.housingType.in(query.getTargetHousingTypes()));
        }

        List<RealEstateSell> content = queryFactory
                .selectFrom(realEstateSell)
                .where(condition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(realEstateSell.id.desc())
                .fetch()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        Long total = queryFactory.select(realEstateSell.count())
                .from(realEstateSell)
                .where(condition)
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }

    @Override
    public List<RealEstateSell> findByQueryAfter(RealEstateSellQuery query, Long lastId, int pageSize) {
        QRealEstateSellJpaEntity realEstateSell = QRealEstateSellJpaEntity.realEstateSellJpaEntity;

        BooleanBuilder condition = new BooleanBuilder();

        if (lastId != null) {
            condition.and(realEstateSell.id.lt(lastId)); // 커서 조건
        }

        if (query.getMaxPrice() != null) {
            condition.and(realEstateSell.price.loe(query.getMaxPrice()));
        }

        if (query.getMinPrice() != null) {
            condition.and(realEstateSell.price.goe(query.getMinPrice()));
        }

        if (query.getMaxNetLeasableArea() != null) {
            condition.and(realEstateSell.netLeasableArea.loe(query.getMaxNetLeasableArea()));
        }

        if (query.getMinNetLeasableArea() != null) {
            condition.and(realEstateSell.netLeasableArea.goe(query.getMinNetLeasableArea()));
        }

        if (query.getEndYearMonth() != null) {
            condition.and(realEstateSell.contractDate.loe(query.getEndYearMonth().atEndOfMonth()));
        }

        if (query.getStartYearMonth() != null) {
            condition.and(realEstateSell.contractDate.goe(query.getStartYearMonth().atDay(1)));
        }

        if (query.getTargetRegionIds() != null && !query.getTargetRegionIds().isEmpty()) {
            condition.and(realEstateSell.regionId.in(query.getTargetRegionIds()));
        }

        if (query.getTargetHousingTypes() != null && !query.getTargetHousingTypes().isEmpty()) {
            condition.and(realEstateSell.housingType.in(query.getTargetHousingTypes()));
        }


        return queryFactory
                .selectFrom(realEstateSell)
                .where(condition)
                .orderBy(realEstateSell.id.desc())
                .limit(pageSize + 1)
                .fetch()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Region> findRegionsBy(RegionQuery query) {
        QRealEstateSellJpaEntity realEstateSell = QRealEstateSellJpaEntity.realEstateSellJpaEntity;


        BooleanBuilder condition = new BooleanBuilder();

        condition.and(realEstateSell.price.between(query.getMinCash(), query.getMaxCash()));
        condition.and(realEstateSell.contractDate.between(query.getStartDate(), query.getEndDate()));

        if (!query.getHousingTypes().isEmpty()) {
            condition.and(realEstateSell.housingType.in(query.getHousingTypes()));
        }

        return queryFactory.select(realEstateSell.regionId)
                .from(realEstateSell)
                .where(condition)
                .groupBy(realEstateSell.regionId)
                .having(
                        query.getMinDealCount() != null
                                ? realEstateSell.count().goe(query.getMinDealCount())
                                : null
                )
                .fetch()
                .stream()
                .map(regionId -> legalDongInfoMapper.toEntity(
                        dealLegalDongCachePort.findById(regionId))
                )
                .collect(Collectors.toList());
    }

    @Override
    public Slice<RegionCandidate> findSliceByRegionQuery(RegionQuery query, Pageable pageable) {
        QRealEstateSellJpaEntity realEstateSell = QRealEstateSellJpaEntity.realEstateSellJpaEntity;

        BooleanBuilder dynamicCondition = new BooleanBuilder();

        if (!query.getHousingTypes().isEmpty()) {
            dynamicCondition.and(realEstateSell.housingType.in(query.getHousingTypes()));
        }

        int pageSize = pageable.getPageSize();
        List<RegionCandidate> content = queryFactory
                .select(
                        new QRegionCandidate(
                                realEstateSell.regionId,
                                realEstateSell.price,
                                realEstateSell.housingType,
                                realEstateSell.contractDate
                        ))
                .from(realEstateSell)
                .where(
                        realEstateSell.contractDate.between(query.getStartDate(), query.getEndDate())
                                .and(realEstateSell.price.between(query.getMinCash(), query.getMaxCash()))
                                .and(realEstateSell.cancelDate.isNull())
                                .and(dynamicCondition)
                )
                .orderBy(realEstateSell.id.asc())
                .limit(pageSize + 1)
                .fetch();

        boolean hasNext = content.size() > pageSize;

        if (hasNext) {
            content.remove(pageSize); // N + 1 제거
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Stream<RegionCandidate> findAllRegionCandidates() {
        return cache.stream();
    }
}
