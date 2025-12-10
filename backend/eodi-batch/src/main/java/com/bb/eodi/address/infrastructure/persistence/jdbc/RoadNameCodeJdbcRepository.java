package com.bb.eodi.address.infrastructure.persistence.jdbc;

import com.bb.eodi.address.domain.entity.RoadNameCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.sql.Types.VARCHAR;

/**
 * 도로명코드 Jdbc Repository
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class RoadNameCodeJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public void insertBatch(List<? extends RoadNameCode> items, int batchSize) {

        String sql = """
                INSERT INTO road_name_code
                (
                    road_name_code,
                    road_name,
                    eng_road_name,
                    umd_seq,
                    sido_name,
                    eng_sido_name,
                    umd_name,
                    eng_umd_name,
                    umd_type,
                    umd_code,
                    enabled,
                    change_code,
                    entrance_date,
                    revocation_date
                )
                VALUES
                ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )
                """;
        jdbcTemplate.batchUpdate(sql, items, batchSize, (ps, entity) -> {
            ps.setObject(1, entity.getRoadNameCode(), VARCHAR);
            ps.setObject(2, entity.getRoadName(), VARCHAR);
            ps.setObject(3, entity.getEngRoadName(), VARCHAR);
            ps.setObject(4, entity.getUmdSeq(), VARCHAR);
            ps.setObject(5, entity.getSidoName(), VARCHAR);
            ps.setObject(6, entity.getEngSidoName(), VARCHAR);
            ps.setObject(7, entity.getUmdName(), VARCHAR);
            ps.setObject(8, entity.getEngUmdName(), VARCHAR);
            ps.setObject(9, entity.getUmdType(), VARCHAR);
            ps.setObject(10, entity.getUmdCode(), VARCHAR);
            ps.setObject(11, entity.getEnabled(), VARCHAR);
            ps.setObject(12, entity.getChangeCode(), VARCHAR);
            ps.setObject(13, entity.getEntranceDate(), VARCHAR);
            ps.setObject(14, entity.getRevocationDate(), VARCHAR);
        });
    }
}
