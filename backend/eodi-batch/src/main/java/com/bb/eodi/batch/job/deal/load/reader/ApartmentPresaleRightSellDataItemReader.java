package com.bb.eodi.batch.job.deal.load.reader;

import com.bb.eodi.port.out.deal.dto.ApartmentPreSaleRightSellDataItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;

/**
 * 아파트 분양권 매매 데이터 ItemReader
 */
@Slf4j
@StepScope
@Component
public class ApartmentPresaleRightSellDataItemReader extends AbstractRealEstateDealDataItemStream
        implements ItemReader<ApartmentPreSaleRightSellDataItem> {

    public ApartmentPresaleRightSellDataItemReader(
            @Value("#{jobExecutionContext['TEMP_FILE']}") String tempFilePath,
            ObjectMapper objectMapper
    ) {
        super(Paths.get(tempFilePath), objectMapper);
    }

    @Override
    public ApartmentPreSaleRightSellDataItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String line = nextLine();
        log.info("ApartmentPresaleRightSellDataItemReader -> read line={}", line);
        return line == null ? null : objectMapper.readValue(line, ApartmentPreSaleRightSellDataItem.class);
    }


}
