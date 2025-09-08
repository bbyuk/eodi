package com.bb.eodi.batch.legaldong.load.reader;

import com.bb.eodi.batch.legaldong.load.model.LegalDongApiResponse;
import com.bb.eodi.batch.legaldong.load.model.LegalDongRow;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 법정동 코드 데이터 reader
 */
@Slf4j
@StepScope
@Component
public class LegalDongRowReader implements ItemReader<LegalDongRow> {

//    private final WebClient legalDongApiClient;
//    private final String region;
//    private final int pageNum;

    public LegalDongRowReader() {
//        this.legalDongApiClient = legalDongApiClient;
//        this.region = region;
//        this.pageNum = Integer.parseInt(pageNum);
    }

    @Override
    public LegalDongRow read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
//        // api 요청
//        LegalDongApiResponse response = legalDongApiClient.get()
//                .uri(uriBuilder -> uriBuilder
//                        .queryParam("numOfRows", RESPONSE_PAGE_SIZE)
//                        .queryParam("region", region)
//                        .queryParam("pageNo", pageNum)
//                        .queryParam("type", "json")
//                        .build()
//                )
//                .retrieve()
//                .bodyToMono(LegalDongApiResponse.class)
//                .block();
//
//        if (response.isSuccess()) {
//            System.out.println("성공");
//        }
//        else {
//            System.out.println("실패");
//        }
//
//        return null;
        return null;
    }
}
