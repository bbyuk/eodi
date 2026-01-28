package com.bb.eodi.finance.domain.repository;

import com.bb.eodi.finance.domain.entity.RegulatingArea;

import java.util.Collection;
import java.util.List;

/**
 * 규제지역 Repository
 */
public interface RegulatingAreaRepository {

    /**
     * 현재 부동산 대책에 따라 규제를 받는 규제지역을 전체 조회한다.
     * @return 규제지역
     */
    List<RegulatingArea> findAll();

    /**
     * 현재 부동산 대책에 따라 규제를 받는 지역을 전체 저장한다.
     * @param entities 규제지역 domain entities
     */
    void saveAll(Collection<? extends RegulatingArea> entities);
}
