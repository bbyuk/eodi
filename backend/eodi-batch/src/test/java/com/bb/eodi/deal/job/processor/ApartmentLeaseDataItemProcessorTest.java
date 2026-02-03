package com.bb.eodi.deal.job.processor;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("small - 아파트 매매 데이터 Chunk 적재 ItemProcessor 테스트")
class ApartmentLeaseDataItemProcessorTest {


    @Test
    @DisplayName("small - 지번 파싱 로직 테스트")
    void testParseJibun() throws Exception {
        // given
        String jibun1 = "2123-12";
        String jibun2 = "2231";
        String jibun3 = "";

        Function<String, Integer[]> parser = (str) -> {
            String[] split = str.split("-");
            Integer[] answer = new Integer[2];

            if (!StringUtils.hasText(str)) return answer;

            if (split.length == 1) {
                answer[0] = Integer.parseInt(split[0]);
            }
            else {
                answer = Arrays.stream(split).map(Integer::parseInt).toArray(Integer[]::new);
            }
            return answer;
        };

        // when
        Integer[] answer1 = parser.apply(jibun1);
        Integer[] answer2 = parser.apply(jibun2);
        Integer[] answer3 = parser.apply(jibun3);

        // then
        Assertions.assertThat(answer1[0]).isEqualTo(2123);
        Assertions.assertThat(answer1[1]).isEqualTo(12);

        Assertions.assertThat(answer2[0]).isEqualTo(2231);
        Assertions.assertThat(answer2[1]).isNull();

        Assertions.assertThat(answer3[0]).isNull();
        Assertions.assertThat(answer3[1]).isNull();

    }

}