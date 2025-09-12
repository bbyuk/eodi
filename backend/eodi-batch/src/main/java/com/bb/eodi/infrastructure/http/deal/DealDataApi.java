package com.bb.eodi.infrastructure.http.deal;

import com.bb.eodi.port.out.deal.dto.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * 부동산 실거래가 데이터 API 명세
 */
@HttpExchange(
        url = "/1613000",
        accept = "application/xml",
        contentType = "application/xml")
public interface DealDataApi {

    /**
     * 아파트 매매 데이터 API 요청
     *
     * @param legalDongCode 지역코드, 각 지역별 코드 행정표준코드관리시스템(www.code.go.kr)의 법정동코드 10자리 중 앞 5자리
     * @param dealDate      계약월, 실거래 자료의 계약년월(6자리)
     * @return 아파트 매매 데이터 API 응답
     */
    @GetExchange("/RTMSDataSvcAptTrade/getRTMSDataSvcAptTrade")
    DealDataResponse<ApartmentSellDataItem> getApartmentSellData(
            @RequestParam("LAWD_CD") String legalDongCode,
            @RequestParam("DEAL_YMD") String dealDate);

    /**
     * 아파트 전/월세 데이터 API 요청
     *
     * @param legalDongCode 지역코드, 각 지역별 코드 행정표준코드관리시스템(www.code.go.kr)의 법정동코드 10자리 중 앞 5자리
     * @param dealDate      계약월, 실거래 자료의 계약년월(6자리)
     * @return 아파트 전/월세 데이터 API 응답
     */
    @GetExchange("/RTMSDataSvcAptRent/getRTMSDataSvcAptRent")
    DealDataResponse<ApartmentLeaseDataItem> getApartmentLeaseData(
            @RequestParam("LAWD_CD") String legalDongCode,
            @RequestParam("DEAL_YMD") String dealDate);

    /**
     * 아파트 분양권 전매 데이터 API 요청
     *
     * @param legalDongCode 지역코드, 각 지역별 코드 행정표준코드관리시스템(www.code.go.kr)의 법정동코드 10자리 중 앞 5자리
     * @param dealDate      계약월, 실거래 자료의 계약년월(6자리)
     * @return 아파트 분양권 전매 데이터 API 응답
     */
    @GetExchange("/RTMSDataSvcSilvTrade/getRTMSDataSvcSilvTrade")
    DealDataResponse<ApartmentPreSaleRightSaleDataItem> getApartmentPreSaleRightSaleData(
            @RequestParam("LAWD_CD") String legalDongCode,
            @RequestParam("DEAL_YMD") String dealDate);

    /**
     * 연립/다세대 주택 매매 데이터 API 요청
     *
     * @param legalDongCode 지역코드, 각 지역별 코드 행정표준코드관리시스템(www.code.go.kr)의 법정동코드 10자리 중 앞 5자리
     * @param dealDate      계약월, 실거래 자료의 계약년월(6자리)
     * @return 연립/다세대 주택 매매 데이터 API 응답
     */
    @GetExchange("/RTMSDataSvcRHTrade/getRTMSDataSvcRHTrade")
    DealDataResponse<MultiHouseholdSellDataItem> getMultiHouseholdSellData(
            @RequestParam("LAWD_CD") String legalDongCode,
            @RequestParam("DEAL_YMD") String dealDate);

    /**
     * 연립/다세대 주택 전/월세 데이터 API 요청
     *
     * @param legalDongCode 지역코드, 각 지역별 코드 행정표준코드관리시스템(www.code.go.kr)의 법정동코드 10자리 중 앞 5자리
     * @param dealDate      계약월, 실거래 자료의 계약년월(6자리)
     * @return 연립/다세대 주택 전/월세 데이터 API 응답
     */
    @GetExchange("/RTMSDataSvcRHRent/getRTMSDataSvcRHRent")
    DealDataResponse<MultiHouseholdLeaseDataItem> getMultiHouseholdLeaseData(
            @RequestParam("LAWD_CD") String legalDongCode,
            @RequestParam("DEAL_YMD") String dealDate);

    /**
     * 단독/다가구 주택 매매 데이터 API 요청
     *
     * @param legalDongCode 지역코드, 각 지역별 코드 행정표준코드관리시스템(www.code.go.kr)의 법정동코드 10자리 중 앞 5자리
     * @param dealDate      계약월, 실거래 자료의 계약년월(6자리)
     * @return 단독/다가구 주택 매매 데이터 API 응답
     */
    @GetExchange("/RTMSDataSvcSHTrade/getRTMSDataSvcSHTrade")
    DealDataResponse<MultiUnitDetachedSellDataItem> getMultiUnitDetachedSellData(
            @RequestParam("LAWD_CD") String legalDongCode,
            @RequestParam("DEAL_YMD") String dealDate);

    /**
     * 단독/다가구 주택 전/월세 데이터 API 요청
     *
     * @param legalDongCode 지역코드, 각 지역별 코드 행정표준코드관리시스템(www.code.go.kr)의 법정동코드 10자리 중 앞 5자리
     * @param dealDate      계약월, 실거래 자료의 계약년월(6자리)
     * @return 단독/다가구 주택 전/월세 데이터 API 응답
     */
    @GetExchange("/RTMSDataSvcSHRent/getRTMSDataSvcSHRent")
    DealDataResponse<MultiUnitDetachedLeaseDataItem> getMultiUnitDetachedLeaseData(
            @RequestParam("LAWD_CD") String legalDongCode,
            @RequestParam("DEAL_YMD") String dealDate);

    /**
     * 오피스텔 매매 데이터 API 요청
     *
     * @param legalDongCode 지역코드, 각 지역별 코드 행정표준코드관리시스템(www.code.go.kr)의 법정동코드 10자리 중 앞 5자리
     * @param dealDate      계약월, 실거래 자료의 계약년월(6자리)
     * @return 오피스텔 매매 데이터 API 응답
     */
    @GetExchange("/RTMSDataSvcOffiTrade/getRTMSDataSvcOffiTrade")
    DealDataResponse<OfficetelSellDataItem> getOfficetelSellData(
            @RequestParam("LAWD_CD") String legalDongCode,
            @RequestParam("DEAL_YMD") String dealDate);

    /**
     * 오피스텔 전/월세 데이터 API 요청
     *
     * @param legalDongCode 지역코드, 각 지역별 코드 행정표준코드관리시스템(www.code.go.kr)의 법정동코드 10자리 중 앞 5자리
     * @param dealDate      계약월, 실거래 자료의 계약년월(6자리)
     * @return 오피스텔 전/월세 데이터 API 응답
     */
    @GetExchange("/RTMSDataSvcOffiRent/getRTMSDataSvcOffiRent")
    DealDataResponse<OfficetelLeaseDataItem> getOfficetelLeaseData(
            @RequestParam("LAWD_CD") String legalDongCode,
            @RequestParam("DEAL_YMD") String dealDate);
}
