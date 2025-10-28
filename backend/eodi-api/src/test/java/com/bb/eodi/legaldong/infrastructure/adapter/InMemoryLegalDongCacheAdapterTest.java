package com.bb.eodi.legaldong.infrastructure.adapter;

import org.assertj.core.api.Assertions;
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

}