package com.bb.eodi.deal.job.partitioner;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;

/**
 * 월별 실거래 데이터 위치정보 매핑 병렬 partitioner
 */
@StepScope
@Component
public class ContractDatePartitioner implements Partitioner {
    private final LocalDate start;
    private final LocalDate end;

    public ContractDatePartitioner(@Value("#{jobParameters['year-month']}") String yearMonth) {
        this.start = LocalDate.of(
                Integer.parseInt(yearMonth.substring(0, 4)),
                Integer.parseInt(yearMonth.substring(4, 6)),
                1);
        this.end = this.start.with(TemporalAdjusters.lastDayOfMonth());
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> result = new HashMap<>();

        long totalDays = end.toEpochDay() - start.toEpochDay() + 1;
        long daysPerPartition = totalDays / gridSize;
        long remainder = totalDays % gridSize;

        LocalDate partitionStart = start;

        for (int i = 0; i < gridSize; i++) {
            long size = daysPerPartition + (i < remainder ? 1 : 0);
            LocalDate partitionEnd = partitionStart.plusDays(size - 1);

            ExecutionContext ctx = new ExecutionContext();
            ctx.putString("start-date", partitionStart.toString());
            ctx.putString("end-date", partitionEnd.toString());
            result.put("partition_" + i, ctx);

            partitionStart = partitionEnd.plusDays(1);
        }

        return result;
    }
}
