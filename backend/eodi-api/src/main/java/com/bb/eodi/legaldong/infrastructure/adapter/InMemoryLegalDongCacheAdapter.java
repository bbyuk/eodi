package com.bb.eodi.legaldong.infrastructure.adapter;

import com.bb.eodi.deal.application.contract.LegalDongInfo;
import com.bb.eodi.deal.application.port.LegalDongCachePort;
import com.bb.eodi.legaldong.infrastructure.persistence.LegalDongJpaEntity;
import com.bb.eodi.legaldong.infrastructure.persistence.LegalDongJpaRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 인메모리 법정동 캐시 어댑터
 */
@Repository
@RequiredArgsConstructor
public class InMemoryLegalDongCacheAdapter implements LegalDongCachePort {
    private final Map<Long, LegalDongInfo> cache = new ConcurrentHashMap<>();
    private final LegalDongJpaRepository legalDongJpaRepository;

    @PostConstruct
    public void initCache() {
        refreshCache();
    }

    public List<LegalDongInfo> findUnMappedDataLegalDongInfo() {
        return cache.values().stream()
                .filter(legalDongInfo ->
                        legalDongInfo.rootId() == null || legalDongInfo.secondId() == null
                ).collect(Collectors.toList());
    }

    public void refreshCache() {
        // 1. clear
        cache.clear();
        List<LegalDongJpaEntity> legalDongs = legalDongJpaRepository.findAll();

        // 2. 임시 노드 트리에 putAll
        Map<Long, LegalDongInfoNode> tempTree =
                legalDongs.stream().map(legalDong -> new LegalDongInfoNode(
                        legalDong.getId(),
                        legalDong.getCode(),
                        legalDong.getName(),
                        legalDong.getLegalDongOrder(),
                        legalDong.getParentId()
                )).collect(Collectors.toMap(
                        LegalDongInfoNode::getId,
                        legalDongInfoNode -> legalDongInfoNode
                ));

        // 3. 임시 노드 트리 노드간 connect
        for (LegalDongInfoNode node : tempTree.values()) {
            traverse(tempTree, node);
        }

        // 4. LegalDongInfo model로 매핑
        tempTree.values().stream()
                .filter(LegalDongInfoNode::isRoot)
                .map(LegalDongInfoNodeMapper::toInfo)
                .forEach(this::loadToCache);
    }

    private void traverse(Map<Long, LegalDongInfoNode> tree, LegalDongInfoNode currentNode) {
        if (currentNode.isRoot()) {
            currentNode.updateRootId(currentNode.getId());
            return;
        }

        if (currentNode.getId().equals(6760L)) {
            System.out.println("tree = " + tree);
        }

        currentNode.connectToParent(tree.get(currentNode.getParentId()));
        traverse(tree, currentNode.getParent());

        // rootId, secondId postorder update
        currentNode.updateRootId(currentNode.getParent().getRootId());
        currentNode.updateSecondId(currentNode.getParent().isRoot() ? currentNode.getId() : currentNode.getParent().getSecondId());
    }

    /**
     * 캐시에 legalDongInfo를 로드한다.
     *
     * @param legalDongInfo
     */
    private void loadToCache(LegalDongInfo legalDongInfo) {
        cache.putIfAbsent(legalDongInfo.id(), legalDongInfo);
        legalDongInfo.children().stream()
                .forEach(this::loadToCache);
    }

    @Override
    public LegalDongInfo findById(Long id) {
        if (!cache.containsKey(id)) {
            throw new RuntimeException(id + " not found");
        }
        return cache.get(id);
    }
}
