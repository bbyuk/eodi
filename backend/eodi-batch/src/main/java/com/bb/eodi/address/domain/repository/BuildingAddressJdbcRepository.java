package com.bb.eodi.address.domain.repository;

import com.bb.eodi.address.domain.entity.BuildingAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.sql.Types.*;

/**
 * 건물주소 Entity JdbcRepository
 */
@Repository
@RequiredArgsConstructor
public class BuildingAddressJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public void saveAll(List<? extends BuildingAddress> entities) {
        String sql = """
                INSERT INTO building_address
                (
                    legal_dong_code,
                    sido_name,
                    sigungu_name,
                    legal_umd_name,
                    legal_ri_name,
                    is_mountain,
                    land_lot_main_no,
                    land_lot_sub_no,
                    road_name_code,
                    road_name,
                    is_underground,
                    building_main_no,
                    building_sub_no,
                    building_name,
                    building_name_detail,
                    building_manage_no,
                    umd_seq,
                    adm_dong_code,
                    adm_dong_name,
                    zip_no,
                    zip_no_seq,
                    change_reason_code,
                    announcement_date,
                    sigungu_building_name,
                    is_complex,
                    basic_district_no,
                    has_detail_address,
                    remark_1,
                    remark_2
                )
                VALUES
                (
                    ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?
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
                    ps.setObject(9, entity.getRoadNameCode(), VARCHAR);
                    ps.setObject(10, entity.getRoadName(), VARCHAR);
                    ps.setObject(11, entity.getIsUnderground(), VARCHAR);
                    ps.setObject(12, entity.getBuildingMainNo(), INTEGER);
                    ps.setObject(13, entity.getBuildingSubNo(), INTEGER);
                    ps.setObject(14, entity.getBuildingName(), VARCHAR);
                    ps.setObject(15, entity.getBuildingNameDetail(), VARCHAR);
                    ps.setObject(16, entity.getBuildingManageNo(), VARCHAR);
                    ps.setObject(17, entity.getUmdSeq(), VARCHAR);
                    ps.setObject(18, entity.getAdmDongCode(), VARCHAR);
                    ps.setObject(19, entity.getAdmDongName(), VARCHAR);
                    ps.setObject(20, entity.getZipNo(), VARCHAR);
                    ps.setObject(21, entity.getZipNoSeq(), VARCHAR);
                    ps.setObject(22, entity.getChangeReasonCode(), VARCHAR);
                    ps.setObject(23, entity.getAnnouncementDate(), VARCHAR);
                    ps.setObject(24, entity.getSigunguBuildingName(), VARCHAR);
                    ps.setObject(25, entity.getIsComplex(), VARCHAR);
                    ps.setObject(26, entity.getBasicDistrictNo(), VARCHAR);
                    ps.setObject(27, entity.getHasDetailAddress(), VARCHAR);
                    ps.setObject(28, entity.getRemark1(), VARCHAR);
                    ps.setObject(29, entity.getRemark2(), VARCHAR);
                }
        );
    }
}
