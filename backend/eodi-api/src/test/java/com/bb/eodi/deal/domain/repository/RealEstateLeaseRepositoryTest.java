package com.bb.eodi.deal.domain.repository;

import com.bb.eodi.config.QuerydslConfig;
import com.bb.eodi.deal.domain.dto.RealEstateLeaseQuery;
import com.bb.eodi.deal.domain.entity.RealEstateLease;
import com.bb.eodi.deal.infrastructure.persistence.RealEstateLeaseMapper;
import com.bb.eodi.deal.infrastructure.persistence.RealEstateLeaseMapperImpl;
import com.bb.eodi.deal.infrastructure.persistence.RealEstateLeaseRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("small - 부동산 임대차 실거래가 데이터 repository small test")
@Import({
        RealEstateLeaseRepositoryImpl.class,
        QuerydslConfig.class,
        RealEstateLeaseMapperImpl.class
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
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

}