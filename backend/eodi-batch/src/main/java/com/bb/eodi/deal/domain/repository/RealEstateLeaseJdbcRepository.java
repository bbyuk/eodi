package com.bb.eodi.deal.domain.repository;

import com.bb.eodi.deal.domain.entity.RealEstateLease;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.sql.Types.*;

/**
 * 부동산 임대차 실거래가 데이터 repository jdbc 구현체
 */
@Repository
@RequiredArgsConstructor
public class RealEstateLeaseJdbcRepository implements RealEstateLeaseRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveAllChunk(List<? extends RealEstateLease> items) {


        String sql = """
                INSERT INTO real_estate_lease
                (
                    region_id,
                    land_lot_value,
                    land_lot_main_no,
                    land_lot_sub_no,
                    is_mountain,
                    legal_dong_name,
                    contract_date,
                    contract_start_month,
                    contract_end_month,
                    deposit,
                    monthly_rent,
                    previous_deposit,
                    previous_monthly_rent,
                    total_floor_area,
                    build_year,
                    net_leasable_area,
                    housing_type,
                    target_name,
                    floor,
                    use_rr_right,
                    created_at,
                    updated_at
                )
                VALUES
                (
                    ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?
                )
                """;
        jdbcTemplate.batchUpdate(
                sql,
                items,
                500,
                (ps, entity) -> {
                    ps.setObject(1, entity.getRegionId(), BIGINT);
                    ps.setObject(2, entity.getLandLotValue(), VARCHAR);
                    ps.setObject(3, entity.getLandLotMainNo(), INTEGER);
                    ps.setObject(4, entity.getLandLotSubNo(), INTEGER);
                    ps.setObject(5, entity.getIsMountain(), TINYINT);
                    ps.setObject(6, entity.getLegalDongName(), VARCHAR);
                    ps.setObject(7, entity.getContractDate(), DATE);
                    ps.setObject(8, entity.getContractStartMonth(), INTEGER);
                    ps.setObject(9, entity.getContractEndMonth(), INTEGER);
                    ps.setObject(10, entity.getDeposit(), INTEGER);
                    ps.setObject(11, entity.getMonthlyRent(), INTEGER);
                    ps.setObject(12, entity.getPreviousDeposit(), INTEGER);
                    ps.setObject(13, entity.getPreviousMonthlyRent(), INTEGER);
                    ps.setObject(14, entity.getTotalFloorArea(), DECIMAL);
                    ps.setObject(15, entity.getBuildYear(), INTEGER);
                    ps.setObject(16, entity.getNetLeasableArea(), DECIMAL);
                    ps.setObject(17, entity.getHousingType().code(), VARCHAR);
                    ps.setObject(18, entity.getTargetName(), VARCHAR);
                    ps.setObject(19, entity.getFloor(), INTEGER);
                    ps.setObject(20, entity.isUseRRRight(), TINYINT);
                    ps.setObject(21, entity.getCreatedAt(), TIMESTAMP);
                    ps.setObject(22, entity.getUpdatedAt(), TIMESTAMP);
                }
        );
    }

    @Override
    public void batchUpdatePosition(List<? extends RealEstateLease> items) {
        String sql = """
                UPDATE  real_estate_lease
                SET     x_pos = ?,
                        y_pos = ?
                WHERE   id = ?
                """;
        jdbcTemplate.batchUpdate(sql, items, 500, (ps, entity) -> {
            ps.setObject(1, entity.getXPos(), DECIMAL);
            ps.setObject(2, entity.getYPos(), DECIMAL);
            ps.setObject(3, entity.getId(), BIGINT);
        });
    }
}
