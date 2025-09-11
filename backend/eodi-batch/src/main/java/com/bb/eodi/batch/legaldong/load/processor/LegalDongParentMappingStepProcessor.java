package com.bb.eodi.batch.legaldong.load.processor;

import com.bb.eodi.batch.legaldong.load.model.LegalDongApiResponseRow;
import com.bb.eodi.domain.legaldong.entity.LegalDong;
import com.bb.eodi.domain.legaldong.repository.LegalDongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LegalDongParentMappingStepProcessor implements ItemProcessor<LegalDongApiResponseRow, LegalDong> {

    private final LegalDongRepository legalDongRepository;

    @Override
    public LegalDong process(LegalDongApiResponseRow item) throws Exception {

        LegalDong targetLegalDong = legalDongRepository.findByCode(item.region_cd())
                .orElseThrow(() -> new RuntimeException("대상 데이터를 찾지 못했습니다."));

        legalDongRepository.findByCode(item.locathigh_cd())
                .ifPresentOrElse(
                        parentLegalDong -> targetLegalDong.setParentCode(parentLegalDong.getCode()),
                        () -> {
                            // targetLegalDong code로 처리
                            int sidoCodeLength = 2;
                            int sigunguCodeLength = 3;
                            int dongCodeLength = 3;
                            int riCodeLength = 2;
                            String noneCode = "0";

                            String code = targetLegalDong.getCode();
                            String parentCode = code.substring(0, sidoCodeLength);
                            String sigunguCode = code.substring(
                                    sidoCodeLength,
                                    sidoCodeLength + sigunguCodeLength);
                            String dongCode = code.substring(
                                    sidoCodeLength + sigunguCodeLength,
                                    sidoCodeLength + sigunguCodeLength + dongCodeLength);
                            String riCode = code.substring(
                                    sidoCodeLength + sigunguCodeLength + dongCodeLength,
                                    sidoCodeLength + sigunguCodeLength + dongCodeLength + riCodeLength);

                            if (!noneCode.repeat(riCodeLength).equals(riCode)) {
                                parentCode += sigunguCode + dongCode + noneCode.repeat(riCodeLength);
                            }
                            else if (!noneCode.repeat(dongCodeLength).equals(dongCode)) {
                                parentCode += sigunguCode + noneCode.repeat(dongCodeLength + riCodeLength);
                            }
                            else if (!noneCode.repeat(sigunguCodeLength).equals(sigunguCode)) {
                                parentCode += noneCode.repeat(sigunguCodeLength + dongCodeLength + riCodeLength);
                            }

                            targetLegalDong.setParentCode(parentCode);
                        }
                );

        return targetLegalDong;
    }
}
