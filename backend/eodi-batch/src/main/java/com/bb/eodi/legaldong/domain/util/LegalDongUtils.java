package com.bb.eodi.legaldong.domain.util;

import org.springframework.util.StringUtils;

/**
 * 법정동 관련 유틸 클래스
 */
public class LegalDongUtils {
    private static final int sidoCodeLength = 2;
    private static final int sigunguCodeLength = 3;
    private static final int dongCodeLength = 3;
    private static final int riCodeLength = 2;

    /**
     * 법정동코드로 부모 코드를 리턴한다.
     * 최상위 코드일 경우 null을 리턴한다.
     * @param legalDongCode 법정동코드
     * @return 부모 법정동 코드
     */
    public static String parseParentCode(String legalDongCode) {
        int totalLength = legalDongCode.length();
        int cursor = sidoCodeLength;

        String result = null;

        if ("0".repeat(sigunguCodeLength)
                .equals(legalDongCode.substring(cursor, cursor += sigunguCodeLength))) {
            // do nothing
        }
        else if ("0".repeat(dongCodeLength)
                .equals(legalDongCode.substring(cursor, cursor += dongCodeLength))) {
            cursor -= dongCodeLength + sigunguCodeLength;
            result = legalDongCode.substring(0, cursor) + "0".repeat(totalLength - cursor);
        }
        else if ("0".repeat(riCodeLength)
                .equals(legalDongCode.substring(cursor, cursor += riCodeLength))) {
            cursor -= riCodeLength + dongCodeLength;
            result = legalDongCode.substring(0, cursor) + "0".repeat(totalLength - cursor);
        }
        else {
            cursor -= riCodeLength;
            result = legalDongCode.substring(0, cursor) + "0".repeat(totalLength - cursor);
        }

        return result;
    }

    /**
     * 법정동코드에서 시도 코드를 파싱한다.
     * @param legalDongCode 법정동코드
     * @return 법정동 시도 코드
     */
    public static String parseSidoCode(String legalDongCode) {
        return legalDongCode.substring(0, sidoCodeLength);
    }

    /**
     * 법정동코드에서 시군구 코드를 파싱한다.
     * @param legalDongCode 법정동코드
     * @return 법정동 시군구 코드
     */
    public static String parseSigunguCode(String legalDongCode) {
        return legalDongCode.substring(sidoCodeLength, sidoCodeLength + sigunguCodeLength);
    }

    /**
     * 법정동코드에서 읍면동 코드를 파싱한다.
     * @param legalDongCode 법정동코드
     * @return 법정동 읍면동 코드
     */
    public static String parseDongCode(String legalDongCode) {
        return legalDongCode.substring(sidoCodeLength + sigunguCodeLength, sidoCodeLength + sigunguCodeLength + dongCodeLength);
    }

    /**
     * 법정동 시도명, 시군구명, 읍면동명, 리명을 조합해 법정동 명을 파싱한다.
     * @param sidoName 시도명
     * @param sigunguName 시군구명
     * @param dongName 읍면동명
     * @param riName 리명
     * @return 법정동명
     */
    public static String parseLegalDongName(String sidoName, String sigunguName, String dongName, String riName) {
        StringBuilder sb = new StringBuilder();
        sb.append(sidoName);

        if (!StringUtils.hasText(sigunguName)) {
            return sb.toString();
        }
        if ((sigunguName.endsWith("구") || sigunguName.endsWith("군"))
                && sigunguName.contains("시")) {
            int divideIndex = sigunguName.indexOf("시") + 1;
            String sigunguSiName = sigunguName.substring(0, divideIndex);
            String sigunguGuName = sigunguName.substring(divideIndex);

            sb.append(" ").append(sigunguSiName);
            sb.append(" ").append(sigunguGuName);
        }
        else {
            sb.append(" ").append(sigunguName);
        }

        if (!StringUtils.hasText(dongName)) {
            return sb.toString();
        }
        sb.append(" ").append(dongName);

        if (!StringUtils.hasText(riName)) {
            return sb.toString();
        }

        sb.append(" ").append(riName);
        return sb.toString();
    }
}
