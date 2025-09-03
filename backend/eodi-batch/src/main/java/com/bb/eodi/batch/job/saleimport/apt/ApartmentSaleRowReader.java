package com.bb.eodi.batch.job.saleimport.apt;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 아파트 매매 데이터 import job processor
 */

@StepScope
@Component
public class ApartmentSaleRowReader implements ItemReader<ApartmentSaleRow> {

    private final BufferedReader reader;
    private final Path targetFilePath;

    public ApartmentSaleRowReader(
            @Value("#{jobParameters['region']}") String region,
            @Value("#{jobParameters['mode']}") String mode,
            @Value("${app.file.base-path}") String basePath) throws IOException {
        this.targetFilePath = Paths.get(basePath).resolve(resolveTargetFilePath(region, mode));
        this.reader = Files.newBufferedReader(this.targetFilePath, StandardCharsets.UTF_8);

        // 헤더 라인 건너뛰기
        this.reader.readLine();
    }

    // 파일 경로 생성
    private Path resolveTargetFilePath(String region, String mode) {
        /**
         * TODO region, mode job parameter로 파일 경로 생성 로직 구현
         */
        return Paths.get(region).resolve("apt").resolve("sale_annual_2508.csv");
    }

    @Override
    public ApartmentSaleRow read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        /**
         * 데이터 read
         */
        String line = reader.readLine();
        if (line == null) {
            return null; // EOF → Step 종료 신호
        }

        String[] tokens = line.split(",");
        return ApartmentSaleRow.builder()
                .sigungu(tokens[1])
                .complexName(tokens[5])
                .netLeasableArea(tokens[6])
                .contractYearMonth(tokens[7])
                .contractDay(tokens[8])
                .price(tokens[9])
                .unit(tokens[10])
                .floor(tokens[11])
                .buyer(tokens[12])
                .seller(tokens[13])
                .buildYear(tokens[14])
                .roadName(tokens[15])
                .cancelDate(tokens[16])
                .tradeMethod(tokens[17])
                .registrationDate(tokens[19])
                .build();
    }
}
