package com.bb.eodi.deal.application.service;

import com.bb.eodi.deal.application.input.FindRealEstateSellInput;
import com.bb.eodi.deal.application.port.LegalDongCachePort;
import com.bb.eodi.deal.application.query.assembler.FindRealEstateSellQueryAssembler;
import com.bb.eodi.deal.application.result.RealEstateSellSummaryResult;
import com.bb.eodi.deal.application.result.mapper.RealEstateSellSummaryResultMapper;
import com.bb.eodi.deal.domain.repository.RealEstateSellRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 부동산 매매 데이터 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RealEstateSellService {

    private final RealEstateSellRepository realEstateSellRepository;
    private final LegalDongCachePort legalDongCachePort;
    private final RealEstateSellSummaryResultMapper realEstateSellSummaryResultMapper;

    private final FindRealEstateSellQueryAssembler queryAssembler;

    /**
     * 부동산 매재 데이터 조회
     *
     * @param input 부동산 매매 데이터 조회 요청 파라미터
     * @param pageable         pageable 객체
     * @return 부동산 매매 데이터 목록
     */
    @Transactional
    public Page<RealEstateSellSummaryResult> findRealEstateSells(
            FindRealEstateSellInput input,
            Pageable pageable) {
        return realEstateSellRepository.findBy(
                        queryAssembler.assemble(input), pageable)
                .map(realEstateSell -> {
                    RealEstateSellSummaryResult resultDto = realEstateSellSummaryResultMapper.toResult(realEstateSell);

                    // legalDongFullName set -> 지역명 + 동명
                    resultDto.setLegalDongFullName(legalDongCachePort.findById(resultDto.getRegionId()) + " " + resultDto.getLegalDongName());
                    return resultDto;
                });
    }

}
