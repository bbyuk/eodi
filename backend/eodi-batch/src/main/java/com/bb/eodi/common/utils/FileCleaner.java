package com.bb.eodi.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 파일 처리 후 재귀적으로 파일을 삭제하는 클래스
 */
@Slf4j
public class FileCleaner {

    public static void deleteAll(Path path) {
        try {
            if (!Files.exists(path)) {
                return;
            }

            if (Files.isDirectory(path)) {
                Files.list(path).forEach(FileCleaner::deleteAll);
            }

            Files.delete(path);
        }
        catch(IOException e) {
            log.error("파일을 삭제하는 과정에서 문제가 발생했습니다.", e);
            throw new RuntimeException(e);
        }
    }
}
