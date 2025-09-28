package com.bb.eodi.deal.application.service;

import com.bb.eodi.deal.application.dto.RealEstateSellSummaryDto;
import com.bb.eodi.deal.application.dto.RealEstateSellSummaryDtoMapper;
import com.bb.eodi.deal.domain.dto.RealEstateSellQuery;
import com.bb.eodi.deal.domain.repository.RealEstateSellRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 부동산 매매 데이터 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RealEstateSellService {

    private final RealEstateSellRepository realEstateSellRepository;
    private final RealEstateSellSummaryDtoMapper summaryDtoMapper;


    /**
     * 부동산 매재 데이터 조회
     * @param query 부동산 매매 데이터 조회 쿼리 파라미터
     * @return 부동산 매매 데이터 목록
     */
    @Transactional
    public Page<RealEstateSellSummaryDto> findRealEstateSells(RealEstateSellQuery query, Pageable pageable) {
        return realEstateSellRepository.findBy(query, pageable)
                .map(summaryDtoMapper::toDto);
    }

}
