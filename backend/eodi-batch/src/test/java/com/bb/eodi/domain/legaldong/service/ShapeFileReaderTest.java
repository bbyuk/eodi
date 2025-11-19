package com.bb.eodi.domain.legaldong.service;

import com.bb.eodi.batch.legaldong.service.ShapeFileReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;
@DisplayName("공간정보 파일 reader 테스트")
class ShapeFileReaderTest {

    ShapeFileReader reader = new ShapeFileReader("/Users/kanghyuk/Desktop/workspace/eodi/bootstrap/init/geo/N3A_G0100000/N3A_G0100000.shp");

    @Test
    @DisplayName("small - adjacencyMap make 메소드 테스트")
    void testMakeAdjacencyMap() throws Exception {
        // given
        // when
        Map<String, Set<String>> adjacencyMap = reader.makeAdjacencyMap();

        // then
        adjacencyMap.forEach((code, adjacents) ->
                System.out.println(code + " → " + String.join(", ", adjacents))
        );

        reader.disposeDataStore();
    }
}