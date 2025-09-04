package com.bb.eodi.batch.legaldong.load.processor;

import com.bb.eodi.batch.legaldong.load.model.LegalDongRow;
import com.bb.eodi.domain.legaldong.entity.LegalDong;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 법정동코드 적재 배치 Item Processor
 */
@StepScope
@Component
public class LegalDongRowProcessor implements ItemProcessor<LegalDongRow, List<LegalDong>> {


    private static final String NAME_DELIMITER = " ";

    @Override
    public List<LegalDong> process(LegalDongRow item) throws Exception {
        List<LegalDong> result = new ArrayList<>();

        String inputCode = item.getLegalDongCode();
        String[] inputNameSplit = item.getLegalDongName().split(NAME_DELIMITER);
        String closeYn = item.getCloseYn();

        String sidoCode = inputCode.substring(0, 2);
        String sigunguCode = inputCode.substring(2, 5);
        String dongCode = inputCode.substring(5, 8);
        boolean isActive = "존재".equals(closeYn);

        // 시도
        result.add(new LegalDong(sidoCode, inputNameSplit[0], isActive));

        // 시군구
        if (inputNameSplit.length > 1) {
            result.add(new LegalDong(sigunguCode, inputNameSplit[1], isActive));
        }

        // 동
        if (inputNameSplit.length > 2) {
            result.add(new LegalDong(dongCode, inputNameSplit[2], isActive));
        }

        return result;
    }
}
