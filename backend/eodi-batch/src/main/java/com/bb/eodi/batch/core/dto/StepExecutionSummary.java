package com.bb.eodi.batch.core.dto;

import com.bb.eodi.batch.core.enums.BatchExecutionStatus;

import java.time.LocalDateTime;

/**
 * 배치 Step 실행 요약 정보 Dto
 */
public record StepExecutionSummary(
        // 배치 JobExecution ID
        Long jobExecutionId,
        // 배치 JobInstance ID
        Long jobInstanceId,
        // 배치 Job 명
        String jobName,
        // 배치 JOB 상태
        BatchExecutionStatus jobStatus,
        // 배치 Step 명
        String stepName,
        // 배치 Step 상태
        BatchExecutionStatus stepExitCode,
        // read 데이터 수
        Long readCount,
        // write 데이터 수
        Long writeCount,
        // 커밋 횟수
        Long commitCount,
        // 롤백 횟수
        Long rollbackCount,
        // 배치 Job Execution 생성 시간
        LocalDateTime createdTime,
        // 배치 Job 시작 시간
        LocalDateTime startTime,
        // 배치 Job 종료 시간
        LocalDateTime endTime,
        // 배치 Job 최종 수정 시간
        LocalDateTime lastUpdated
) {
}
