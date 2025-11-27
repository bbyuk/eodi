package com.bb.eodi.address.infrastructure.persistence.jdbc;

import com.bb.eodi.address.domain.entity.LandLotAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.sql.Types.*;

/**
 * 지번주소 JdbcRepository
 */
@Repository
@RequiredArgsConstructor
public class LandLotAddressJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * batch save
     * @param entities 저장할 entity
     */
    public void saveAll(List<? extends LandLotAddress> entities) {

        String sql = """
                INSERT INTO land_lot_address
                (
                    legal_dong_code,
                    sido_name,
                    sigungu_name,
                    legal_umd_name,
                    legal_ri_name,
                    is_mountain,
                    land_lot_main_no,
                    land_lot_sub_no,
                    land_lot_seq,
                    road_name_code,
                    is_underground,
                    building_main_no,
                    building_sub_no,
                    change_reason_code
                )
                VALUES
                (
                    ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?
                )
                """;

        jdbcTemplate.batchUpdate(
                sql,
                entities,
                500,
                (ps, entity) -> {
                    ps.setObject(1, entity.getLegalDongCode(), VARCHAR);
                    ps.setObject(2, entity.getSidoName(), VARCHAR);
                    ps.setObject(3, entity.getSigunguName(), VARCHAR);
                    ps.setObject(4, entity.getLegalUmdName(), VARCHAR);
                    ps.setObject(5, entity.getLegalRiName(), VARCHAR);
                    ps.setObject(6, entity.getIsMountain(), VARCHAR);
                    ps.setObject(7, entity.getLandLotMainNo(), INTEGER);
                    ps.setObject(8, entity.getLandLotSubNo(), INTEGER);
                    ps.setObject(9, entity.getLandLotSeq(), BIGINT);
                    ps.setObject(10, entity.getRoadNameCode(), VARCHAR);
                    ps.setObject(11, entity.getIsUnderground(), VARCHAR);
                    ps.setObject(12, entity.getBuildingMainNo(), INTEGER);
                    ps.setObject(13, entity.getBuildingSubNo(), INTEGER);
                    ps.setObject(14, entity.getChangeReasonCode(), VARCHAR);
                }
        );
    }

}
