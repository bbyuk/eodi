package com.bb.eodi.legaldong.infrastructure.adapter.mapper;

import com.bb.eodi.deal.application.contract.DealLegalDongInfo;
import com.bb.eodi.finance.application.contract.FinanceLegalDongInfo;
import com.bb.eodi.legaldong.application.result.LegalDongInfo;
import org.mapstruct.Mapper;

/**
 * 법정동 정보 adapter mapper
 */
@Mapper(componentModel = "spring")
public interface LegalDongInfoMapper {

    DealLegalDongInfo forDeal(LegalDongInfo legalDongInfo);

    FinanceLegalDongInfo forFinance(LegalDongInfo legalDongInfo);
}
