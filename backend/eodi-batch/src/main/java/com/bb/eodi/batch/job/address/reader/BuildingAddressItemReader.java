package com.bb.eodi.batch.job.address.reader;

import com.bb.eodi.batch.job.address.dto.BuildingAddressItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * 건물DB - 건물주소 전체 및 변동 레이아웃 ItemReader
 */
@Slf4j
public class BuildingAddressItemReader extends AbstractResourceAwareLineItemReader {

    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String[] line = readLine("\\|");

        if (line == null) return null;

        return BuildingAddressItem.builder()
                .legalDongCode(line[0])
                .sidoName(line[1])
                .sigunguName(line[2])
                .legalUmdName(line[3])
                .legalRiName(line[4])
                .isMountain(line[5])
                .landLotMainNo(line[6])
                .landLotSubNo(line[7])
                .roadNameCode(line[8])
                .roadName(line[9])
                .isUnderground(line[10])
                .buildingMainNo(line[11])
                .buildingSubNo(line[12])
                .buildingName(line[13])
                .buildingNameDetail(line[14])
                .buildingManageNo(line[15])
                .umdSeq(line[16])
                .admDongCode(line[17])
                .admDongName(line[18])
                .zipNo(line[19])
                .zipNoSeq(line[20])
                .changeReasonCode(line[22])
                .announcementDate(line[23])
                .sigunguBuildingName(line[25])
                .isComplex(line[26])
                .basicDistrictNo(line[27])
                .hasDetailAddress(line[28])
                .remark1(line[29])
                .remark2(line[30])
                .build();
    }
}
