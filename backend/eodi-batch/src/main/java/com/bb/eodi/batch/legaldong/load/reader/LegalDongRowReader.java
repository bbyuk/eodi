package com.bb.eodi.batch.legaldong.load.reader;

import com.bb.eodi.batch.legaldong.load.model.LegalDongRow;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 법정동 코드 데이터 reader
 */
@StepScope
@Component
public class LegalDongRowReader implements ItemReader<LegalDongRow> {

    private final BufferedReader reader;
    private final Path targetFilePath;

    public LegalDongRowReader(
            @Value("${app.file.base-path}") String basePath
    ) throws IOException {
        this.targetFilePath = Paths.get(basePath)
                .resolve("legaldong")
                .resolve("법정동코드 전체자료.txt");
        this.reader = Files.newBufferedReader(this.targetFilePath, Charset.forName("EUC-KR"));

        this.reader.readLine();
    }

    @Override
    public LegalDongRow read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String line = reader.readLine();
        if (line == null) {
            return null;
        }

        String[] tokens = line.split("\t");
        return LegalDongRow.builder()
                .legalDongCode(tokens[0])
                .legalDongName(tokens[1])
                .closeYn(tokens[2])
                .build();
    }
}
