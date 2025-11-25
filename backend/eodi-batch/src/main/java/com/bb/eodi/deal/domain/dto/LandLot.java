package com.bb.eodi.deal.domain.dto;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * 지번
 *
 * input {산|}{지번본번}-{지번부번}
 * ex1) "1111-22"
 * ex2) "1111"
 * ex3) ""
 * ex4) "산122-23"
 * ex5) "산12"
 *
 */
@Slf4j
@Getter
public class LandLot {

    public LandLot(String value) {
        this.value = value;

        try {
            String[] split = value.split("-");

            // 1. 빈 값 예외
            // ""
            if (!StringUtils.hasText(value)) {
                return;
            }

            // 2. 산 예외
            // 산89
            // 산122-21
            if (value.startsWith("산")) {
                this.isMountain = true;
                split[0] = split[0].substring(1);
            }

            if (split.length == 1) {
                this.landLotMainNo = Integer.parseInt(split[0]);
            } else {
                this.landLotMainNo = Integer.parseInt(split[0]);
                this.landLotSubNo = Integer.parseInt(split[1]);
            }
            this.isMountain = false;
        }
        catch(Exception e) {
            log.error(e.getMessage(), e);
            log.error("지번을 파싱하는 도중 에러가 발생했습니다.");
            this.landLotMainNo = null;
            this.landLotSubNo = null;
            this.isMountain = null;
            this.correct = false;
        }
    }

    private Integer landLotMainNo;
    private Integer landLotSubNo;
    private Boolean isMountain;

    private String value;
    private boolean correct = true;
}
