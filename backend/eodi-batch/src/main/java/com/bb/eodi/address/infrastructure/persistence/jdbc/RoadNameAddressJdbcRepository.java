package com.bb.eodi.address.infrastructure.persistence.jdbc;

import com.bb.eodi.address.domain.dto.AddressPositionMappingParameter;
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

    public void insertBatch(List<? extends RoadNameAddress> items, int batchSize) {
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
        jdbcTemplate.batchUpdate(sql, items, batchSize, (ps, entity) -> {
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
    public void batchUpdateAdditionalInfo(Collection<? extends RoadNameAddress> items, int batchSize) {
        String sql = """
                UPDATE  road_name_address
                SET     building_name = ?
                WHERE   id = ?
                """;
        jdbcTemplate.batchUpdate(sql, items, batchSize, (ps, entity) -> {
            ps.setObject(1, entity.getBuildingName(), VARCHAR);
            ps.setObject(2, entity.getId(), BIGINT);
        });
    }

    /**
     * 도로명주소 주소위치정보를 배치 업데이트한다.
     * @param items batch update 파라미터
     */
    public void batchUpdatePosition(Collection<? extends AddressPositionMappingParameter> items, int batchSize) {
        String sql = """
                UPDATE  road_name_address rna
                JOIN    land_lot_address lla
                ON      rna.manage_no = lla.manage_no
                SET     rna.x_pos = ?,
                        rna.y_pos = ?
                WHERE   lla.legal_dong_code = ?
                AND     rna.road_name_code = ?
                AND     rna.building_main_no = ?
                AND     rna.building_sub_no = ?
                AND     rna.is_underground = ?
                """;
        jdbcTemplate.batchUpdate(sql, items, batchSize, (ps, entity) -> {
            ps.setObject(1, entity.getXPos(), DECIMAL);
            ps.setObject(2, entity.getYPos(), DECIMAL);
            ps.setObject(3, entity.getLegalDongCode(), VARCHAR);
            ps.setObject(4, entity.getRoadNameCode(), VARCHAR);
            ps.setObject(5, entity.getBuildingMainNo(), INTEGER);
            ps.setObject(6, entity.getBuildingSubNo(), INTEGER);
            ps.setObject(7, entity.getIsUnderground(), VARCHAR);
        });
    }
}
