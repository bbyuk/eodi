package com.bb.eodi.address.infrastructure.persistence.jdbc;

import com.bb.eodi.address.domain.dto.AddressPositionMappingParameter;
import com.bb.eodi.address.domain.entity.RoadNameAddress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void insertBatch(List<? extends RoadNameAddress> items, int batchSize) {
        log.debug("RoadNameAddressJdbcRepository.insertBatch");
        String sql = """
                INSERT INTO road_name_address
                (
                    manage_no,
                    road_name_code,
                    is_underground,
                    building_main_no,
                    building_sub_no,
                
                    legal_dong_code,
                    sido_name,
                    sigungu_name,
                    umd_name,
                    ri_name,
                
                    is_mountain,
                    land_lot_main_no,
                    land_lot_sub_no,
                
                    road_name,
                    adm_dong_code,
                    adm_dong_name,
                    basic_district_no,
                
                    before_road_name_address,
                    effect_start_date,
                    is_multi,
                    update_reason_code,
                
                    building_name,
                    sigungu_building_name,
                    remark,
                
                    x_pos,
                    y_pos
                )
                VALUES (
                    ?, ?, ?, ?, ?,
                    ?, ?, ?, ?, ?,
                    ?, ?, ?,
                    ?, ?, ?, ?,
                    ?, ?, ?, ?,
                    ?, ?, ?,
                    ?, ?
                )
                """;
        jdbcTemplate.batchUpdate(sql, items, batchSize, (ps, entity) -> {

            int i = 1;

            // === 도메인 키 (UNIQUE)
            ps.setObject(i++, entity.getManageNo(), VARCHAR);
            ps.setObject(i++, entity.getRoadNameCode(), VARCHAR);
            ps.setObject(i++, entity.getIsUnderground(), VARCHAR);
            ps.setObject(i++, entity.getBuildingMainNo(), INTEGER);
            ps.setObject(i++, entity.getBuildingSubNo(), INTEGER);

            // === 행정 주소
            ps.setObject(i++, entity.getLegalDongCode(), VARCHAR);
            ps.setObject(i++, entity.getSidoName(), VARCHAR);
            ps.setObject(i++, entity.getSigunguName(), VARCHAR);
            ps.setObject(i++, entity.getUmdName(), VARCHAR);
            ps.setObject(i++, entity.getRiName(), VARCHAR);

            // === 지번 매핑
            ps.setObject(i++, entity.getIsMountain(), VARCHAR);
            ps.setObject(i++, entity.getLandLotMainNo(), INTEGER);
            ps.setObject(i++, entity.getLandLotSubNo(), INTEGER);

            // === 도로명 상세
            ps.setObject(i++, entity.getRoadName(), VARCHAR);
            ps.setObject(i++, entity.getAdmDongCode(), VARCHAR);
            ps.setObject(i++, entity.getAdmDongName(), VARCHAR);
            ps.setObject(i++, entity.getBasicDistrictNo(), VARCHAR);

            // === 이력 / 상태
            ps.setObject(i++, entity.getBeforeRoadNameAddress(), VARCHAR);
            ps.setObject(i++, entity.getEffectStartDate(), VARCHAR);
            ps.setObject(i++, entity.getIsMulti(), VARCHAR);
            ps.setObject(i++, entity.getUpdateReasonCode(), VARCHAR);

            // === 건물명
            ps.setObject(i++, entity.getBuildingName(), VARCHAR);
            ps.setObject(i++, entity.getSigunguBuildingName(), VARCHAR);
            ps.setObject(i++, entity.getRemark(), VARCHAR);

            // === 좌표
            ps.setObject(i++, entity.getXPos(), DECIMAL);
            ps.setObject(i++, entity.getYPos(), DECIMAL);
        });

    }

    /**
     * 도로명주소 부가정보에 해당하는 컬럼을 batch update 한다.
     *
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
     *
     * @param items batch update 파라미터
     */
    public void batchUpdatePosition(Collection<? extends AddressPositionMappingParameter> items, int batchSize) {
        String sql = """
                UPDATE  road_name_address rna
                JOIN    land_lot_address lla
                ON      rna.manage_no = lla.manage_no
                SET     rna.x_pos = :xPos,
                        rna.y_pos = :yPos
                WHERE   lla.legal_dong_code in (:legalDongCodes)
                AND     rna.road_name_code = :roadNameCode
                AND     rna.building_main_no = :buildingMainNo
                AND     rna.building_sub_no = :buildingSubNo
                AND     rna.is_underground = :isUnderground
                """;
        namedParameterJdbcTemplate.batchUpdate(
                sql,
                items.stream()
                        .map(item -> new MapSqlParameterSource()
                                .addValue("xPos", item.getXPos())
                                .addValue("yPos", item.getYPos())
                                .addValue("legalDongCodes", item.getLegalDongCodes())
                                .addValue("roadNameCode", item.getRoadNameCode())
                                .addValue("buildingMainNo", item.getBuildingMainNo())
                                .addValue("buildingSubNo", item.getBuildingSubNo())
                                .addValue("isUnderground", item.getIsUnderground())
                        ).toArray(SqlParameterSource[]::new)
        );
    }

    public void batchInsertUnmapped(List<AddressPositionMappingParameter> unmappedList, int batchSize) {
        String sql = """
                INSERT INTO unmapped
                (
                    legal_dong_codes,
                    road_name_code,
                    building_main_no,
                    building_sub_no,
                    is_underground,
                    x_pos,
                    y_pos
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        jdbcTemplate.batchUpdate(sql, unmappedList, batchSize, (ps, entity) -> {
            ps.setString(1, String.join(",", entity.getLegalDongCodes()));
            ps.setObject(2, entity.getRoadNameCode(), VARCHAR);
            ps.setObject(3, entity.getBuildingMainNo(), INTEGER);
            ps.setObject(4, entity.getBuildingSubNo(), INTEGER);
            ps.setObject(5, entity.getIsUnderground(), VARCHAR);
            ps.setObject(6, entity.getXPos(), DECIMAL);
            ps.setObject(7, entity.getYPos(), DECIMAL);
        });
    }

    /**
     * 도로명주소 주소위치정보를 배치 업데이트한다.
     *
     * @param items batch update 파라미터
     */
    public void updatePositionBatch(Collection<? extends RoadNameAddress> items, int batchSize) {
        String sql = """
                UPDATE  road_name_address
                SET     x_pos = ?,
                        y_pos = ?
                WHERE   manage_no = ?
                AND     road_name_code = ? 
                AND     is_underground = ?
                AND     building_main_no = ?
                AND     building_sub_no = ?
                """;

        jdbcTemplate.batchUpdate(sql, items, batchSize, (ps, entity) -> {
            int i = 1;
            ps.setObject(i++, entity.getXPos(), DECIMAL);
            ps.setObject(i++, entity.getYPos(), DECIMAL);

            ps.setObject(i++, entity.getManageNo(), VARCHAR);
            ps.setObject(i++, entity.getRoadNameCode(), VARCHAR);
            ps.setObject(i++, entity.getIsUnderground(), VARCHAR);
            ps.setObject(i++, entity.getBuildingMainNo(), INTEGER);
            ps.setObject(i++, entity.getBuildingSubNo(), INTEGER);
        });
    }

    /**
     * 도로명 주소를 batch update한다.
     *
     * @param items     배치 Update할 데이터
     * @param batchSize 배치 size
     */
    public void batchUpdate(Collection<? extends RoadNameAddress> items, int batchSize) {
        String sql = """
                UPDATE  road_name_address
                SET     legal_dong_code = ?,
                        sido_name = ?,
                        sigungu_name = ?,
                        umd_name = ?,
                        ri_name = ?,
                        is_mountain = ?,
                        land_lot_main_no = ?,
                        land_lot_sub_no = ?,
                        road_name = ?,
                        adm_dong_code = ?,
                        adm_dong_name = ?,
                        basic_district_no = ?,
                        before_road_name_address = ?,
                        effect_start_date = ?,
                        is_multi = ?,
                        update_reason_code = ?,
                        building_name = ?,
                        sigungu_building_name = ?,
                        remark = ?
                WHERE   manage_no = ?
                AND     road_name_code = ?
                AND     is_underground = ?
                AND     building_main_no = ?
                AND     building_sub_no = ?
                """;

        jdbcTemplate.batchUpdate(
                sql, items, batchSize, (ps, entity) -> {
                    ps.setObject(1, entity.getLegalDongCode(), VARCHAR);
                    ps.setObject(2, entity.getSidoName(), VARCHAR);
                    ps.setObject(3, entity.getSigunguName(), VARCHAR);
                    ps.setObject(4, entity.getUmdName(), VARCHAR);
                    ps.setObject(5, entity.getRiName(), VARCHAR);
                    ps.setObject(6, entity.getIsMountain(), VARCHAR);
                    ps.setObject(7, entity.getLandLotMainNo(), INTEGER);
                    ps.setObject(8, entity.getLandLotSubNo(), INTEGER);
                    ps.setObject(9, entity.getRoadNameCode(), VARCHAR);
                    ps.setObject(10, entity.getAdmDongCode(), VARCHAR);
                    ps.setObject(11, entity.getAdmDongName(), VARCHAR);
                    ps.setObject(12, entity.getBasicDistrictNo(), VARCHAR);
                    ps.setObject(13, entity.getBeforeRoadNameAddress(), VARCHAR);
                    ps.setObject(14, entity.getEffectStartDate(), VARCHAR);
                    ps.setObject(15, entity.getIsMulti(), VARCHAR);
                    ps.setObject(16, entity.getUpdateReasonCode(), VARCHAR);
                    ps.setObject(17, entity.getBuildingName(), VARCHAR);
                    ps.setObject(18, entity.getSigunguBuildingName(), VARCHAR);
                    ps.setObject(19, entity.getRemark(), VARCHAR);
                    ps.setObject(20, entity.getManageNo(), VARCHAR);
                    ps.setObject(21, entity.getRoadNameCode(), VARCHAR);
                    ps.setObject(22, entity.getIsUnderground(), VARCHAR);
                    ps.setObject(23, entity.getBuildingMainNo(), INTEGER);
                    ps.setObject(24, entity.getBuildingSubNo(), INTEGER);
                }
        );
    }

    /**
     * 도로명 주소를 batch delete한다.
     *
     * @param items     배치 delete할 데이터
     * @param batchSize 배치 size
     */
    public void batchDelete(Collection<? extends RoadNameAddress> items, int batchSize) {

        String sql = """
                DELETE
                FROM    road_name_address
                WHERE   manage_no = ?
                AND     road_name_code = ?
                AND     is_underground = ?
                AND     building_main_no = ?
                AND     building_sub_no = ?
                """;
        jdbcTemplate.batchUpdate(
                sql, items, batchSize, (ps, entity) -> {
                     ps.setObject(1, entity.getManageNo(), VARCHAR);
                     ps.setObject(2, entity.getRoadNameCode(), VARCHAR);
                     ps.setObject(3, entity.getIsUnderground(), VARCHAR);
                     ps.setObject(4, entity.getBuildingMainNo(), INTEGER);
                     ps.setObject(5, entity.getBuildingSubNo(), INTEGER);
                });
    }
}