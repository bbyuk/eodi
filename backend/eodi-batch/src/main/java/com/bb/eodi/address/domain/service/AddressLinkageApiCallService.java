package com.bb.eodi.address.domain.service;

import com.bb.eodi.address.domain.port.AddressLinkageApiPort;
import com.bb.eodi.ops.domain.entity.ReferenceVersion;
import com.bb.eodi.ops.domain.repository.ReferenceVersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobInterruptedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.bb.eodi.ops.domain.enums.ReferenceTarget.ADDRESS;

/**
 * 주소 연계 API 요청 담당 서비스
 */
@Service
@RequiredArgsConstructor
public class AddressLinkageApiCallService {

    private final ReferenceVersionRepository referenceVersionRepository;
    private final AddressLinkageApiPort addressLinkageApiPort;

    /**
     * 대상 디렉터리에 마지막 최신 일자부터 현재 일자까지 변동분 파일을 다운로드한다.
     * 대상 디렉터리 하위에 일자별로 yyyyMM 형태의 이름을 가진 서브디렉터리가 생성되며
     * 각 일자별로 파일이 다운로드된다.
     * @param targetDirectory 다운로드 대상 디렉터리
     * @throws JobInterruptedException 주소 DB가 이미 최신화된 상태로, Batch Job Status를 STOPPED로 종료한다.
     */
    @Transactional(readOnly = true)
    public AddressLinkageResult downloadNewFiles(String targetDirectory, AddressLinkagePeriod period) throws JobInterruptedException {
        ReferenceVersion referenceVersion = referenceVersionRepository.findByTargetName(ADDRESS.getValue())
                .orElseThrow(() -> new RuntimeException(ADDRESS.getValue() + " 기준정보 버전 정보를 찾지 못했습니다."));

        if (!period.to().isAfter(referenceVersion.getEffectiveDate())) {
            return AddressLinkageResult.ALREADY_UP_TO_DATE;
        }

        long between = ChronoUnit.DAYS.between(period.from(), period.to());
        /**
         * 10일 초과시 10일씩 나눠서 api 요청 및 jobExecutionContext에 등록
         */
        if (between > 10) {
            long mul = between / 10;

            for (int i = 0; i < mul; i++) {
                LocalDate fixedFromDate = period.from().plusDays(i * 10);
                LocalDate fixedToDate = period.from().plusDays((i + 1) * 10 - 1);

                addressLinkageApiPort.downloadUpdatedAddress(targetDirectory, fixedFromDate, fixedToDate);
            }

            LocalDate lastFromDate = period.from().plusDays(mul * 10);
            addressLinkageApiPort.downloadUpdatedAddress(targetDirectory, lastFromDate, period.to());
        }
        else {
            addressLinkageApiPort.downloadUpdatedAddress(targetDirectory, period.from(), period.to());
        }

        return AddressLinkageResult.SUCCESS;
    }

    /**
     * 주소 최신화 배치 수행 대상기간을 조회한다.
     * @return 주소 최신화 배치 수행 대상기간
     */
    @Transactional(readOnly = true)
    public AddressLinkagePeriod findTargetPeriod(String targetName) {
        return new AddressLinkagePeriod(
                referenceVersionRepository.findByTargetName(targetName)
                        .orElseThrow(() -> new RuntimeException("주소 기준정보 버전을 찾지 못했습니다."))
                        .getEffectiveDate()
                        .plusDays(1),
                LocalDate.now()
        );
    }

}
