package com.bb.eodi.common.api.client;

import com.bb.eodi.batch.deal.dto.*;
import com.bb.eodi.batch.deal.port.DealDataPort;
import com.bb.eodi.common.api.dto.DealDataQuery;
import com.bb.eodi.common.api.dto.DealDataResponse;
import com.bb.eodi.common.api.spec.DealDataApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 부동산 매매 데이터 API 클라이언트 구현체
 */
@Component
@RequiredArgsConstructor
public class DealDataApiClient implements DealDataPort {

    private final DealDataApi dealDataApi;

    @Override
    public DealDataResponse<ApartmentSellDataItem> getApartmentSellData(DealDataQuery query) {
        return dealDataApi.getApartmentSellData(query.regionCode(), query.dealMonth(), query.pageSize(), query.pageNum());
    }

    @Override
    public DealDataResponse<ApartmentLeaseDataItem> getApartmentLeaseData(DealDataQuery query) {
        return dealDataApi.getApartmentLeaseData(query.regionCode(), query.dealMonth(), query.pageSize(), query.pageNum());
    }

    @Override
    public DealDataResponse<ApartmentPresaleRightSellDataItem> getApartmentPresaleRightSellData(DealDataQuery query) {
        return dealDataApi.getApartmentPreSaleRightSaleData(query.regionCode(), query.dealMonth(), query.pageSize(), query.pageNum());
    }

    @Override
    public DealDataResponse<MultiHouseholdHouseSellDataItem> getMultiHouseholdSellData(DealDataQuery query) {
        return dealDataApi.getMultiHouseholdSellData(query.regionCode(), query.dealMonth(), query.pageSize(), query.pageNum());
    }

    @Override
    public DealDataResponse<MultiHouseholdHouseLeaseDataItem> getMultiHouseholdLeaseData(DealDataQuery query) {
        return dealDataApi.getMultiHouseholdLeaseData(query.regionCode(), query.dealMonth(), query.pageSize(), query.pageNum());
    }

    @Override
    public DealDataResponse<MultiUnitDetachedSellDataItem> getMultiUnitDetachedSellData(DealDataQuery query) {
        return dealDataApi.getMultiUnitDetachedSellData(query.regionCode(), query.dealMonth(), query.pageSize(), query.pageNum());
    }

    @Override
    public DealDataResponse<MultiUnitDetachedLeaseDataItem> getMultiUnitDetachedLeaseData(DealDataQuery query) {
        return dealDataApi.getMultiUnitDetachedLeaseData(query.regionCode(), query.dealMonth(), query.pageSize(), query.pageNum());
    }

    @Override
    public DealDataResponse<OfficetelSellDataItem> getOfficetelSellData(DealDataQuery query) {
        return dealDataApi.getOfficetelSellData(query.regionCode(), query.dealMonth(), query.pageSize(), query.pageNum());
    }

    @Override
    public DealDataResponse<OfficetelLeaseDataItem> getOfficetelLeaseData(DealDataQuery query) {
        return dealDataApi.getOfficetelLeaseData(query.regionCode(), query.dealMonth(), query.pageSize(), query.pageNum());
    }
}
