package com.bb.eodi.infrastructure.legaldong.repository;

import com.bb.eodi.domain.legaldong.entity.LegalDong;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("small - 법정동 JpaRepository 데이터 조회 테스트")
@Import({
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class LegalDongJpaRepositoryTest {
    @Autowired
    LegalDongJpaRepository legalDongJpaRepository;


    @Test
    @DisplayName("법정동 명으로 최상위 시군구 코드를 리턴한다.")
    public void testFindTopSigunguCode() throws Exception {
        // given
        String name = "서울특별시 강남구 대치동";

        // when
        LegalDong legalDong = legalDongJpaRepository.findTopSigunguCodeByName(name).get();

        // then

        Assertions.assertThat(legalDong.getCode()).isEqualTo("1168000000");

    }
}