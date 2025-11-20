package com.bb.eodi.core.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 배치 메타 정보 Repository 구현체
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class BatchMetaRepositoryImpl implements BatchMetaRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public boolean isCompletedMonthlyStep(String jobName, String stepName, String targetYearMonth) {
        log.debug("BatchMetaRepositoryImpl:::isCompletedMonthlyStep({}, {}, {})", jobName, stepName, targetYearMonth);
        String sql = """
                SELECT	
                        COUNT(1)
                 FROM	BATCH_JOB_EXECUTION bje
                 JOIN 	BATCH_JOB_INSTANCE bji 			
                		ON bje.JOB_INSTANCE_ID = bji.JOB_INSTANCE_ID
                 JOIN	BATCH_STEP_EXECUTION bse 		
                		ON bje.JOB_EXECUTION_ID = bse.JOB_EXECUTION_ID
                 JOIN	BATCH_JOB_EXECUTION_PARAMS bjep
                		ON bje.JOB_EXECUTION_ID  = bjep.JOB_EXECUTION_ID
                WHERE 	1=1
                  AND	bse.EXIT_CODE = 'COMPLETED'
                  AND	bjep.PARAMETER_NAME = 'year-month'
                  AND	bji.JOB_NAME = :jobName
                  AND   bse.STEP_NAME = :stepName
                  AND	bjep.PARAMETER_VALUE = :targetYearMonth
                """;

        Map<String, Object> params = Map.of(
                "jobName", jobName,
                "stepName", stepName,
                "targetYearMonth", targetYearMonth
        );

        return 0 < jdbcTemplate.queryForObject(sql, params, Integer.class);
    }
}
