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
                    ps.setObject(1, entity.getRegionId(), BIGINT);
                    ps.setObject(2, entity.getLandLotValue(), VARCHAR);
                    ps.setObject(3, entity.getLandLotMainNo(), INTEGER);
                    ps.setObject(4, entity.getLandLotSubNo(), INTEGER);
                    ps.setObject(5, entity.getIsMountain(), TINYINT);
                    ps.setObject(6, entity.getLegalDongName(), VARCHAR);
                    ps.setObject(7, entity.getContractDate(), DATE);
                    ps.setObject(8, entity.getPrice(), BIGINT);
                    ps.setObject(9, entity.getTradeMethodType(), VARCHAR);
                    ps.setObject(10, entity.getCancelDate(), DATE);
                    ps.setObject(11, entity.getBuildYear(), INTEGER);
                    ps.setObject(12, entity.getNetLeasableArea(), DECIMAL);
                    ps.setObject(13, entity.getLandArea(), DECIMAL);
                    ps.setObject(14, entity.getTotalFloorArea(), DECIMAL);
                    ps.setObject(15, entity.getBuyer(), VARCHAR);
                    ps.setObject(16, entity.getSeller(), VARCHAR);
                    ps.setObject(17, entity.getHousingType().code(), VARCHAR);
                    ps.setObject(18, entity.getDateOfRegistration(), DATE);
                    ps.setObject(19, entity.getTargetName(), VARCHAR);
                    ps.setObject(20, entity.getBuildingDong(), VARCHAR);
                    ps.setObject(21, entity.getFloor(), INTEGER);
                    ps.setObject(22, entity.getIsLandLease(), TINYINT);
                    ps.setObject(23, entity.getCreatedAt(), TIMESTAMP);
                    ps.setObject(24, entity.getUpdatedAt(), TIMESTAMP);
                }
        );
    }
}
