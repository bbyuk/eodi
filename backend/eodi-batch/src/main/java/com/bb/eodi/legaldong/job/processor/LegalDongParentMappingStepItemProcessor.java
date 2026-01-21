package com.bb.eodi.legaldong.job.processor;

import com.bb.eodi.legaldong.domain.entity.LegalDong;
import com.bb.eodi.legaldong.domain.repository.LegalDongRepository;
import com.bb.eodi.legaldong.domain.util.LegalDongUtils;
import com.bb.eodi.legaldong.job.dto.LegalDongItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * 법정동 부모 매핑 Step ItemProcessor
 */
@Slf4j
@Component
@StepScope
@RequiredArgsConstructor
public class LegalDongParentMappingStepItemProcessor implements ItemProcessor<LegalDongItem, LegalDong> {


    private final LegalDongRepository legalDongRepository;

    @Override
    public LegalDong process(LegalDongItem item) throws Exception {

        LegalDong targetLegalDong = legalDongRepository.findAnyByCode(item.getLegalDongCode())
                .orElseThrow(() -> new RuntimeException("대상 데이터를 찾지 못했습니다. ====== " + item.getLegalDongCode()));

        legalDongRepository.findByCode(LegalDongUtils.parseParentCode(item.getLegalDongCode()))
                .ifPresentOrElse(
                        parentLegalDong -> targetLegalDong.setParentCode(parentLegalDong.getCode()),
                        () -> targetLegalDong.setParentCode(LegalDongUtils.parseParentCode(targetLegalDong.getCode()))
                );

        return targetLegalDong;
    }
}
