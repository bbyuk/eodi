package com.bb.eodi.domain.legaldong.service;

import lombok.extern.slf4j.Slf4j;
import org.geotools.api.data.FileDataStore;
import org.geotools.api.data.FileDataStoreFinder;
import org.geotools.api.data.SimpleFeatureSource;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.geometry.jts.WKBReader;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKBWriter;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * shp 파일을 읽어 공간정보를 파싱하기 위한 파서 서비스
 */
@Slf4j
@StepScope
@Component
public class ShapeFileReader {

    private static final String FILE_CHARSET = "EUC-KR";
    private static final String CODE_KEY = "BJCD";

    private final Path filePath;

    private FileDataStore dataStore;

    private final Map<String, byte[]> GEOMETRY_MAP = new HashMap<>();
    private final WKBReader wkbReader = new WKBReader();
    private final WKBWriter wkbWriter = new WKBWriter();


    public ShapeFileReader(@Value("#{jobParameters['file-path']}") String filePath) {
        this.filePath = Paths.get(filePath);
        initialRead();
    }

    /**
     * shp 파일을 읽어 법정동 코드 기준 인덱스 맵, 법정동 set을 생성해 필드에 저장한다.
     */
    public void initialRead() {
        try {
            FileDataStore dataStore = FileDataStoreFinder.getDataStore(filePath.toFile());
            if (!(dataStore instanceof ShapefileDataStore shapefileStore)) {
                System.out.println("Not a shapefile data store.");
                return;
            }

            shapefileStore.setCharset(Charset.forName(FILE_CHARSET));
            this.dataStore = shapefileStore;

            SimpleFeatureSource featureSource = dataStore.getFeatureSource();
            if (featureSource == null) return;

            try (SimpleFeatureIterator it = featureSource.getFeatures().features()) {
                int idx = 0;
                while (it.hasNext()) {
                    SimpleFeature f = it.next();
                    String legalDongCode = String.valueOf(f.getAttribute(CODE_KEY));
                    GEOMETRY_MAP.putIfAbsent(legalDongCode, wkbWriter.write((Geometry) f.getDefaultGeometry()));
                }
            }
        }
        catch(IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("인덱스 맵을 만드는 중 에러가 발생했습니다.");
        }
    }

    /**
     * 대상 법정동 코드에 해당하는 Geometry 객체를 파싱해 리턴한다.
     * @param legalDongCode 법정동코드
     * @return Geometry 객체
     */
    public Geometry getGeometry(String legalDongCode) {
        try {
            return wkbReader.read(GEOMETRY_MAP.get(legalDongCode));
        }
        catch(ParseException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("Geometry 파싱 중 에러가 발생했습니다.");
        }
    }

    /**
     * shp파일을 읽어 만든 geometryMap을 읽어 법정동 기준 행정경계 인접 테이블을 생성한다.
     *
     * @return 법정동 코드 기준 행정경계 인접 테이블
     */
    public Map<String, Set<String>> makeAdjacencyMap() {
        Map<String, Set<String>> adjacencyMap = new LinkedHashMap<>();
        List<String> targetLegalDongCodes = new ArrayList<>(GEOMETRY_MAP.keySet());

        for (int i = 0; i < targetLegalDongCodes.size(); i++) {
            String sourceLegalDongCode = targetLegalDongCodes.get(i);
            Geometry sourceGeometry = getGeometry(sourceLegalDongCode);

            for (int j = i + 1; j < targetLegalDongCodes.size(); j++) {
                String targetLegalDongCode = targetLegalDongCodes.get(j);
                Geometry targetGeometry = getGeometry(targetLegalDongCode);

                if (sourceGeometry.touches(targetGeometry)) {
                    log.info("adjacent : {} <-> {}", sourceLegalDongCode, targetLegalDongCode);
                    adjacencyMap.computeIfAbsent(sourceLegalDongCode, k -> new LinkedHashSet<>()).add(targetLegalDongCode);
                    adjacencyMap.computeIfAbsent(targetLegalDongCode, k -> new LinkedHashSet<>()).add(sourceLegalDongCode);
                }
            }
        }

        return adjacencyMap;
    }

    /**
     * DataStore를 dispose 한다.
     */
    public void disposeDataStore() {
        if (this.dataStore == null) return;

        this.dataStore.dispose();
    }
}
