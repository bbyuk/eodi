package com.bb.eodi.integration.juso.linkage;

import com.bb.eodi.address.domain.port.AddressLinkageApiPort;
import kr.go.ads.client.ADSReceiver;
import kr.go.ads.client.ADSUtils;
import kr.go.ads.client.ReceiveData;
import kr.go.ads.client.ReceiveDatas;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 도로명주소 연계 API 클라이언트 구현체
 */
@Slf4j
@Component
public class AddressLinkageApiClient implements AddressLinkageApiPort {

    @Value("${api.juso.key}")
    private String apiKey;

    @Value("${api.juso.temp-file-path}")
    private String tempFilePath;

    /**
     * ============= 송수신포맷 =============
     */
    // 도로명주소한글
    private static final String ROAD_NAME_ADDRESS_CODE = "100001";
    // 도로명주소 출입구정보
    private static final String ROAD_NAME_ADDRESS_ENTRANCE_CODE = "200001";
    /**
     * ============= 송수신포맷 =============
     */
    private static final String DAILY = "D";
    private static final String MONTHLY = "M";

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");

    private void callAPI(String fromDate, String toDate, String targetCode) {
        ADSReceiver ads = new ADSReceiver();  // ADSReceiver객체 생성
        String retry_in = "Y";        // 재반영 요청 여부

        // 일변동 자료를 저장할 파일경로를 설정합니다.
        ads.setFilePath(tempFilePath);
        ads.setCreateDateDirectory(ADSUtils.YYMMDD);
        try {
            // 변동자료 연계서비스 요청 및 응답데이터 확인
            ReceiveDatas receiveDatas = ads.receiveAddr(apiKey, DAILY, targetCode, retry_in, fromDate, toDate);
            /* ---------------------------------응답 결과 확인 ---------------------------------*/
            if (receiveDatas.getResult() != 0) {
                if (receiveDatas.getResult() == -1) {
                    log.error("서버 접속 실패 : 잠시후 재시도 하시기 바랍니다.");
                }
                // 서버 페이지 오류 사항 확인
                log.error("Result code : {}", receiveDatas.getResult());
                log.error("Response code : {}", receiveDatas.getResCode());
                log.error("Response msg : {}", receiveDatas.getResMsg());
                return;
            }

            if (!"P0000".equals(receiveDatas.getResCode())) {
                log.error("Result code : {}", receiveDatas.getResult());
                log.error("Response code : {}", receiveDatas.getResCode());
                log.error("Response msg : {}", receiveDatas.getResMsg());
            }

            log.info("Response code : {}", receiveDatas.getResCode());  // 응답 코드
            log.info("Response msg : {}", receiveDatas.getResMsg());    // 응답 메세지
            /* ---------------------------------응답 결과 완료  ---------------------------------*/
            // 결과 데이터 정렬
            ArrayList result = receiveDatas.getReceiveDatas(ADSUtils.UPDATE_ASC);
            Iterator itr = result.iterator();
            while (itr.hasNext()) {
                // 결과 데이터 건별 정보 확인
                ReceiveData receiveData = (ReceiveData) itr.next();
                log.info(" CNTC : ");
                log.info(receiveData.getCntcCode());
                log.info(" RES_CODE : ");
                log.info(receiveData.getResCode());

                if (!"P0000".equals(receiveData.getResCode())) {
                    log.error("해당파일에 대한 응답이 정상이 아니기에 재요청 필요");
                    log.error("Response code : {}", receiveData.getResCode());
                    log.error("Response msg : {}", receiveData.getResMsg());
                    throw new RuntimeException("해당파일에 대한 응답이 정상이 아니기에 재요청 필요");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 날짜 파라미터 validation
     *
     * 1. toDate는 fromDate보다 이전일 수 없다.
     * 2. fromDate와 toDate간 차이는 10일을 초과할 수 없다.
     *
     * @param fromDate  시작일자
     * @param toDate    종료일자
     */
    private static void validateDateParameter(LocalDate fromDate, LocalDate toDate) {
        boolean correctDate = !toDate.isBefore(fromDate) && ChronoUnit.DAYS.between(fromDate, toDate) <= 10;
        if (!correctDate) {
            throw new RuntimeException("잘못된 날짜 파라미터 입력입니다.");
        }
    }

    /**
     * 대상 기간의 도로명주소 일변동 정보를 다운로드한다.
     * @param fromDate 대상기간 시작일자
     * @param toDate 대상기간 종료일자
     */
    @Override
    public void downloadUpdatedAddress(LocalDate fromDate, LocalDate toDate) {
        validateDateParameter(fromDate, toDate);
        callAPI(dtf.format(fromDate), dtf.format(toDate), ROAD_NAME_ADDRESS_CODE);
    }

    /**
     * 대상 기간의 도로명주소 일변동 정보를 다운로드한다.
     * @param fromDate 대상기간 시작일자
     * @param toDate 대상기간 종료일자
     */
    @Override
    public void downloadUpdatedAddressPosition(LocalDate fromDate, LocalDate toDate) {
        validateDateParameter(fromDate, toDate);
        callAPI(dtf.format(fromDate), dtf.format(toDate), ROAD_NAME_ADDRESS_ENTRANCE_CODE);
    }


}
