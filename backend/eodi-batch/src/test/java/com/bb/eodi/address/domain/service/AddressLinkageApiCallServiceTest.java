package com.bb.eodi.address.domain.service;

import com.bb.eodi.common.utils.FileCleaner;
import com.bb.eodi.ops.domain.entity.ReferenceVersion;
import com.bb.eodi.ops.domain.enums.ReferenceTarget;
import com.bb.eodi.ops.domain.repository.ReferenceVersionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@DisplayName("medium - 주소 연계 API 호출 서비스 연동 테스트")
@SpringBootTest
@Transactional
class AddressLinkageApiCallServiceTest {

    @MockitoBean
    private ReferenceVersionRepository referenceVersionRepository;

    @Autowired
    private AddressLinkageApiCallService service;

    @BeforeEach
    public void beforeEach() {
        when(referenceVersionRepository.findByTargetName(any()))
                .thenReturn(Optional.of(
                        ReferenceVersion.builder()
                                .targetName(ReferenceTarget.ADDRESS.getValue())
                                .effectiveDate(LocalDate.of(2025, 11, 30))
                                .build()
                ));
    }


    @Test
    @DisplayName("대상 기간 조회 테스트")
    void testFindTargetPeriod() throws Exception {
        // given


        // when
        AddressLinkagePeriod targetPeriod = service.findTargetPeriod(ReferenceTarget.ADDRESS.value);


        // then
        Assertions.assertThat(targetPeriod.duration()).isEqualTo(45);
    }

    @Test
    @DisplayName("연계 API 호출 및 다운로드 테스트")
    void testLinkageApiCallAndDownload() throws Exception {
        // given

        // when
        AddressLinkagePeriod targetPeriod = service.findTargetPeriod(ReferenceTarget.ADDRESS.getValue());
        String targetDirectory = "C:\\Users\\User\\Desktop\\private\\workspace\\eodi-project\\eodi\\bootstrap\\address\\address_temp";
        AddressLinkageResult addressLinkageResult = service.downloadNewFiles(
                targetDirectory,
                targetPeriod
        );

        File dir = new File(targetDirectory);

        // then
        try {
            Assertions.assertThat(dir.listFiles().length).isEqualTo(46);
        }
        finally {
            Arrays.stream(dir.listFiles())
                    .map(File::toPath)
                    .forEach(FileCleaner::deleteAll);
        }
    }


}