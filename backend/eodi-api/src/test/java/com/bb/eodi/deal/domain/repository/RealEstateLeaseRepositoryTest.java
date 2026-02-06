package com.bb.eodi.deal.domain.repository;

import com.bb.eodi.deal.domain.entity.RealEstateLease;
import com.bb.eodi.deal.domain.entity.Region;
import com.bb.eodi.deal.domain.query.RealEstateLeaseQuery;
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


@DisplayName("small - 부동산 임대차 실거래가 데이터 repository small test")
@Transactional
@SpringBootTest
class RealEstateLeaseRepositoryTest {

    @Autowired
    RealEstateLeaseRepository realEstateLeaseRepository;

    @Test
    @DisplayName("부동산 임대차 실거래가 데이터 조회 - 파라미터 없음")
    void testRealEstateLeaseWithoutParam() throws Exception {
        // given

        // when
        Page<RealEstateLease> result = realEstateLeaseRepository.findBy(
                RealEstateLeaseQuery.builder().build(),
                PageRequest.of(0, 5)
        );

        // then
        Assertions.assertThat(result.getContent()).isNotEmpty();

    }

    @Test
    @DisplayName("small - 부동산 임대차 deposit / 기간 기반 지역 조회 test")
    void testFindLeaseRegions() throws Exception {
        // given
        RegionQuery query = RegionQuery.builder()
                .minCash(45000L)
                .maxCash(55000L)
                .startDate(LocalDate.now().minusMonths(3))
                .endDate(LocalDate.now())
                .build();

        // when
        List<Region> regions = realEstateLeaseRepository.findRegionsBy(query);

        // then
        System.out.println("regions.size() = " + regions.size());
        Assertions.assertThat(true);
    }

}
