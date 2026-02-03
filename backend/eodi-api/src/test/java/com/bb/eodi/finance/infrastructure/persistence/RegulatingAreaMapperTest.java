package com.bb.eodi.finance.infrastructure.persistence;

import com.bb.eodi.finance.domain.entity.RegulatingArea;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("medium - 규제지역 mapstruct mapper 테스트")
@SpringBootTest
class RegulatingAreaMapperTest {

    @Autowired
    RegulatingAreaMapper regulatingAreaMapper;

    @Test
    @DisplayName("규제지역 domain entity -> Jpa entity 매핑 테스트")
    void testToJpaEntity() {
        // given
        RegulatingArea seoulJongrogu = RegulatingArea.builder()
                .legalDongId(2L)
                .effectiveStartDate(LocalDate.now())
                .effectiveEndDate(LocalDate.of(9999, 12, 31))
                .build();

        // when
        RegulatingAreaJpaEntity jpaEntity = regulatingAreaMapper.toJpaEntity(seoulJongrogu);

        // then
        Assertions.assertThat(jpaEntity.getLegalDongId()).isEqualTo(2L);
        Assertions.assertThat(jpaEntity.getEffectiveStartDate()).isEqualTo(LocalDate.now());
        Assertions.assertThat(jpaEntity.getEffectiveEndDate()).isEqualTo(LocalDate.of(9999, 12, 31));

    }

}