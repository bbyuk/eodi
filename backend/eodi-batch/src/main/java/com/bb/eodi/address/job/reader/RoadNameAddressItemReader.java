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
        String[] split = nextLine("\\|");

        if (split == null) return null;

        try {
            return RoadNameAddressItem
                    .builder()
                    .manageNo(split[0])
                    .roadNameCode(split[1])
                    .umdSeq(split[2])
                    .isUnderground(split[3])
                    .buildingMainNo(split[4])
                    .buildingSubNo(split[5])
                    .basicDistrictNo(split[6])
                    .changeReasonCode(split[7])
                    .entranceDate(split[8])
                    .beforeChangeRoadNameAddress(split[9])
                    .hasDetailAddress(split[10])
                    .build();
        } catch (ArrayIndexOutOfBoundsException e) {
            log.error("매핑 실패 : {}", e.getMessage(), e);
            throw new ItemStreamException(e);
        }
    }
}
