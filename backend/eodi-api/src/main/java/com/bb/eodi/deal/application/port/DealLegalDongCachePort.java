package com.bb.eodi.deal.application.port;

import com.bb.eodi.deal.application.contract.DealLegalDongInfo;

/**
 * 법정동 캐시 조회 포트
 */
public interface DealLegalDongCachePort {

    /**
     * 법정동 ID로 법정동 캐시에서 법정동 정보를 조회한다.
     * @param id 법정동 ID
     * @return 법정동 정보
     */
    DealLegalDongInfo findById(Long id);

    /**
     * 법정동 코드로 법정동 캐시에서 법정동 정보를 조회한다.
     * @param code 법정동 코드
     * @return 법정동 정보
     */
    DealLegalDongInfo findByCode(String code);
}
