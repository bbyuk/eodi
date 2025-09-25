package com.bb.eodi.deal.domain.repository;

import com.bb.eodi.deal.domain.dto.RealEstateSellQuery;
import com.bb.eodi.deal.domain.entity.RealEstateSell;
import com.bb.eodi.deal.infrastructure.persistence.RealEstateSellMapperImpl;
import com.bb.eodi.deal.infrastructure.persistence.RealEstateSellRepositoryImpl;
import com.bb.eodi.legaldong.infrastructure.persistence.LegalDongMapperImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@DisplayName("small - 부동산 매매 실거래가 데이터 repository small test")
@Import({
        RealEstateSellRepositoryImpl.class,
        RealEstateSellMapperImpl.class,
        LegalDongMapperImpl.class
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class RealEstateSellRepositoryTest {

    @Autowired
    RealEstateSellRepository realEstateSellRepository;

    @Test
    @DisplayName("small - 부동산 매매 실거래가 데이터 일반 조회 test")
    void testFindByQuery() throws Exception {
        // given

        RealEstateSellQuery query = RealEstateSellQuery.builder()
                .minPrice(50000)
                .maxPrice(70000)
                .build();

        // when
        List<RealEstateSell> results = realEstateSellRepository.findBy(query);

        // then
        Assertions.assertThat(results).isNotEmpty();
    }

}