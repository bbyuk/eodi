package com.bb.eodi.address.domain.port;


import java.time.LocalDate;

/**
 * 주소연계 API 클라이언트 port
 */
public interface AddressLinkageApiPort {
    /**
     * 대상 기간의 도로명주소 일변동 정보를 다운로드한다.
     * @param fromDate 대상기간 시작일자
     * @param toDate 대상기간 종료일자
     */
    void downloadUpdatedAddress(LocalDate fromDate, LocalDate toDate);

    /**
     * 대상 기간의 도로명주소 일변동 정보를 다운로드한다.
     * @param fromDate 대상기간 시작일자
     * @param toDate 대상기간 종료일자
     */
    void downloadUpdatedAddressPosition(LocalDate fromDate, LocalDate toDate);
}
