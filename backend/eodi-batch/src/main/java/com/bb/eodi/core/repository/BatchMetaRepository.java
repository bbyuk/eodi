package com.bb.eodi.core.repository;

/**
 * 배치 메타 정보 레포지토리 인터페이스
 */
public interface BatchMetaRepository {
    /**
     *
     * @param jobName job 명
     * @param stepName step 명
     * @param targetYearMonth 대상 월
     * @return 대상월 step 성공 여부
     */
    boolean isCompletedMonthlyStep(String jobName, String stepName, String targetYearMonth);
}
