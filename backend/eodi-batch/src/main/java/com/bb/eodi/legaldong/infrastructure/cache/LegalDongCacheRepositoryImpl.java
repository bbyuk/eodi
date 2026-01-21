package com.bb.eodi.legaldong.infrastructure.cache;

import com.bb.eodi.legaldong.domain.dto.LegalDongInfoDto;
import com.bb.eodi.legaldong.domain.entity.LegalDong;
import com.bb.eodi.legaldong.domain.entity.QLegalDong;
import com.bb.eodi.legaldong.domain.repository.LegalDongCacheRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LegalDongCacheRepositoryImpl implements LegalDongCacheRepository {

    private final JPAQueryFactory queryFactory;

    private static final Map<Long, LegalDongInfoDto> cache = new HashMap<>();
    private static final Map<String, LegalDongInfoDto> cacheByCode = new HashMap<>();

    @PostConstruct
    public void initCache() {
        refreshCache();
        cacheByCode.putAll(cache.values().stream().collect(Collectors.toMap(
                LegalDongInfoDto::getCode,
                legalDongInfoDto -> legalDongInfoDto
        )));
    }

    public void refreshCache() {
        // 1. clear
        cache.clear();

        QLegalDong ld = QLegalDong.legalDong;
        List<LegalDong> legalDongs = queryFactory.selectFrom(ld)
                .where(ld.isActive.eq(true))
                .fetch();

        // 2. 캐시에 load
        Map<Long, LegalDongInfoDto> tempTree = legalDongs.stream().map(legalDong -> new LegalDongInfoDto(
                        legalDong.getId(),
                        legalDong.getCode(),
                        legalDong.getName(),
                        legalDong.getLegalDongOrder(),
                        legalDong.getParentId()
                ))
                .collect(Collectors.toMap(LegalDongInfoDto::getId, legalDongInfoDto -> legalDongInfoDto));

        tempTree.values().forEach(legalDongInfoDto -> traverse(tempTree, legalDongInfoDto));

        // 3. 노드간 연결
        tempTree.values().stream()
                .filter(LegalDongInfoDto::isRoot)
                .forEach(this::loadToCache);
    }

    private void traverse(Map<Long, LegalDongInfoDto> tree, LegalDongInfoDto currentNode) {
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
     * @param legalDongInfo 법정동 정보
     */
    private void loadToCache(LegalDongInfoDto legalDongInfo) {
        cache.putIfAbsent(legalDongInfo.getId(), legalDongInfo);
        legalDongInfo.getChildren().stream()
                .forEach(this::loadToCache);
    }

    @Override
    public Optional<LegalDongInfoDto> findLegalDongInfoById(Long id) {
        return Optional.ofNullable(cache.get(id));
    }

    @Override
    public Optional<LegalDongInfoDto> findLegalDongInfoByCode(String code) {
        return Optional.ofNullable(cacheByCode.get(code));
    }
}
