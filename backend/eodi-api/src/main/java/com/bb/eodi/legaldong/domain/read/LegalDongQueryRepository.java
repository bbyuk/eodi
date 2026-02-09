package com.bb.eodi.legaldong.domain.read;

import com.bb.eodi.legaldong.domain.entity.LegalDong;
import com.bb.eodi.legaldong.domain.query.LegalDongFindQuery;

import java.util.List;


public interface LegalDongQueryRepository {

    /**
     * 조회 쿼리에 따른 법정동 목록을 리턴한다.
     * @param query 법정동 조회 쿼리
     * @return 쿼리에 해당하는 법정동 목록
     */
    List<LegalDong> findBy(LegalDongFindQuery query);
}
