package x.y.z.bill.util;

import java.util.regex.Pattern;

import org.apache.commons.lang3.CharUtils;

import io.alpha.util.StringUtils;

public final class IdCardNoValidator {

    private static final int POWER[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
    private static final Pattern P = Pattern
            .compile("^[1-9]\\d{5}(?:19|20)\\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|1[0-9]|2[0-9]|3[0-1])\\d{3}(?:\\d|x|X)$");

    private IdCardNoValidator() {
    }

    public static boolean validate(final String idCardNo) {
        if (isIdCardNo(idCardNo)) {
            return validate0(idCardNo);
        }
        return false;
    }

    private static boolean isIdCardNo(final String idCardNo) {
        if (StringUtils.isBlank(idCardNo) || idCardNo.length() != 18) {
            return false;
        }
        return P.matcher(idCardNo).matches();
    }

    private static boolean validate0(final String idCardNo) {
        return idCardNo.substring(17).equalsIgnoreCase(
                calculateCheckCode(powerSum(converCharToInt(idCardNo.substring(0, 17).toCharArray()))));
    }

    private static int powerSum(final int[] bits) {
        int sum = 0;
        if (POWER.length != bits.length) {
            return sum;
        }
        for (int i = 0; i < bits.length; i++) {
            sum = sum + bits[i] * POWER[i];
        }
        return sum;
    }

    private static String calculateCheckCode(final int number) {
        switch (number % 11) {
            case 10:
                return "2";
            case 9:
                return "3";
            case 8:
                return "4";
            case 7:
                return "5";
            case 6:
                return "6";
            case 5:
                return "7";
            case 4:
                return "8";
            case 3:
                return "9";
            case 2:
                return "x";
            case 1:
                return "0";
            case 0:
                return "1";
        }
        return null;
    }

    private static int[] converCharToInt(final char[] chars) throws NumberFormatException {
        int[] array = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            array[i] = CharUtils.toIntValue(chars[i]);
        }
        return array;
    }
}
