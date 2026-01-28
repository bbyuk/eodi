package com.bb.eodi.finance.application.service;

import com.bb.eodi.finance.application.input.RegulatingAreaRegisterInput;
import com.bb.eodi.finance.domain.entity.RegulatingArea;
import com.bb.eodi.finance.domain.repository.RegulatingAreaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
@DisplayName("medium - 규제지역서비스 DB 통합테스트")
class RegulatingAreaServiceTest {

    @Autowired
    RegulatingAreaService regulatingAreaService;

    @Autowired
    RegulatingAreaRepository regulatingAreaRepository;

    @Test
    @DisplayName("규제지역 등록 테스트")
    void testRegisterRegulatingArea() {
        // given
        List<String> targets = List.of(
                "서울특별시",
                "경기도 과천시",
                "경기도 광명시",
                "경기도 성남시 분당구",
                "경기도 성남시 수정구",
                "경기도 성남시 중원구",
                "경기도 수원시 영통구",
                "경기도 수원시 장안구",
                "경기도 수원시 팔달구",
                "경기도 안양시 동안구",
                "경기도 용인시 수지구",
                "경기도 의왕시",
                "경기도 하남시"
        );
        RegulatingAreaRegisterInput input = new RegulatingAreaRegisterInput(targets, LocalDate.now(), LocalDate.of(9999, 12, 31));

        // when
        regulatingAreaService.register(input);

        // then

        List<RegulatingArea> allRegulatingArea = regulatingAreaRepository.findAll();
        Assertions.assertThat(allRegulatingArea).isNotEmpty();
    }

}