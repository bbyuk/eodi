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
     * input {산|}{지번본번}-{지번부번}
     * ex1) "1111-22"
     * ex2) "1111"
     * ex3) ""
     * ex4) "산122-23"
     * ex5) "산12"
     *
     * @return [{지번본번}, {지번부번}, {산여부(1(산)|0}] Integer 배열
     */
    public static Integer[] parseJibun(String str) {
        String[] split = str.split("-");
        Integer[] answer = new Integer[3];

        // 1. 빈 값 예외
        // ""
        if (!StringUtils.hasText(str)) return answer;

        // 2. 산 예외
        // 산89
        // 산122-21
        answer[2] = 0;
        if (str.startsWith("산")) {
            answer[2] = 1;
            split[0] = split[0].substring(1);
        }

        if (split.length == 1) {
            answer[0] = Integer.parseInt(split[0]);
        }
        else {
            answer[0] = Integer.parseInt(split[0]);
            answer[1] = Integer.parseInt(split[1])
        }
        return answer;
    }
}
