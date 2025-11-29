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
public class RoadNameAddressItemReader extends AbstractResourceAwareLineItemReader<RoadNameAddressItem> {

    @Override
    public RoadNameAddressItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String[] split = readLine("\\|");

        if (split == null) return null;

        try {
            return RoadNameAddressItem
                    .builder()
                    .sigunguCode(split[0])
                    .roadNameNo(split[1])
                    .roadName(split[2])
                    .engRoadName(split[3])
                    .umdSeq(split[4])
                    .sidoName(split[5])
                    .sigunguName(split[6])
                    .umdGb(split[7])
                    .umdCode(split[8])
                    .umdName(split[9])
                    .parentRoadNameNo(split[10])
                    .parentRoadName(split[11])
                    .useYn(split[12])
                    .changeHistoryReason(split[13])
                    .changeHistoryInfo(split[14])
                    .engSidoName(split[15])
                    .engSigunguName(split[16])
                    .engUmdName(split[17])
                    .announcementDate(split[18])
                    .expirationDate(split.length == 20 ? split[19] : null)
                    .build();
        } catch (ArrayIndexOutOfBoundsException e) {
            log.error("매핑 실패 : {}", e.getMessage(), e);
            throw new ItemStreamException(e);
        }
    }
}
