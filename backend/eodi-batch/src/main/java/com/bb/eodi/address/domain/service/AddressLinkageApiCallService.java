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
import java.util.function.BiConsumer;

import static com.bb.eodi.address.domain.service.AddressLinkageTarget.*;
import static com.bb.eodi.address.domain.service.AddressLinkageTarget.ROAD_NAME_ADDRESS_KOR;


/**
 * 주소 연계 API 요청 담당 서비스
 */
@Service
@RequiredArgsConstructor
public class AddressLinkageApiCallService {

    private final ReferenceVersionRepository referenceVersionRepository;
    private final AddressLinkageApiPort addressLinkageApiPort;

    /**
     * 대상 디렉터리에 마지막 최신 일자부터 현재 일자까지 도로명주소 연계 파일을 다운로드한다.
     * 대상 디렉터리 하위에 일자별로 yyMMdd 형태의 이름을 가진 서브디렉터리가 생성되며
     * 각 일자별로 파일이 다운로드된다.
     * @param targetDirectory 다운로드 대상 디렉터리
     * @throws JobInterruptedException 주소 DB가 이미 최신화된 상태로, Batch Job Status를 STOPPED로 종료한다.
     */
    @Transactional(readOnly = true)
    public AddressLinkageResult downloadNewFiles(String targetDirectory, AddressLinkagePeriod period) {
        String referenceVersionName = ROAD_NAME_ADDRESS_KOR.getReferenceVersionName();
        ReferenceVersion referenceVersion = referenceVersionRepository.findByTargetName(referenceVersionName)
                .orElseThrow(() -> new RuntimeException(referenceVersionName + " 기준정보 버전 정보를 찾지 못했습니다."));

        if (!period.to().isAfter(referenceVersion.getEffectiveDate())) {
            return AddressLinkageResult.ALREADY_UP_TO_DATE;
        }

        executeInTenDayChunks(period, (from, to) -> addressLinkageApiPort.downloadUpdatedAddress(targetDirectory, from, to));
        return AddressLinkageResult.SUCCESS;
    }

    /**
     * 대상 디렉터리에 마지막 최신 반영 일자부터 현재 일자까지 주소 출입구 정보 연계 파일을 다운로드한다.
     * 대상 디렉터리 하위에 일자별로 yyMMdd 형태의 이름을 가진 서브디렉터리가 생성되며
     * 각 일자별로 파일이 다운로드된다.
     *
     * @param targetDirectory 다운로드 대상 디렉터리
     * @param period
     * @return
     * @throws JobInterruptedException
     */
    @Transactional(readOnly = true)
    public AddressLinkageResult downloadEntranceLinkageFiles(String targetDirectory, AddressLinkagePeriod period) {
        String referenceVersionName = ADDRESS_ENTRANCE.getReferenceVersionName();

        ReferenceVersion referenceVersion = referenceVersionRepository.findByTargetName(referenceVersionName)
                .orElseThrow(() -> new RuntimeException(referenceVersionName + " 기준정보 버전 정보를 찾지 못했습니다."));

        if (!period.to().isAfter(referenceVersion.getEffectiveDate())) {
            return AddressLinkageResult.ALREADY_UP_TO_DATE;
        }

        executeInTenDayChunks(period, (from, to) -> addressLinkageApiPort.downloadUpdatedAddressPosition(targetDirectory, from, to));
        return AddressLinkageResult.SUCCESS;
    }

    /**
     * 10일 단위 chunk로 나누어 API 요청
     * @param period 기간
     * @param action API 요청
     */
    private void executeInTenDayChunks(
            AddressLinkagePeriod period,
            BiConsumer<LocalDate, LocalDate> action
    ) {
        long between = ChronoUnit.DAYS.between(period.from(), period.to());
        if (between > 10) {
            long mul = between / 10;
            for (int i = 0; i < mul; i++) {
                action.accept(
                        period.from().plusDays(i * 10),
                        period.from().plusDays((i + 1) * 10 - 1)
                );
            }
            action.accept(period.from().plusDays(mul * 10), period.to());
        } else {
            action.accept(period.from(), period.to());
        }
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
                        .plusDays(1)
        );
    }

}
