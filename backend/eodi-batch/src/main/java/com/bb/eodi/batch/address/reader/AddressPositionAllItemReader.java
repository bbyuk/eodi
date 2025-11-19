package com.bb.eodi.batch.address.reader;

import com.bb.eodi.batch.address.dto.AddressPositionItem;
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
                .sigunguCode(line[0])
                .entranceSeq(line[1])
                .legalDongCode(line[2])
                .sidoName(line[3])
                .sigunguName(line[4])
                .umdName(line[5])
                .roadNameCode(line[6])
                .roadName(line[7])
                .isUnderground(line[8])
                .buildingMainNo(line[9])
                .buildingSubNo(line[10])
                .buildingName(line[11])
                .zipNo(line[12])
                .buildingType(line[13])
                .isBuildingGroup(line[14])
                .admDong(line[15])
                .xPos(line[16])
                .yPos(line[17])
                .build();
    }
}
