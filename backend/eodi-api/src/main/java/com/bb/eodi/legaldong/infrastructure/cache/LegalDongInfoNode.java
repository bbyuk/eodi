package com.bb.eodi.legaldong.infrastructure.cache;


import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * 크로스도메인 법정동 정보 트리 노드
 * use only at Adapter
 */
@Getter
class LegalDongInfoNode {
    private final Long id;
    private final String code;
    private final String name;
    private final int order;
    private Long rootId;
    private Long secondId;
    private final Long parentId;
    private LegalDongInfoNode parent;
    private final Set<LegalDongInfoNode> children = new HashSet<>();

    LegalDongInfoNode(Long id, String code, String name, int order, Long parentId) {
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
    void connectToParent(LegalDongInfoNode parent) {
        this.parent = parent;
        parent.children.add(this);
    }

    void updateSecondId(Long id) {
        this.secondId = id;
    }

    void updateRootId(Long id) {
        this.rootId = id;
    }

    boolean isRoot() {
        return id.equals(rootId);
    }

    boolean isLeaf() {
        return children.isEmpty();
    }
}
