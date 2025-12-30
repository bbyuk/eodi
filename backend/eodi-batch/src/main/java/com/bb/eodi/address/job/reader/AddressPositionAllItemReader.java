package com.bb.eodi.address.job.reader;

import com.bb.eodi.address.job.dto.AddressPositionItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * 도로명주소DB - 위치요약정보DB 전체분 ItemReader
 */
@Slf4j
public class AddressPositionAllItemReader extends AbstractAddressItemStreamReader<AddressPositionItem> {

    public AddressPositionAllItemReader(String filePath) {
        super(filePath);
    }

    @Override
    public AddressPositionItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String[] line = nextLine("\\|");

        if (line == null) return null;

        return AddressPositionItem.builder()
                .manageNo(line[0])
                .legalDongCode(line[1])
                .sidoName(line[2])
                .sigunguName(line[3])
                .umdName(line[4])
                .riName(line[5])
                .roadNameCode(line[6])
                .roadName(line[7])
                .isUnderground(line[8])
                .buildingMainNo(line[9])
                .buildingSubNo(line[10])
                .basicDistrictNo(line[11])
                .effectStartDate(line[12])
                .updateReasonCode(line[13])
                .entranceSeq(line[14])
                .entranceCode(line[15])
                .entranceType(line[16])
                .xPos(line[17])
                .yPos(line[18])
                .build();
    }
}
