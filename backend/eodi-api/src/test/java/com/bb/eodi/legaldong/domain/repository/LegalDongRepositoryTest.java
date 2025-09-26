package com.bb.eodi.legaldong.domain.repository;

import com.bb.eodi.legaldong.infrastructure.persistence.LegalDongMapperImpl;
import com.bb.eodi.legaldong.infrastructure.persistence.LegalDongRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({LegalDongRepositoryImpl.class, LegalDongMapperImpl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LegalDongRepositoryTest {
    @Autowired
    LegalDongRepository legalDongRepository;

    @Test
    @DisplayName("small - 법정동 코드로 조회")
    void findLegalDongWithCode() throws Exception {
        // given
        String targetCode = "2717010800";
        Long answerId = 4L;

        // when
        legalDongRepository.findByCode(targetCode).ifPresent(
                legalDong -> Assertions.assertThat(legalDong.getId()).isEqualTo(answerId)
        );

        // then
    }
}