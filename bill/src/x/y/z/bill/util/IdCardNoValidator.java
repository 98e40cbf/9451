package x.y.z.bill.util;

import java.util.regex.Pattern;

import org.apache.commons.lang3.CharUtils;

import io.alpha.util.StringUtils;

public final class IdCardNoValidator {

    private IdCardNoValidator() {
    }

    private static final int POWER[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

    public static boolean validate(final String idCardNo) {
        if (isIdCardNo(idCardNo)) {
            return validate0(idCardNo);
        }
        return false;
    }

    private static final Pattern P = Pattern.compile("^\\d{17}(?:\\d|x|X)$");

    private static boolean isIdCardNo(final String idCardNo) {
        if (StringUtils.isBlank(idCardNo) || idCardNo.length() != 18) {
            return false;
        }
        return P.matcher(idCardNo).matches();
    }

    private static boolean validate0(final String idCardNo) {
        char front17Chars[] = idCardNo.substring(0, 17).toCharArray();
        if (!isDigital(front17Chars)) {
            return false;
        }
        String checkCode = calculateCheckCode(powerSum(converCharToInt(front17Chars)));
        String lastChar = idCardNo.substring(17, 18);
        if (!lastChar.equalsIgnoreCase(checkCode)) {
            return false;
        }
        return true;
    }

    private static boolean isDigital(final char[] chars) {
        for (char c : chars) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    private static int powerSum(final int[] bit) {
        int sum = 0;
        if (POWER.length != bit.length) {
            return sum;
        }
        for (int i = 0; i < bit.length; i++) {
            sum = sum + bit[i] * POWER[i];
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
        int[] arr = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            arr[i] = CharUtils.toIntValue(chars[i]);
        }
        return arr;
    }
}
