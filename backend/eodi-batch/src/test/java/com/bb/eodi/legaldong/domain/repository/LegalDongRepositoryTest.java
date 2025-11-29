package com.bb.eodi.legaldong.domain.repository;

import com.bb.eodi.legaldong.domain.entity.LegalDong;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@DisplayName("medium - 법정동 Repository DB 연동 테스트")
class LegalDongRepositoryTest {

    @Autowired
    LegalDongRepository legalDongRepository;


    @Test
    @DisplayName("조회 - 시도코드, 시군구코드, 법정동명으로 법정동 코드 조회")
    void testFindBySidoCodeAndSigunguCodeAndLegalDongName() throws Exception {
        // given
        String sidoCode = "27";
        String sigunguCode = "170";
        String legalDongName = "평리동";

        // when
        Optional<LegalDong> optional = legalDongRepository.findBySidoCodeAndSigunguCodeAndLegalDongName(
                sidoCode,
                sigunguCode,
                "대구광역시 서구 " + legalDongName
        );

        // then
        Assertions.assertThat(optional.isPresent()).isTrue();
        Assertions.assertThat(optional.get().getId()).isEqualTo(10L);

    }
}