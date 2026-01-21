package com.bb.eodi.legaldong.domain.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@DisplayName("small - 법정동 유틸 테스트")
class LegalDongUtilsTest {
    @Test
    @DisplayName("부모 법정동코드 파싱 로직 테스트")
    void testParseParentCode() throws Exception {
        // given
        String[] legalDongCodes = {"1223100000", "1223102100", "1200000000", "2671025021"};

        // when
        List<String> result = Arrays.stream(legalDongCodes)
                .map(LegalDongUtils::parseParentCode)
                .collect(Collectors.toList());

        List<String> answer = new ArrayList<>(Collections.nCopies(4, null));
        answer.set(0, "1200000000");
        answer.set(1,"1223100000");
        answer.set(3,  "2671025000");

        // then
        Assertions.assertThat(result).isEqualTo(answer);
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

        List<String> answer = List.of(
                "경기도 용인시 처인구 양지면 식금리", "서울특별시 종로구", "서울특별시 종로구 청운동", "서울특별시"
        );

        // when
        List<String> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result.add(LegalDongUtils.parseLegalDongName(sidoNames[i], sigunguNames[i], dongNames[i], riNames[i]));
        }

        // then
        Assertions.assertThat(result).isEqualTo(answer);
    }
}