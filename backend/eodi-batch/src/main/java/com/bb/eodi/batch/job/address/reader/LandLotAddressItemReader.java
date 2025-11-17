package com.bb.eodi.batch.job.address.reader;

import com.bb.eodi.batch.job.address.dto.LandLotAddressItem;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * 지번 주소 ItemReader
 */
public class LandLotAddressItemReader extends AbstractResourceAwareLineItemReader<LandLotAddressItem> {

    @Override
    public LandLotAddressItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String[] line = readLine("\\|");

        return LandLotAddressItem.builder()
                .legalDongCode(line[0])
                .sidoName(line[1])
                .sigunguName(line[2])
                .legalUmdName(line[3])
                .legalRiName(line[4])
                .isMountain(line[5])
                .landLotMainNo(line[6])
                .landLotSubNo(line[7])
                .roadNameCode(line[8])
                .isUnderground(line[9])
                .buildingMainNo(line[10])
                .buildingSubNo(line[11])
                .landLotSeq(line[12])
                .changeReasonCode(line[13])
                .build();
    }
}
