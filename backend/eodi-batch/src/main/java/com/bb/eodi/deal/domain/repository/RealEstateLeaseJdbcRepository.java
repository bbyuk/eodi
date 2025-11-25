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
                    ps.setObject(1, BIGINT);
                    ps.setObject(2, VARCHAR);
                    ps.setObject(3, INTEGER);
                    ps.setObject(4, INTEGER);
                    ps.setObject(5, TINYINT);
                    ps.setObject(6, VARCHAR);
                    ps.setObject(7, DATE);
                    ps.setObject(8, INTEGER);
                    ps.setObject(9, INTEGER);
                    ps.setObject(10, INTEGER);
                    ps.setObject(11, INTEGER);
                    ps.setObject(12, INTEGER);
                    ps.setObject(13, INTEGER);
                    ps.setObject(14, DECIMAL);
                    ps.setObject(15, INTEGER);
                    ps.setObject(16, DECIMAL);
                    ps.setObject(17, VARCHAR);
                    ps.setObject(18, VARCHAR);
                    ps.setObject(19, INTEGER);
                    ps.setObject(20, TINYINT);
                    ps.setObject(21, TIMESTAMP);
                    ps.setObject(22, TIMESTAMP);
                }
        );
    }
}
