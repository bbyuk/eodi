package com.bb.eodi.legaldong.application.service;

import com.bb.eodi.legaldong.application.input.RegionFindInput;
import com.bb.eodi.legaldong.application.result.LegalDongFindResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("medium - 법정동 조회 DB 통합 테스트")
@SpringBootTest
class LegalDongServiceTest {

    @Autowired
    LegalDongService legalDongService;


    @Test
    @DisplayName("최상위 법정동 목록 조회 - 시도 조회 / 전국 시군구 17개 시도")
    void testFindAllRootLegalDongs() throws Exception {
        // given

        long before = System.currentTimeMillis();

        // when
        List<LegalDongFindResult> allRootLegalDongs = legalDongService.findAllRootLegalDongs();

        long after = System.currentTimeMillis();

        // then
        Assertions.assertThat(allRootLegalDongs).hasSize(17);

        // 50ms 내에 서비스 메소드 완료
        Assertions.assertThat(after - before).isLessThan(70);
    }

    @Test
    @DisplayName("지역 목록 조회 - 시군구 조회 / 시도별 시군구")
    void testFindRegions() throws Exception {
        // given
        String code = "1100000000";

        // when
        long before = System.currentTimeMillis();
        List<LegalDongFindResult> allRegionLegalDongs = legalDongService.findAllRegionLegalDongs(new RegionFindInput(code));
        long after = System.currentTimeMillis();
        // then

        Assertions.assertThat(allRegionLegalDongs).isNotEmpty();
        Assertions.assertThat(after - before).isLessThan(70);

    }
}