package com.bb.eodi.legaldong.domain.dto;


import lombok.Getter;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * 크로스도메인 법정동 정보 트리 노드
 * use only at Adapter
 */
@Getter
public class LegalDongInfoDto {
    private final Long id;
    private final String code;
    private final String name;
    private final int order;
    private final Long parentId;

    private Long rootId;
    private Long secondId;
    private LegalDongInfoDto parent;
    private final Set<LegalDongInfoDto> children = new HashSet<>();

    public LegalDongInfoDto(Long id, String code, String name, int order, Long parentId) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.order = order;
        this.parentId = parentId;

        if (parentId == null) {
            this.rootId = id;
        }
    }

    /**
     * 부모 노드와 현재 트리를 연결한다.
     * @param parent 부모 노드
     */
    public void connectToParent(LegalDongInfoDto parent) {
        this.parent = parent;
        parent.children.add(this);
    }

    public void updateSecondId(Long id) {
        this.secondId = id;
    }

    public void updateRootId(Long id) {
        this.rootId = id;
    }

    public boolean isRoot() {
        return id.equals(rootId);
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    /**
     * 자식 노드 중 부동산 실거래가 데이터에 포함된 법정동명으로 끝나는 법정동 정보 dto 노드를 찾아 리턴한다.
     * @param legalDongName 법정동명
     * @return 대상 법정동
     */
    public Optional<LegalDongInfoDto> findSubtreeNode(String legalDongName) {
        if (name.endsWith(" " + legalDongName)) {
            return Optional.of(this);
        }

        if (getChildren().isEmpty()) {
            return Optional.empty();
        }

        for (LegalDongInfoDto child : getChildren()) {
            Optional<LegalDongInfoDto> result = child.findSubtreeNode(legalDongName);
            if (result.isPresent()) {
                return result;
            }
        }

        return Optional.empty();
    }
}
