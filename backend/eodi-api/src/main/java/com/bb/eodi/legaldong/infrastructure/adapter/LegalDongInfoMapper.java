package com.bb.eodi.legaldong.infrastructure.adapter;

import com.bb.eodi.deal.application.model.LegalDongInfo;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * 법정동 정보 매퍼
 * 캐시 구성 트리 node -> info 도메인 모델 매핑처리
 */
public class LegalDongInfoMapper {

    public static LegalDongInfo toInfo(LegalDongInfoNode node) {
        Map<Long, LegalDongInfo> mappedChildren = node.getChildren().entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> toInfo(entry.getValue())
                ));

        return new LegalDongInfo(
                node.getId(),
                node.getCode(),
                node.getName(),
                node.getOrder(),
                node.getParentId(),
                Map.copyOf(mappedChildren)
        );
    }
}
