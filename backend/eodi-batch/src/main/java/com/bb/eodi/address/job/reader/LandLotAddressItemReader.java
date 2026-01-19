package com.bb.eodi.address.job.reader;

import com.bb.eodi.address.job.dto.LandLotAddressItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.bb.eodi.deal.domain.eunms.MonthlyDealDataLoadJobKey.CURRENT_INDEX;

/**
 * 지번 주소 ItemReader
 */
@Slf4j
public class LandLotAddressItemReader extends AbstractAddressItemStreamReader<LandLotAddressItem> {

    public LandLotAddressItemReader(String filePath) {
        super(filePath);
    }

    @Override
    public LandLotAddressItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String[] line = nextLine("\\|");

        if (line == null) return null;

        if (line.length == 1 && line[0].equals("No Data")) {
            return null;
        }

        return LandLotAddressItem.builder()
                .manageNo(line[0])              // 도로명주소관리번호
                .legalDongCode(line[1])         // 법정동코드
                .sidoName(line[2])              // 시도명
                .sigunguName(line[3])           // 시군구명
                .umdName(line[4])               // 법정읍면동명
                .riName(line[5])                // 법정리명
                .isMountain(line[6])            // 산여부
                .landLotMainNo(line[7])         // 지번본번
                .landLotSubNo(line[8])          // 지번부번
                .roadNameCode(line[9])          // 도로명코드
                .isUnderground(line[10])        // 지하여부
                .buildingMainNo(line[11])       // 건물본번
                .buildingSubNo(line[12])        // 건물부번
                .updateReasonCode(line[13])     // 이동사유코드
                .build();
    }
}
