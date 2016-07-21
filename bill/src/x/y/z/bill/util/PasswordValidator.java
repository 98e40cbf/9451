package x.y.z.bill.util;

import org.apache.commons.lang3.ArrayUtils;

import io.alpha.util.StringUtils;

public final class PasswordValidator {

    private static final char[] UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final char[] LOWER = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final char[] NUMBER = "0123456789".toCharArray();
    private static final char[] CHARACTER = "~`!@#$%^&*()_-+={}[]|;:,<>.?/".toCharArray();

    private PasswordValidator() {
    }

    public static boolean validate(final String input) {
        int length;
        if (StringUtils.isBlank(input) || (length = input.length()) < 8 || length > 20) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);
            if (!ArrayUtils.contains(UPPER, c) && !ArrayUtils.contains(LOWER, c) && !ArrayUtils.contains(NUMBER, c)
                    && !ArrayUtils.contains(CHARACTER, c)) {
                return false;
            }
        }
        int upper = contains(UPPER, input);
        int lower = contains(LOWER, input);
        int number = contains(NUMBER, input);
        int character = contains(CHARACTER, input);
        return (upper + lower + number + character) > 1;
    }

    private static int contains(final char[] chars, final String input) {
        for (int i = 0, len = input.length(); i < len; i++) {
            if (ArrayUtils.contains(chars, input.charAt(i))) {
                return 1;
            }
        }
        return 0;
    }

}
