package com.bb.eodi.infrastructure.http.deal;

import com.bb.eodi.infrastructure.api.govdata.GovernmentDataApiProperties;
import com.bb.eodi.port.out.deal.DealDataPort;
import com.bb.eodi.port.out.deal.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 부동산 매매 데이터 API 클라이언트 구현체
 */
@Component
@RequiredArgsConstructor
public class DealDataApiClient implements DealDataPort {

    private final DealDataApi dealDataApi;
    private final GovernmentDataApiProperties properties;

    @Override
    public DealDataResponse<ApartmentSellDataItem> getApartmentSellData(DealDataQuery query) {
        return dealDataApi.getApartmentSellData(query.legalDongCode(), query.dealDate(), properties.key());
    }

    @Override
    public DealDataResponse<ApartmentLeaseDataItem> getApartmentLeaseData(DealDataQuery query) {
        return dealDataApi.getApartmentLeaseData(query.legalDongCode(), query.dealDate(), properties.key());
    }

    @Override
    public DealDataResponse<ApartmentPreSaleRightSaleDataItem> getApartmentPreSaleRightSaleData(DealDataQuery query) {
        return dealDataApi.getApartmentPreSaleRightSaleData(query.legalDongCode(), query.dealDate(), properties.key());
    }

    @Override
    public DealDataResponse<MultiHouseholdSellDataItem> getMultiHouseholdSellData(DealDataQuery query) {
        return dealDataApi.getMultiHouseholdSellData(query.legalDongCode(), query.dealDate(), properties.key());
    }

    @Override
    public DealDataResponse<MultiHouseholdLeaseDataItem> getMultiHouseholdLeaseData(DealDataQuery query) {
        return dealDataApi.getMultiHouseholdLeaseData(query.legalDongCode(), query.dealDate(), properties.key());
    }

    @Override
    public DealDataResponse<MultiUnitDetachedSellDataItem> getMultiUnitDetachedSellData(DealDataQuery query) {
        return dealDataApi.getMultiUnitDetachedSellData(query.legalDongCode(), query.dealDate(), properties.key());
    }

    @Override
    public DealDataResponse<MultiUnitDetachedLeaseDataItem> getMultiUnitDetachedLeaseData(DealDataQuery query) {
        return dealDataApi.getMultiUnitDetachedLeaseData(query.legalDongCode(), query.dealDate(), properties.key());
    }

    @Override
    public DealDataResponse<OfficetelSellDataItem> getOfficetelSellData(DealDataQuery query) {
        return dealDataApi.getOfficetelSellData(query.legalDongCode(), query.dealDate(), properties.key());
    }

    @Override
    public DealDataResponse<OfficetelLeaseDataItem> getOfficetelLeaseData(DealDataQuery query) {
        return dealDataApi.getOfficetelLeaseData(query.legalDongCode(), query.dealDate(), properties.key());
    }
}
