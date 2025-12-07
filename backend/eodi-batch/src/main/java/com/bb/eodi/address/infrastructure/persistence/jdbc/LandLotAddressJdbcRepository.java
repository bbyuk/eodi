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
    public void insertBatch(List<? extends LandLotAddress> entities) {

        String sql = """
                INSERT INTO land_lot_address
                (
                    manage_no,
                    seq,
                    legal_dong_code,
                    sido_name,
                    sigungu_name,
                    legal_umd_name,
                    legal_ri_name,
                    is_mountain,
                    land_lot_main_no,
                    land_lot_sub_no,
                    is_representative
                )
                VALUES
                (
                    ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?
                )
                """;

        jdbcTemplate.batchUpdate(
                sql,
                entities,
                500,
                (ps, entity) -> {
                    ps.setObject(1, entity.getManageNo(), VARCHAR);
                    ps.setObject(2, entity.getSeq(), INTEGER);
                    ps.setObject(3, entity.getLegalDongCode(), VARCHAR);
                    ps.setObject(4, entity.getSidoName(), VARCHAR);
                    ps.setObject(5, entity.getSigunguName(), VARCHAR);
                    ps.setObject(6, entity.getLegalUmdName(), VARCHAR);
                    ps.setObject(7, entity.getLegalRiName(), VARCHAR);
                    ps.setObject(8, entity.getIsMountain(), VARCHAR);
                    ps.setObject(9, entity.getLandLotMainNo(), INTEGER);
                    ps.setObject(10, entity.getLandLotSubNo(), INTEGER);
                    ps.setObject(11, entity.getIsRepresentative(), VARCHAR);
                }
        );
    }

    /**
     * 부가정보에 해당하는 컬럼들을 batch update한다.
     * @param items 업데이트 대상 items
     */
    public void batchUpdateAdditionalInfo(List<? extends LandLotAddress> items) {
        String sql = """
                UPDATE  land_lot_address
                SET     building_name = ?
                WHERE   id = ?
                """;
        jdbcTemplate.batchUpdate(sql, items, 500, (ps, entity) -> {
            ps.setObject(1, entity.getBuildingName(), VARCHAR);
            ps.setObject(2, entity.getId(), BIGINT);
        });
    }
}
