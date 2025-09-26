package com.bb.eodi.legaldong.infrastructure.persistence;

import com.bb.eodi.legaldong.domain.entity.LegalDong;
import com.bb.eodi.legaldong.domain.repository.LegalDongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 법정동 Repository 구현
 */
@Repository
@RequiredArgsConstructor
public class LegalDongRepositoryImpl implements LegalDongRepository {

    private final LegalDongJpaRepository legalDongJpaRepository;
    private final LegalDongMapper mapper;

    @Override
    public Optional<LegalDong> findByCode(String code) {
        return legalDongJpaRepository.findByCode(code)
                .map(mapper::toDomain);
    }
}
