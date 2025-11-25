package com.bb.eodi.deal.domain.repository;

import com.bb.eodi.deal.domain.entity.RealEstateSell;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

import static java.sql.Types.*;

/**
 * 부동산 매매 실거래가 Repository JdbcTemplate 구현체
 */
@Repository
@RequiredArgsConstructor
public class RealEstateSellJdbcRepository implements RealEstateSellRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveAllChunk(List<? extends RealEstateSell> items) {

        String sql = """
                INSERT INTO real_estate_sell
                (
                    region_id,
                    land_lot_value,
                    land_lot_main_no,
                    land_lot_sub_no,
                    is_mountain,
                    legal_dong_name,
                    contract_date,
                    price,
                    trade_method_type,
                    cancel_date,
                    build_year,
                    net_leasable_area,
                    land_area,
                    total_floor_area,
                    buyer,
                    seller,
                    housing_type,
                    date_of_registration,
                    target_name,
                    building_dong,
                    floor,
                    is_land_lease,
                    created_at,
                    updated_at
                )
                VALUES
                (
                    ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?
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
                    ps.setObject(8, BIGINT);
                    ps.setObject(9, VARCHAR);
                    ps.setObject(10, DATE);
                    ps.setObject(11, INTEGER);
                    ps.setObject(12, DECIMAL);
                    ps.setObject(13, DECIMAL);
                    ps.setObject(14, DECIMAL);
                    ps.setObject(15, VARCHAR);
                    ps.setObject(16, VARCHAR);
                    ps.setObject(17, VARCHAR);
                    ps.setObject(18, DATE);
                    ps.setObject(19, VARCHAR);
                    ps.setObject(20, VARCHAR);
                    ps.setObject(21, INTEGER);
                    ps.setObject(22, TINYINT);
                    ps.setObject(23, TIMESTAMP);
                    ps.setObject(24, TIMESTAMP);
                }
        );
    }
}
