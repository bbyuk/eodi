package com.bb.eodi.legaldong.domain.dto;

import com.bb.eodi.legaldong.domain.repository.LegalDongCacheRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@DisplayName("medium - 법정동 정보 법정동 postfix 자손노드 검색기능 테스트")
class LegalDongInfoDtoTest {

    @Autowired
    LegalDongCacheRepository cacheRepository;


    @Test
    void testFindLegalDong() {
        // given
        Long regionId = 14618L;
        String legalDongName = "숭인동";

        LegalDongInfoDto targetRegionLegalDongInfoDto = cacheRepository.findLegalDongInfoById(regionId)
                .orElseThrow(() -> new RuntimeException("대상 지역 법정동 정보를 찾지 못했습니다."));

        // when
        Optional<LegalDongInfoDto> subtreeNode = targetRegionLegalDongInfoDto.findSubtreeNode(legalDongName);

        // then
        Assertions.assertThat(subtreeNode.isPresent()).isTrue();
    }

    @Test
    @DisplayName("서브트리 노드 목록 조회 메소드 테스트")
    void testFindSubtreeNodes() {
        // given
        String legalDongCode = "2771025600";

        // when
        LegalDongInfoDto legalDongInfoDto = cacheRepository.findLegalDongInfoByCode(legalDongCode).orElseThrow(() -> new RuntimeException("법정동을 찾지 못했습니다."));

        // then
        List<LegalDongInfoDto> subtreeNodes = legalDongInfoDto.findSubtreeNodes();
        Assertions.assertThat(subtreeNodes).isNotEmpty();
    }
}