package com.bb.eodi.deal.job.reader;

import com.bb.eodi.common.type.TypeCode;
import com.bb.eodi.deal.domain.entity.RealEstateLease;
import com.bb.eodi.deal.domain.type.HousingType;
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
 * 월별 부동산 임대차 실거래가 데이터 좌표 매핑 ItemReader
 */
@Slf4j
@StepScope
@Component
public class MonthlyRealEstateLeaseDataPositionMappingItemReader extends JdbcCursorItemReader<RealEstateLease> {

    private static final String sql = """
            SELECT  /* MonthlyRealEstateLeaseDataPositionMappingItemReader.selectRealEstateLeaseByMonth */
                id                      AS  id,
                region_id               AS  regionId,
                land_lot_value          AS  landLotValue,
                land_lot_main_no        AS  landLotMainNo,
                land_lot_sub_no         AS  landLotSubNo,
                is_mountain             AS  isMountain,
                legal_dong_name         AS  legalDongName,
                contract_date           AS  contractDate,
                contract_start_month    AS  contractStartMonth,
                contract_end_month      AS  contractEndMonth,
                deposit                 AS  deposit,
                monthly_rent            AS  monthlyRent,
                previous_deposit        AS  previousDeposit,
                previous_monthly_rent   AS  previousMonthlyRent,
                total_floor_area        AS  totalFloorArea,
                build_year              AS  buildYear,
                net_leasable_area       AS  netLeasableArea,
                housing_type            AS  housingType,
                target_name             AS  targetName,
                floor                   AS  floor,
                use_rr_right            AS  useRRRight,
                x_pos                   AS  xPos,
                y_pos                   AS  yPos,
                created_at              AS  createdAt,
                updated_at              AS  updatedAt
            FROM    real_estate_lease rel
            WHERE   1=1
            AND     rel.contract_date >= ?
            AND     rel.contract_date <= ?
            """;

    /**
     * 부동산 임대차 실거래가 Jdbc RowMapper
     */
    private static class RealEstateLeaseRowMapper implements RowMapper<RealEstateLease> {
        @Override
        public RealEstateLease mapRow(ResultSet rs, int rowNum) throws SQLException {
            return RealEstateLease.builder()
                    .id(rs.getLong("id"))
                    .regionId(rs.getLong("regionId"))
                    .landLotValue(rs.getString("landLotValue"))
                    .landLotMainNo(rs.getInt("landLotMainNo"))
                    .landLotSubNo(rs.getInt("landLotSubNo"))
                    .isMountain(rs.getBoolean("isMountain"))
                    .legalDongName(rs.getString("legalDongName"))
                    .contractDate(rs.getDate("contractDate").toLocalDate())
                    .contractStartMonth(rs.getInt("contractStartMonth"))
                    .contractEndMonth(rs.getInt("contractEndMonth"))
                    .deposit(rs.getInt("deposit"))
                    .monthlyRent(rs.getInt("monthlyRent"))
                    .previousDeposit(rs.getInt("previousDeposit"))
                    .previousMonthlyRent(rs.getInt("previousMonthlyRent"))
                    .totalFloorArea(rs.getBigDecimal("totalFloorArea"))
                    .buildYear(rs.getInt("buildYear"))
                    .netLeasableArea(rs.getBigDecimal("netLeasableArea"))
                    .housingType(TypeCode.from(HousingType.class, rs.getString("housingType")))
                    .targetName(rs.getString("targetName"))
                    .floor(rs.getInt("floor"))
                    .useRRRight(rs.getBoolean("useRRRight"))
                    .xPos(rs.getBigDecimal("xPos"))
                    .yPos(rs.getBigDecimal("yPos"))
                    .createdAt(rs.getTimestamp("createdAt").toLocalDateTime())
                    .updatedAt(rs.getTimestamp("updatedAt").toLocalDateTime())
                    .build();
        }
    }

    public MonthlyRealEstateLeaseDataPositionMappingItemReader(
            @Value("#{jobParameters['year-month']}") String yearMonth,
            DataSource dataSource
    ) {
        int year = Integer.parseInt(yearMonth.substring(0, 4));
        int month = Integer.parseInt(yearMonth.substring(4, 6));

        LocalDate firstDay = LocalDate.of(year, month, 1);
        LocalDate lastDay = firstDay.with(TemporalAdjusters.lastDayOfMonth());

        setDataSource(dataSource);
        setSql(sql);
        setPreparedStatementSetter(ps -> {
            ps.setObject(1, firstDay, Types.DATE);
            ps.setObject(2, lastDay, Types.DATE);
        });
        setRowMapper(new RealEstateLeaseRowMapper());
        setVerifyCursorPosition(false);
    }
}
