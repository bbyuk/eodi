package com.bb.eodi.legaldong.infrastructure.adapter;

import com.bb.eodi.deal.application.contract.LegalDongInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({InMemoryLegalDongCacheAdapter.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InMemoryLegalDongCacheAdapterTest {

    @Autowired
    InMemoryLegalDongCacheAdapter legalDongCacheAdapter;

    @Test
    void refreshCacheTest() throws Exception {
        // given

        // when
        legalDongCacheAdapter.refreshCache();

        // then
        Assertions.assertThat(legalDongCacheAdapter.findUnMappedDataLegalDongInfo()).hasSize(17);
    }

    @Test
    @DisplayName("medium - 코드를 키로 캐싱한 법정동 refresh 동작 테스트")
    void refreshCacheByCodeTest() throws Exception {
        // given
        String code = "1111000000";

        // when
        legalDongCacheAdapter.refreshCache();

        // then
        LegalDongInfo legalDongInfo = legalDongCacheAdapter.findByCode(code);
        Assertions.assertThat(legalDongInfo.name()).isEqualTo("서울특별시 종로구");
    }

}