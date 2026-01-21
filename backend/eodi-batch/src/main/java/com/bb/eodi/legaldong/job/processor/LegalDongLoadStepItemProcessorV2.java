package com.bb.eodi.legaldong.job.processor;

import com.bb.eodi.legaldong.domain.entity.LegalDong;
import com.bb.eodi.legaldong.domain.util.LegalDongUtils;
import com.bb.eodi.legaldong.job.dto.LegalDongItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static com.bb.eodi.legaldong.domain.util.LegalDongUtils.*;

/**
 * 국토교통부 공시 파일 단위 법정동 ItemProcessor
 */
@Slf4j
@Component
@StepScope
public class LegalDongLoadStepItemProcessorV2 implements ItemProcessor<LegalDongItem, LegalDong> {

    @Override
    public LegalDong process(LegalDongItem item) throws Exception {
        log.debug("load processing = {}", item);
        int cursor = 0;

        // parentId는 다음 step에서 처리
        return LegalDong.builder()
                .code(item.getLegalDongCode())
                .sidoCode(parseSidoCode(item.getLegalDongCode()))
                .sigunguCode(parseSigunguCode(item.getLegalDongCode()))
                .dongCode(parseDongCode(item.getLegalDongCode()))
                .name(parseLegalDongName(item.getSidoName(), item.getSigunguName(), item.getUmdName(), item.getRiName()))
                .parentCode(parseParentCode(item.getLegalDongCode()))
                .legalDongOrder(Integer.parseInt(item.getLegalDongOrder()))
                .isActive(!StringUtils.hasText(item.getRevocationDate()))
                .build();
    }
}
