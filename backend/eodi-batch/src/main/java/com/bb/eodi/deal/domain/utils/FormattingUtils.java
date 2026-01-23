package com.bb.eodi.deal.domain.utils;

import com.bb.eodi.deal.domain.type.DealType;
import com.bb.eodi.deal.domain.type.HousingType;
import com.bb.eodi.deal.job.config.DealJobContextKey;

import java.time.LocalDate;

/**
 * 부동산 실거래가 데이터 포매팅 공통 유틸
 */
public class FormattingUtils {

    /**
     * 파라미터로 주어진 LocalDate를 yyyyMM 형태의 yearMonth로 포매팅한다.
     * @param localDate 포매팅 대상 LocalDate
     * @return yyyyMM
     */
    public static String toYearMonth(LocalDate localDate) {
        return new StringBuilder()
                .append(localDate.getYear())
                .append(String.format("%2s", localDate.getMonthValue()).replace(' ', '0'))
                .toString();
    }

    /**
     * 주택유형, 거래유형으로 기준정보 버전 대상명을 생성한다.
     * @param housingType 주택유형
     * @param dealType 거래유형
     * @return 버전 대상명
     */
    public static String toReferenceVersionTargetName(HousingType housingType, DealType dealType) {
        return housingType.name() + "-" + dealType.name();
    }

    /**
     * prefix, 대상으로 JobExecutionContext key 생성해 리턴한다.
     * @param prefix prefix
     * @param key 대상
     * @return jobExecutionContextKey
     */
    public static String toJobExecutionContextKey(String prefix, DealJobContextKey key) {
        return prefix + "-" + key.name();
    }
}
