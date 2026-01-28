package com.bb.eodi.finance.application.port;

import com.bb.eodi.deal.application.contract.LegalDongInfo;

public interface FinanceLegalDongCachePort {
    /**
     * 법정동 명으로 법정동 캐시에서 법정동 정보를 조회한다.
     * @param name 법정동명
     * @return 법정동 정보
     */
    LegalDongInfo findByName(String name);

}
