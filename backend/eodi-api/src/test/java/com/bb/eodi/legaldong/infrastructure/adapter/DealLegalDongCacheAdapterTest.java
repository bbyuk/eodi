package com.bb.eodi.legaldong.infrastructure.adapter;

import com.bb.eodi.deal.application.contract.LegalDongInfo;
import com.bb.eodi.legaldong.infrastructure.cache.InMemoryLegalDongCache;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DisplayName("medium - 실거래가 - 법정동 adapter 구현 테스트")
class DealLegalDongCacheAdapterTest {

    @Autowired
    InMemoryLegalDongCache legalDongCacheAdapter;

    @Test
    @DisplayName("medium - ID를 키로 캐싱한 법정동 정보 refresh 동작 테스트")
    void refreshCacheTest() throws Exception {
        // given

        // when
        legalDongCacheAdapter.refreshCache();

        // then
        Assertions.assertThat(legalDongCacheAdapter.findUnMappedDataLegalDongInfo()).hasSize(17);
    }

    @Test
    @DisplayName("medium - 코드를 키로 캐싱한 법정동 정보 refresh 동작 테스트")
    void refreshCacheByCodeTest() throws Exception {
        // given
        String code = "1111000000";

        // when
        legalDongCacheAdapter.refreshCache();

        // then
        LegalDongInfo legalDongInfo = legalDongCacheAdapter.findByCode(code);
        Assertions.assertThat(legalDongInfo.name()).isEqualTo("서울특별시 종로구");
    }

    @Test
    @DisplayName("medium - ID를 키로 캐싱한 법정동 정보와 코드를 키로 캐싱한 법정동 정보 동일한 인스턴스인지 테스트")
    void testCachingDataEqualityTest() throws Exception {
        // given
        String code = "1111000000";

        // when
        legalDongCacheAdapter.refreshCache();

        LegalDongInfo byCode = legalDongCacheAdapter.findByCode(code);
        LegalDongInfo byId = legalDongCacheAdapter.findById(byCode.id());

        // then
        Assertions.assertThat(byId).isSameAs(byCode);
    }
}