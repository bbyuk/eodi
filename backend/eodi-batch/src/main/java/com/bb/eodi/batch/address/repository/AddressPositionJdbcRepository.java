package com.bb.eodi.batch.address.repository;

import com.bb.eodi.batch.address.entity.AddressPosition;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.sql.Types.*;

/**
 * 주소위치정보 JdbcRepository 구현체
 */
@Repository
@RequiredArgsConstructor
public class AddressPositionJdbcRepository implements AddressPositionRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * jdbc 배치 update로 파라미터 entity를 전체 저장한다.
     * @param entities 저장할 entity 목록
     */
    @Override
    public void saveAll(List<? extends AddressPosition> entities) {

        String sql = """
                INSERT INTO address_position
                (
                    sigungu_code,
                    entrance_seq,
                    legal_dong_code,
                    sido_name,
                    sigungu_name,
                    umd_name,
                    road_name_code,
                    road_name,
                    is_underground,
                    building_main_no,
                    building_sub_no,
                    building_name,
                    zip_no,
                    building_type,
                    is_building_group,
                    x_pos,
                    y_pos
                )
                VALUES
                (
                    ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? 
                )
                """;

        jdbcTemplate.batchUpdate(
                sql,
                entities,
                500,
                (ps, entity) -> {
                    ps.setObject(1, entity.getSigunguCode(), VARCHAR);
                    ps.setObject(2, entity.getEntranceSeq(), VARCHAR);
                    ps.setObject(3, entity.getLegalDongCode(), VARCHAR);
                    ps.setObject(4, entity.getSidoName(), VARCHAR);
                    ps.setObject(5, entity.getSigunguName(), VARCHAR);
                    ps.setObject(6, entity.getUmdName(), VARCHAR);
                    ps.setObject(7, entity.getRoadNameCode(), VARCHAR);
                    ps.setObject(8, entity.getRoadName(), VARCHAR);
                    ps.setObject(9, entity.getIsUnderground(), VARCHAR);
                    ps.setObject(10, entity.getBuildingMainNo(), INTEGER);
                    ps.setObject(11, entity.getBuildingSubNo(), INTEGER);
                    ps.setObject(12, entity.getBuildingName(), VARCHAR);
                    ps.setObject(13, entity.getZipNo(), VARCHAR);
                    ps.setObject(14, entity.getBuildingType(), VARCHAR);
                    ps.setObject(15, entity.getIsBuildingGroup(), VARCHAR);
                    ps.setObject(16, entity.getXPos(), DECIMAL);
                    ps.setObject(17, entity.getYPos(), DECIMAL);
                }
        );
    }
}
