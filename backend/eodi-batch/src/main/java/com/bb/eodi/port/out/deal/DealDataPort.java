package com.bb.eodi.port.out.deal;

import com.bb.eodi.port.out.deal.dto.*;

/**
 * 부동산 실거래가 데이터 API 인터페이스
 */
public interface DealDataPort {

    /**
     * 아파트 매매 데이터 API 요청
     * @param query 부동산 거래 데이터 API 요청 쿼리 파라미터
     * @return 아파트 매매 데이터 API 응답
     */
    DealDataResponse<ApartmentSellDataItem> getApartmentSellData(DealDataQuery query);

    /**
     * 아파트 전/월세 데이터 API 요청
     * @param query 부동산 거래 데이터 API 요청 쿼리 파라미터
     * @return 아파트 전/월세 데이터 API 응답
     */
    DealDataResponse<ApartmentLeaseDataItem> getApartmentLeaseData(DealDataQuery query);

    /**
     * 아파트 분양권 전매 데이터 API 요청
     * @param query 부동산 거래 데이터 API 요청 쿼리 파라미터
     * @return 아파트 분양권 전매 데이터 API 응답
     */
    DealDataResponse<ApartmentPreSaleRightSaleDataItem> getApartmentPreSaleRightSaleData(DealDataQuery query);

    /**
     * 연립/다세대 주택 매매 데이터 API 요청
     * @param query 부동산 거래 데이터 API 요청 쿼리 파라미터
     * @return 연립/다세대 주택 매매 데이터 API 응답
     */
    DealDataResponse<MultiHouseholdSellDataItem> getMultiHouseholdSellData(DealDataQuery query);

    /**
     * 연립/다세대 주택 전/월세 데이터 API 요청
     * @param query 부동산 거래 데이터 API 요청 쿼리 파라미터
     * @return 연립/다세대 주택 전/월세 데이터 API 응답
     */
    DealDataResponse<MultiHouseholdLeaseDataItem> getMultiHouseholdLeaseData(DealDataQuery query);

    /**
     * 단독/다가구 주택 매매 데이터 API 요청
     * @param query 부동산 거래 데이터 API 요청 쿼리 파라미터
     * @return 단독/다가구 주택 매매 데이터 API 응답
     */
    DealDataResponse<MultiUnitDetachedSellDataItem> getMultiUnitDetachedSellData(DealDataQuery query);

    /**
     * 단독/다가구 주택 전/월세 데이터 API 요청
     * @param query 부동산 거래 데이터 API 요청 쿼리 파라미터
     * @return 단독/다가구 주택 전/월세 데이터 API 응답
     */
    DealDataResponse<MultiUnitDetachedLeaseDataItem> getMultiUnitDetachedLeaseData(DealDataQuery query);

    /**
     * 오피스텔 매매 데이터 API 요청
     * @param query 부동산 거래 데이터 API 요청 쿼리 파라미터
     * @return 오피스텔 매매 데이터 API 응답
     */
    DealDataResponse<OfficetelSellDataItem> getOfficetelSellData(DealDataQuery query);

    /**
     * 오피스텔 전/월세 데이터 API 요청
     * @param query 부동산 거래 데이터 API 요청 쿼리 파라미터
     * @return 오피스텔 전/월세 데이터 API 응답
     */
    DealDataResponse<OfficetelLeaseDataItem> getOfficetelLeaseData(DealDataQuery query);
}
