package com.bb.eodi.domain.geo.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;
@DisplayName("공간정보 파일 reader 테스트")
class ShapeFileReaderTest {

    ShapeFileReader reader = new ShapeFileReader("C:/Users/User/Downloads/N3A_G0100000/N3A_G0100000.shp");


    @Test
    @DisplayName("small - reader 테스트")
    void testShapeReader() throws Exception {
//        // given
//        Path filePath = Paths.get("C:/Users/User/Downloads/N3A_G0100000/N3A_G0100000.shp");
//
//        // when
//        Map<String, Geometry> read = reader.read(filePath);
//
//        // then
//        Assertions.assertThat(read).hasSize(264);
    }

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