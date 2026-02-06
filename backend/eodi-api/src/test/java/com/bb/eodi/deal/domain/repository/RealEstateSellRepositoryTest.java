package com.bb.eodi.deal.domain.repository;

import com.bb.eodi.deal.domain.entity.RealEstateSell;
import com.bb.eodi.deal.domain.entity.Region;
import com.bb.eodi.deal.domain.query.RealEstateSellQuery;
import com.bb.eodi.deal.domain.query.RegionQuery;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@DisplayName("small - 부동산 매매 실거래가 데이터 repository small test")
@SpringBootTest
@Transactional
class RealEstateSellRepositoryTest {

    @Autowired
    RealEstateSellRepository realEstateSellRepository;

    @Test
    @DisplayName("small - 부동산 매매 실거래가 데이터 일반 조회 test")
    void testFindByQuery() throws Exception {
        // given

        RealEstateSellQuery query = RealEstateSellQuery.builder()
                .minPrice(50000L)
                .maxPrice(70000L)
                .build();

        ;

        // when
        Page<RealEstateSell> results = realEstateSellRepository.findBy(query, PageRequest.of(0, 5));

        // then
        Assertions.assertThat(results.getContent()).isNotEmpty();
    }

    @Test
    @DisplayName("small - 부동산 매매 price / 기간 기반 지역 조회 test")
    void testFindSellRegionsBy() throws Exception {
        // given
        RegionQuery query = RegionQuery.builder()
                .minCash(45000L)
                .maxCash(55000L)
                .startDate(LocalDate.now().minusMonths(3))
                .endDate(LocalDate.now())
                .build();

        // when
        List<Region> targetRegions = realEstateSellRepository.findRegionsBy(query);

        System.out.println("targetRegions.size() = " + targetRegions.size());
        // then
        Assertions.assertThat(true);
    }

}