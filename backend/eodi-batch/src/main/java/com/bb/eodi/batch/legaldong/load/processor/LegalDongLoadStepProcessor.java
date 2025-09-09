package com.bb.eodi.batch.legaldong.load.processor;

import com.bb.eodi.batch.legaldong.load.model.LegalDongApiResponseRow;
import com.bb.eodi.domain.legaldong.entity.LegalDong;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * 법정동 코드 적재 배치 processor
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LegalDongLoadStepProcessor implements ItemProcessor<LegalDongApiResponseRow, LegalDong> {

    @Override
    public LegalDong process(LegalDongApiResponseRow readItem) throws Exception {
        log.debug("load processing = {}", readItem);
        // parentId는 다음 step에서 처리
        return LegalDong.builder()
                .code(readItem.region_cd())
                .sidoCode(readItem.sido_cd())
                .sigunguCode(readItem.sgg_cd())
                .dongCode(readItem.umd_cd())
                .name(readItem.locatadd_nm())
                .legalDongOrder(readItem.locat_order())
                .isActive(true)
                .build();
    }
}
