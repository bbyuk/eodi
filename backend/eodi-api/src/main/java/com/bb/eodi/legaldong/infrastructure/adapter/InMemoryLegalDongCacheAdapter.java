package com.bb.eodi.legaldong.infrastructure.adapter;

import com.bb.eodi.deal.application.model.LegalDongInfo;
import com.bb.eodi.deal.application.port.LegalDongCachePort;
import com.bb.eodi.legaldong.infrastructure.persistence.LegalDongJpaEntity;
import com.bb.eodi.legaldong.infrastructure.persistence.LegalDongJpaRepository;
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
            LegalDongInfoNode currentNode = node;

            while(!currentNode.isRoot() && !currentNode.isConnectedToParent()) {
                currentNode.connectToParent(tempTree.get(currentNode.getParentId()));
                currentNode = currentNode.getParent();
            }
        }

        // 4. LegalDongInfo model로 매핑
        tempTree.values().stream()
                .filter(LegalDongInfoNode::isRoot)
                .map(LegalDongInfoMapper::toInfo)
                .forEach(this::loadToCache);
    }

    /**
     * 캐시에 legalDongInfo를 로드한다.
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
