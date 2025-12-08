package com.bb.eodi.address.infrastructure.persistence.jdbc;

import com.bb.eodi.address.domain.entity.RoadNameAddress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import static java.sql.Types.*;

/**
 * 도로명주소 JdbcRepository
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class RoadNameAddressJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public void insertBatch(List<? extends RoadNameAddress> items) {
        log.debug("RoadNameAddressJdbcRepository.insertBatch");
        String sql = """
                INSERT INTO road_name_address
                (
                    manage_no,
                    road_name_code,
                    umd_seq,
                    is_underground,
                    building_main_no,
                    building_sub_no,
                    basic_district_no,
                    has_detail_address
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;
        jdbcTemplate.batchUpdate(sql, items, 500, (ps, entity) -> {
            ps.setObject(1, entity.getManageNo(), VARCHAR);
            ps.setObject(2, entity.getRoadNameCode(), VARCHAR);
            ps.setObject(3, entity.getUmdSeq(), VARCHAR);
            ps.setObject(4, entity.getIsUnderground(), VARCHAR);
            ps.setObject(5, entity.getBuildingMainNo(), INTEGER);
            ps.setObject(6, entity.getBuildingSubNo(), INTEGER);
            ps.setObject(7, entity.getBasicDistrictNo(), VARCHAR);
            ps.setObject(8, entity.getHasDetailAddress(), VARCHAR);
        });

    }

    /**
     * 도로명주소 부가정보에 해당하는 컬럼을 batch update 한다.
     * @param items batch update 대상 item 목록
     */
    public void batchUpdateAdditionalInfo(Collection<? extends RoadNameAddress> items) {
        String sql = """
                UPDATE  road_name_address
                SET     building_name = ?
                WHERE   id = ?
                """;
        jdbcTemplate.batchUpdate(sql, items, 500, (ps, entity) -> {
            ps.setObject(1, entity.getBuildingName(), VARCHAR);
            ps.setObject(2, entity.getId(), BIGINT);
        });
    }
}
