package com.bb.eodi.legaldong.domain.util;

import com.bb.eodi.legaldong.job.processor.LegalDongLoadStepItemProcessorV2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("small - 법정동 유틸 테스트")
class LegalDongUtilsTest {
    @Test
    @DisplayName("부모 법정동코드 파싱 로직 테스트")
    void testParseParentCode() throws Exception {
        // given
        String legalDongCode_1 = "1223100000";
        String legalDongCode_2 = "1223102100";
        String legalDongCode_3 = "1200000000";

        // when
        String parentCode_1 = LegalDongUtils.parseParentCode(legalDongCode_1);
        String parentCode_2 = LegalDongUtils.parseParentCode(legalDongCode_2);
        String parentCode_3 = LegalDongUtils.parseParentCode(legalDongCode_3);

        // then
        Assertions.assertThat(parentCode_1).isEqualTo("1200000000");
        Assertions.assertThat(parentCode_2).isEqualTo("1223100000");
        Assertions.assertThat(parentCode_3).isNull();
    }


    @Test
    @DisplayName("법정동명 파싱 로직 테스트")
    void testParseLegalDongName() throws Exception {
        // given
        int n = 4;

        String[] sidoNames = { "경기도", "서울특별시", "서울특별시", "서울특별시" };
        String[] sigunguNames = { "용인시처인구", "종로구", "종로구", "" };
        String[] dongNames = { "양지면", "", "청운동", "" };
        String[] riNames = { "식금리", "", "", "" };

        List<String> actual = List.of(
                "경기도 용인시 처인구 양지면 식금리", "서울특별시 종로구", "서울특별시 종로구 청운동", "서울특별시"
        );

        // when
        List<String> expected = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            expected.add(LegalDongUtils.parseLegalDongName(sidoNames[i], sigunguNames[i], dongNames[i], riNames[i]));
        }

        // then
        Assertions.assertThat(expected).isEqualTo(actual);
    }
}