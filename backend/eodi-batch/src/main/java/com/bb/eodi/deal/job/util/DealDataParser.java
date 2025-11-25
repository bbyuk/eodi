package com.bb.eodi.deal.job.util;

import com.bb.eodi.deal.domain.dto.LandLot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;


/**
 * 실거래 데이터 파서
 */
@Slf4j
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
    public static LandLot parseLandLot(String str) {
        LandLot answer = new LandLot();

        try {
            String[] split = str.split("-");

            // 1. 빈 값 예외
            // ""
            if (!StringUtils.hasText(str)) return answer;

            // 2. 산 예외
            // 산89
            // 산122-21
            answer.setIsMountain(false);
            if (str.startsWith("산")) {
                answer.setIsMountain(true);
                split[0] = split[0].substring(1);
            }

            if (split.length == 1) {
                answer.setLandLotMainNo(Integer.parseInt(split[0]));
            } else {
                answer.setLandLotMainNo(Integer.parseInt(split[0]));
                answer.setLandLotSubNo(Integer.parseInt(split[1]));
            }
        }
        catch(Exception e) {
            log.error(e.getMessage(), e);
            log.error("지번을 파싱하는 도중 에러가 발생했습니다.");
            answer = new LandLot();
        }

        return answer;
    }
}
