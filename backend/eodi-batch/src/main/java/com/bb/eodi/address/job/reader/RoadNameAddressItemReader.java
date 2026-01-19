package com.bb.eodi.address.job.reader;

import com.bb.eodi.address.job.dto.RoadNameAddressItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * 도로명주소 단일 ItemReader
 */
@Slf4j
public class RoadNameAddressItemReader extends AbstractAddressItemStreamReader<RoadNameAddressItem> {

    public RoadNameAddressItemReader(String filePath) {
        super(filePath);
    }

    @Override
    public RoadNameAddressItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String[] line = nextLine("\\|");

        if (line == null) return null;

        if (line.length == 1 && line[0].equals("No Data")) {
            return null;
        }

        try {
            return RoadNameAddressItem.builder()
                    .manageNo(line[0])                    // 도로명주소관리번호
                    .legalDongCode(line[1])               // 법정동코드
                    .sidoName(line[2])                    // 시도명
                    .sigunguName(line[3])                 // 시군구명
                    .umdName(line[4])                     // 법정읍면동명
                    .riName(line[5])                      // 법정리명
                    .isMountain(line[6])                  // 산여부
                    .landLotMainNo(line[7])               // 지번본번
                    .landLotSubNo(line[8])                // 지번부번
                    .roadNameCode(line[9])                // 도로명코드
                    .roadName(line[10])                   // 도로명
                    .isUnderground(line[11])              // 지하여부
                    .buildingMainNo(line[12])             // 건물본번
                    .buildingSubNo(line[13])              // 건물부번
                    .admDongCode(line[14])                // 행정동코드
                    .admDongName(line[15])                // 행정동명
                    .basicDistrictNo(line[16])            // 기초구역번호
                    .beforeRoadNameAddress(line[17])      // 이전도로명주소
                    .effectStartDate(line[18])            // 효력발생일
                    .isMulti(line[19])                    // 공동주택구분
                    .updateReasonCode(line[20])           // 이동사유코드
                    .buildingName(line[21])               // 건축물대장건물명
                    .sigunguBuildingName(line[22])        // 시군구용건물명
                    .remark(line[23])                     // 비고
                    .build();
        } catch (ArrayIndexOutOfBoundsException e) {
            log.error("매핑 실패 : {}", e.getMessage(), e);
            throw new ItemStreamException(e);
        }
    }
}
