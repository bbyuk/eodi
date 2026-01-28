package com.bb.eodi.legaldong.infrastructure.cache;

import com.bb.eodi.deal.application.contract.LegalDongInfo;
import com.bb.eodi.legaldong.application.cache.LegalDongCache;
import com.bb.eodi.legaldong.domain.entity.LegalDong;
import com.bb.eodi.legaldong.domain.repository.LegalDongRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 인메모리 법정동 캐시
 */
@Component
@RequiredArgsConstructor
public class InMemoryLegalDongCache implements LegalDongCache {
    private final Map<Long, LegalDongInfo> cache = new ConcurrentHashMap<>();
    private final Map<String, LegalDongInfo> cacheByCode = new ConcurrentHashMap<>();
    private final Map<String, LegalDongInfo> cacheByName = new ConcurrentHashMap<>();

    private final LegalDongRepository legalDongRepository;

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
        cacheByCode.clear();
        List<LegalDong> legalDongs = legalDongRepository.findAll();

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

        currentNode.connectToParent(tree.get(currentNode.getParentId()));
        traverse(tree, currentNode.getParent());

        // rootId, secondId postorder update
        currentNode.updateRootId(currentNode.getParent().getRootId());
        currentNode.updateSecondId(currentNode.getParent().isRoot() ? currentNode.getId() : currentNode.getParent().getSecondId());
    }

    /**
     * 캐시에 legalDongInfo를 로드한다.
     *
     * @param legalDongInfo 법정동정보
     */
    private void loadToCache(LegalDongInfo legalDongInfo) {
        cache.putIfAbsent(legalDongInfo.id(), legalDongInfo);
        cacheByCode.putIfAbsent(legalDongInfo.code(), legalDongInfo);
        cacheByName.putIfAbsent(legalDongInfo.name(), legalDongInfo);

        legalDongInfo.children().stream()
                .forEach(this::loadToCache);
    }

    /**
     * 캐시에서 법정동 ID로 법정동 정보를 조회한다.
     * @param id 법정동 ID
     * @return 법정동 정보
     */
    public LegalDongInfo findById(Long id) {
        if (!cache.containsKey(id)) {
            throw new RuntimeException(id + " not found");
        }
        return cache.get(id);
    }

    /**
     * 캐시에서 법정동 코드로 법정동 정보를 조회한다.
     * @param code 법정동코드
     * @return 법정동 정보
     */
    public LegalDongInfo findByCode(String code) {
        if (!cacheByCode.containsKey(code)) {
            throw new RuntimeException(code + " not found");
        }

        return cacheByCode.get(code);
    }

    /**
     * 캐시에서 법정동 명으로 법정동 정보를 조회한다.
     * @param name 법정동 명
     * @return 법정동 정보
     */
    public LegalDongInfo findByName(String name) {
        if (!cacheByName.containsKey(name)) {
            throw new RuntimeException(name + " not found");
        }
        return cacheByName.get(name);
    }
}
