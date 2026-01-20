package com.bb.eodi.address.job.tasklet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 주소 연계 API를 통해 다운로드 받은 파일을 모두 unzip 하는 Tasklet
 */
@Slf4j
@RequiredArgsConstructor
public class AddressLinkageFileUnzipTasklet implements Tasklet {

    private final String targetDirectory;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        File dir = new File(targetDirectory);
        File[] subDirectories = dir.listFiles();

        // 일자별 처리
        for (File subDirectory : Objects.requireNonNull(subDirectories)) {
            File[] zipFiles = subDirectory.listFiles();

            Arrays.stream(Objects.requireNonNull(zipFiles))
                    .forEach(zipFile -> {
                        // 현재 디렉터리에 풀기
                        Path targetPath = zipFile.getParentFile().toPath();

                        try (InputStream fis = new FileInputStream(zipFile);
                             BufferedInputStream bis = new BufferedInputStream(fis);
                             ZipInputStream zis = new ZipInputStream(bis)) {

                            Files.createDirectories(targetPath);

                            ZipEntry entry;
                            while ((entry = zis.getNextEntry()) != null) {

                                Path resolvedPath = targetPath.resolve(entry.getName()).normalize();

                                // Zip Slip 공격 방지 (필수)
                                if (!resolvedPath.startsWith(targetPath)) {
                                    throw new IOException("Invalid ZIP entry: " + entry.getName());
                                }

                                if (entry.isDirectory()) {
                                    Files.createDirectories(resolvedPath);
                                } else {
                                    Files.createDirectories(resolvedPath.getParent());

                                    try (OutputStream os = Files.newOutputStream(resolvedPath)) {
                                        zis.transferTo(os);
                                    }
                                }

                                zis.closeEntry();
                            }

                        } catch (IOException e) {
                            throw new RuntimeException("ZIP 압축 해제 실패", e);
                        }
                    });
        }

        return RepeatStatus.FINISHED;
    }
}
