package com.bb.eodi.legaldong.application.cache;

import com.bb.eodi.deal.application.contract.LegalDongInfo;

public interface LegalDongCache {

    /**
     * 법정동 ID로 법정동 정보를 캐시에서 조회한다.
     * @param id 법정동 ID
     * @return 법정동 정보
     */
    LegalDongInfo findById(Long id);

    /**
     * 법정동 코드로 법정동 정보를 캐시에서 조회한다.
     * @param code 법정동 코드
     * @return 법정동 정보
     */
    LegalDongInfo findByCode(String code);

    /**
     * 법정동 명으로 법정동 정보를 캐시에서 조회한다.
     * @param name 법정동 명
     * @return 법정동 정보
     */
    LegalDongInfo findByName(String name);
}
