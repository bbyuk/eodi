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
     *
     * @param entities 저장할 entity
     */
    public void insertBatch(List<? extends LandLotAddress> entities, int batchSize) {

        String sql = """
                INSERT INTO land_lot_address
                (
                    manage_no,
                    legal_dong_code,
                    sido_name,
                    sigungu_name,
                    umd_name,
                    ri_name,
                
                    is_mountain,
                    land_lot_main_no,
                    land_lot_sub_no,
                
                    road_name_code,
                    is_underground,
                    building_main_no,
                    building_sub_no,
                
                    update_reason_code
                )
                VALUES
                (
                    ?, ?, ?, ?, ?, ?,
                    ?, ?, ?,
                    ?, ?, ?, ?,
                    ?
                )
                """;

        jdbcTemplate.batchUpdate(
                sql,
                entities,
                batchSize,
                (ps, entity) -> {

                    int i = 1;

                    // === 도메인 키
                    ps.setObject(i++, entity.getManageNo(), VARCHAR);
                    ps.setObject(i++, entity.getLegalDongCode(), VARCHAR);
                    ps.setObject(i++, entity.getSidoName(), VARCHAR);
                    ps.setObject(i++, entity.getSigunguName(), VARCHAR);
                    ps.setObject(i++, entity.getUmdName(), VARCHAR);
                    ps.setObject(i++, entity.getRiName(), VARCHAR);

                    // === 지번 정보
                    ps.setObject(i++, entity.getIsMountain(), VARCHAR);
                    ps.setObject(i++, entity.getLandLotMainNo(), INTEGER);
                    ps.setObject(i++, entity.getLandLotSubNo(), INTEGER);

                    // === 도로명 / 건물 정보
                    ps.setObject(i++, entity.getRoadNameCode(), VARCHAR);
                    ps.setObject(i++, entity.getIsUnderground(), VARCHAR);
                    ps.setObject(i++, entity.getBuildingMainNo(), INTEGER);
                    ps.setObject(i++, entity.getBuildingSubNo(), INTEGER);

                    // === 이력
                    ps.setObject(i++, entity.getUpdateReasonCode(), VARCHAR);
                }
        );
    }

    /**
     * 관련지번 주소를 batch update한다.
     *
     * @param entities  batch update 대상 entities
     * @param batchSize batch size
     */
    public void updateBatch(List<? extends LandLotAddress> entities, int batchSize) {
        String sql = """
                UPDATE  land_lot_address
                SET     sido_name = ?,
                        sigungu_name = ?,
                        umd_name = ?,
                        ri_name = ?,
                        is_underground = ?,
                        building_main_no = ?,
                        building_sub_no = ?,
                        update_reason_code = ?
                WHERE   manage_no = ?,
                AND     legal_dong_code = ?,
                AND     is_mountain = ?,
                AND     land_lot_main_no = ?,
                AND     land_lot_sub_no = ?,
                AND     road_name_code = ?
                """;
        jdbcTemplate.batchUpdate(
                sql, entities, batchSize, (ps, entity) -> {
                    ps.setObject(1, entity.getSidoName(), VARCHAR);
                    ps.setObject(2, entity.getSigunguName(), VARCHAR);
                    ps.setObject(3, entity.getUmdName(), VARCHAR);
                    ps.setObject(4, entity.getRiName(), VARCHAR);
                    ps.setObject(5, entity.getIsUnderground(), VARCHAR);
                    ps.setObject(6, entity.getBuildingMainNo(), INTEGER);
                    ps.setObject(7, entity.getBuildingSubNo(), INTEGER);
                    ps.setObject(8, entity.getUpdateReasonCode(), VARCHAR);
                    ps.setObject(9, entity.getManageNo(), VARCHAR);
                    ps.setObject(10, entity.getLegalDongCode(), VARCHAR);
                    ps.setObject(11, entity.getIsMountain(), VARCHAR);
                    ps.setObject(12, entity.getLandLotMainNo(), INTEGER);
                    ps.setObject(13, entity.getLandLotSubNo(), INTEGER);
                    ps.setObject(14, entity.getRoadNameCode(), VARCHAR);
                }
        );
    }

    /**
     * 관련지번 주소를 batch delete한다.
     *
     * @param entities  batch delete 대상 entities
     * @param batchSize bathc size
     */
    public void deleteBatch(List<LandLotAddress> entities, int batchSize) {
        String sql = """
                DELETE 
                FROM    land_lot_address
                WHERE   manage_no = ?
                AND     legal_dong_code = ?
                AND     is_mountain = ?
                AND     land_lot_main_no = ?
                AND     land_lot_sub_no = ?
                AND     road_name_code = ?
                """;
        jdbcTemplate.batchUpdate(sql, entities, batchSize, (ps, entity) -> {
            ps.setObject(1, entity.getManageNo(), VARCHAR);
            ps.setObject(2, entity.getLegalDongCode(), VARCHAR);
            ps.setObject(3, entity.getIsMountain(), VARCHAR);
            ps.setObject(4, entity.getLandLotMainNo(), INTEGER);
            ps.setObject(5, entity.getLandLotSubNo(), INTEGER);
            ps.setObject(6, entity.getRoadNameCode(), VARCHAR);
        });
    }
}
