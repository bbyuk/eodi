package com.bb.eodi.deal.application.port;

import com.bb.eodi.deal.application.contract.LegalDongInfo;

/**
 * 법정동 캐시 조회 포트
 */
public interface LegalDongCachePort {

    /**
     * 법정동 ID로 법정동 캐시에서 법정동 정보를 조회한다.
     * @param id 법정동 ID
     * @return 법정동 정보
     */
    LegalDongInfo findById(Long id);

    /**
     * 법정동 코드로 법정동 캐시에서 법정동 정보를 조회한다.
     * @param code 법정동 코드
     * @return 법정동 정보
     */
    LegalDongInfo findByCode(String code);
}
