package com.bb.eodi.deal.job.reader;

import com.bb.eodi.common.type.TypeCode;
import com.bb.eodi.deal.domain.entity.RealEstateSell;
import com.bb.eodi.deal.domain.type.HousingType;
import com.bb.eodi.deal.domain.type.TradeMethodType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

/**
 * 월별 부동산 실거래가 데이터 좌표 매핑 ItemReader
 */
@Slf4j
@StepScope
@Component
public class MonthlyRealEstateSellDataPositionMappingItemReader extends JdbcCursorItemReader<RealEstateSell> {

    private static final String sql = """
            SELECT  /* MonthlyRealEstateSellDataPositionMappingItemReader.selectRealEstateSellByMonth */
                    id                      AS  id,
                    region_id               AS  regionId,
                    land_lot_value          AS  landLotValue,
                    land_lot_main_no        AS  landLotMainNo,
                    land_lot_sub_no         AS  landLotSubNo,
                    is_mountain             AS  isMountain,
                    legal_dong_name         AS  legalDongName,
                    contract_date           AS  contractDate,
                    price                   AS  price,
                    trade_method_type       AS  tradeMethodType,
                    cancel_date             AS  cancelDate,
                    build_year              AS  buildYear,
                    net_leasable_area       AS  netLeasableArea,
                    land_area               AS  landArea,
                    total_floor_area        AS  totalFloorArea,
                    buyer                   AS  buyer,
                    seller                  AS  seller,
                    housing_type            AS  housingType,
                    date_of_registration    AS  dateOfRegistration,
                    target_name             AS  targetName,
                    building_dong           AS  buildingDong,
                    floor                   AS  floor,
                    is_land_lease           AS  isLandLease,
                    x_pos                   AS  xPos,
                    y_pos                   AS  yPos,
                    created_at              AS  createdAt,
                    updated_at              AS  updatedAt
            FROM    real_estate_sell res
            WHERE   1=1
            AND     res.contract_date >= ?
            AND     res.contract_date <= ?
            """;

    /**
     * 부동산 매매 실거래가 Jdbc RowMapper
     */
    private static class RealEstateSellRowMapper implements RowMapper<RealEstateSell> {
        @Override
        public RealEstateSell mapRow(ResultSet rs, int rowNum) throws SQLException {
            return RealEstateSell
                    .builder()
                    .id(rs.getLong("id"))
                    .regionId(rs.getLong("regionId"))
                    .landLotValue(rs.getString("landLotValue"))
                    .landLotMainNo(rs.getInt("landLotMainNo"))
                    .landLotSubNo(rs.getInt("landLotSubNo"))
                    .isMountain(rs.getBoolean("isMountain"))
                    .legalDongName(rs.getString("legalDongName"))
                    .contractDate(rs.getDate("contractDate").toLocalDate())
                    .price(rs.getLong("price"))
                    .tradeMethodType(TypeCode.from(TradeMethodType.class, rs.getString("tradeMethodType")))
                    .cancelDate(rs.getDate("cancelDate") != null ? rs.getDate("cancelDate").toLocalDate() : null)
                    .buildYear(rs.getInt("buildYear"))
                    .netLeasableArea(rs.getBigDecimal("netLeasableArea"))
                    .landArea(rs.getBigDecimal("landArea"))
                    .totalFloorArea(rs.getBigDecimal("totalFloorArea"))
                    .buyer(rs.getString("buyer"))
                    .seller(rs.getString("seller"))
                    .housingType(TypeCode.from(HousingType.class, rs.getString("housingType")))
                    .dateOfRegistration(rs.getDate("dateOfRegistration") != null ? rs.getDate("dateOfRegistration").toLocalDate() : null)
                    .targetName(rs.getString("targetName"))
                    .buildingDong(rs.getString("buildingDong"))
                    .floor(rs.getInt("floor"))
                    .isLandLease(rs.getBoolean("isLandLease"))
                    .xPos(rs.getBigDecimal("xPos"))
                    .yPos(rs.getBigDecimal("yPos"))
                    .createdAt(rs.getTimestamp("createdAt").toLocalDateTime())
                    .updatedAt(rs.getTimestamp("updatedAt").toLocalDateTime())
                    .build();
        }
    }

    public MonthlyRealEstateSellDataPositionMappingItemReader(
            @Value("#{stepExecutionContext['start-date']}") String startDate,
            @Value("#{stepExecutionContext['end-date']}") String endDate,
            DataSource dataSource
    ) {
        LocalDate firstDay = LocalDate.parse(startDate);
        LocalDate lastDay = LocalDate.parse(endDate);

        setDataSource(dataSource);
        setSql(sql);
        setPreparedStatementSetter(ps -> {
            ps.setObject(1, firstDay, Types.DATE);
            ps.setObject(2, lastDay, Types.DATE);
        });
        setRowMapper(new RealEstateSellRowMapper());
        setVerifyCursorPosition(false);
    }
}
