package com.bb.eodi.address.job.reader;

import com.bb.eodi.address.job.dto.RoadNameCodeItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * 도로명코드 ItemReader
 */
@Slf4j
public class RoadNameCodeItemReader extends AbstractAddressItemStreamReader<RoadNameCodeItem> {


    public RoadNameCodeItemReader(String filePath) {
        super(filePath);
    }

    @Override
    public RoadNameCodeItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String[] split = nextLine("\\|");

        if (split == null) {
            return null;
        }

        return RoadNameCodeItem
                .builder()
                .roadNameCode(split[0])
                .roadName(split[1])
                .engRoadName(split[2])
                .umdSeq(split[3])
                .sidoName(split[4])
                .engSidoName(split[5])
                .sigunguName(split[6])
                .engSigunguName(split[7])
                .umdName(split[8])
                .engUmdName(split[9])
                .umdType(split[10])
                .umdCode(split[11])
                .enabled(split[12])
                .changeCode(split[13])
                .changeLog(split[14])
                .entranceDate(split[15])
                .revocationDate(split[16])
                .build();
    }
}
