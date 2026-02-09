package com.bb.eodi.legaldong.infrastructure.cache;

import com.bb.eodi.legaldong.application.result.LegalDongInfo;

import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * 법정동 정보 매퍼
 * 캐시 구성 트리 node -> info 도메인 모델 매핑처리
 */
public class LegalDongInfoNodeMapper {

    public static LegalDongInfo toInfo(LegalDongInfoNode node) {
        if (node.isLeaf()) {
            return new LegalDongInfo(node.getId(),
                    node.getCode(),
                    node.getName(),
                    node.getOrder(),
                    node.getRootId(),
                    node.getSecondId(),
                    node.getParentId(),
                    new HashSet<>()
            );
        }

        return new LegalDongInfo(
                node.getId(),
                node.getCode(),
                node.getName(),
                node.getOrder(),
                node.getRootId(),
                node.getSecondId(),
                node.getParentId(),
                node.getChildren().stream()
                        .map(LegalDongInfoNodeMapper::toInfo)
                        .collect(Collectors.toSet())
        );
    }
}
