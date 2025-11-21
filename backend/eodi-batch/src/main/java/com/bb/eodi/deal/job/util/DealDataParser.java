package com.bb.eodi.deal.job.util;

import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * 실거래 데이터 파서
 */
public class DealDataParser {

    /**
     * 지번 데이터를 파싱한다
     *
     * input {지번본번}-{지번부번}
     * ex1) "1111-22"
     * ex2) "1111"
     * ex3) ""
     *
     * @return [{지번본번}, {지번부번}] Integer 배열
     */
    public static Integer[] parseJibun(String str) {
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
    }
}
