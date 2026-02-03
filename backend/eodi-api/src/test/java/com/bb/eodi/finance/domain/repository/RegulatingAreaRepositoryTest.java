package com.bb.eodi.finance.domain.repository;

import com.bb.eodi.finance.domain.entity.RegulatingArea;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@SpringBootTest
@DisplayName("medium - 규제지역 Repository DB 연동 테스트")
@Transactional
class RegulatingAreaRepositoryTest {

    @Autowired
    RegulatingAreaRepository regulatingAreaRepository;

    @Test
    @DisplayName("규제지역 전체 저장 테스트")
    void testSaveAll() throws Exception {
        // given
        RegulatingArea seoulJongrogu = RegulatingArea.builder()
                .legalDongId(2L)
                .effectiveStartDate(LocalDate.now())
                .effectiveEndDate(LocalDate.of(9999, 12, 31))
                .build();

        RegulatingArea seoulJoonggu = RegulatingArea.builder()
                .legalDongId(95L)
                .effectiveStartDate(LocalDate.now())
                .effectiveEndDate(LocalDate.of(9999, 12, 31))
                .build();

        // then
        regulatingAreaRepository.saveAll(
                List.of(seoulJongrogu, seoulJoonggu)
        );

        // when
        regulatingAreaRepository.findAll()
                        .stream().map(RegulatingArea::getId)
                        .forEach(id -> Assertions.assertThat(id).isNotNull());
    }

    @Test
    @DisplayName("규제지역여부 조회 테스트 - 규제지역 in")
    void testIsRegulatingArea() throws Exception {
        // given
        Long id = 146L;

        // when
        boolean regulatingArea = regulatingAreaRepository.isRegulatingArea(id);

        // then
        Assertions.assertThat(regulatingArea).isTrue();
    }

    @Test
    @DisplayName("규제지역여부 조회 테스트 - 규제지역 out")
    void testIsNotRegulatingArea() throws Exception {
        // given
        Long id = 0L;

        // when
        boolean isRegulatingArea = regulatingAreaRepository.isRegulatingArea(id);


        // then
        Assertions.assertThat(isRegulatingArea).isFalse();
    }
}