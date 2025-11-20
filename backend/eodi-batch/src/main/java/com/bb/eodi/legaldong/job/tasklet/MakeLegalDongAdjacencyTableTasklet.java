package com.bb.eodi.legaldong.job.tasklet;

import com.bb.eodi.legaldong.domain.entity.LegalDongAdjacency;
import com.bb.eodi.legaldong.domain.repository.LegalDongAdjacencyRepository;
import com.bb.eodi.legaldong.domain.repository.LegalDongRepository;
import com.bb.eodi.legaldong.domain.service.ShapeFileReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * 법정동코드 인접 정보 테이블 생성 Tasklet
 */
@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class MakeLegalDongAdjacencyTableTasklet implements Tasklet {

    private final ShapeFileReader shapeFileReader;
    private final LegalDongRepository legalDongRepository;
    private final LegalDongAdjacencyRepository legalDongAdjacencyRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        Map<String, Set<String>> adjacencyMap = shapeFileReader.makeAdjacencyMap();

        for (Map.Entry<String, Set<String>> entry : adjacencyMap.entrySet()) {
            String sourceLegalDongCode = entry.getKey();
            legalDongRepository.findByCode(sourceLegalDongCode)
                    .ifPresentOrElse(
                            sourceLegalDong -> {
                                for (String targetLegalDongCode : entry.getValue()) {

                                    legalDongRepository.findByCode(targetLegalDongCode)
                                            .ifPresentOrElse(
                                                    targetLegalDong
                                                            -> legalDongAdjacencyRepository.save(LegalDongAdjacency
                                                            .builder()
                                                            .sourceId(sourceLegalDong.getId())
                                                            .targetId(targetLegalDong.getId())
                                                            .build()),
                                                    () -> { /* do nothing */}
                                            );
                                }
                            },
                            () -> {
                                /* do nothing */
                            }
                    );
        }

        return RepeatStatus.FINISHED;
    }
}
