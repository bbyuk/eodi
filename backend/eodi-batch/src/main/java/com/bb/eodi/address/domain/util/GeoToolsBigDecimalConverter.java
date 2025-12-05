package com.bb.eodi.address.domain.util;

import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.api.referencing.operation.MathTransform;
import org.geotools.api.referencing.operation.TransformException;
import org.geotools.referencing.CRS;


import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * 좌표계 변환
 * TM5179 -> WGS84 변환
 */
public class GeoToolsBigDecimalConverter {

    private static final MathContext MC = new MathContext(15, RoundingMode.HALF_UP);

    private static final CoordinateReferenceSystem TM_KOREA;
    private static final CoordinateReferenceSystem WGS84;
    private static final MathTransform TM_TO_WGS;

    static {
        try {
            TM_KOREA = CRS.decode("EPSG:5179", true);
            WGS84 = CRS.decode("EPSG:4326", true);
            TM_TO_WGS = CRS.findMathTransform(TM_KOREA, WGS84, true);
        } catch (Exception e) {
            throw new RuntimeException("좌표계 초기화 실패", e);
        }
    }

    public static BigDecimal[] toWgs84(BigDecimal x, BigDecimal y) {
        try {
            if (x == null || y == null) {
                System.out.println("x = " + x);
            }
            double[] src = new double[] { x.doubleValue(), y.doubleValue() };
            double[] dst = new double[2];

            TM_TO_WGS.transform(src, 0, dst, 0, 1);

            BigDecimal lat = new BigDecimal(dst[1], MC); // 위도
            BigDecimal lon = new BigDecimal(dst[0], MC); // 경도

            return new BigDecimal[] { lat, lon };

        } catch (TransformException e) {
            throw new RuntimeException("좌표 변환 실패", e);
        }
    }
}
