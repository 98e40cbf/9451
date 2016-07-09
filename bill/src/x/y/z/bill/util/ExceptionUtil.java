package x.y.z.bill.util;

import org.springframework.dao.DuplicateKeyException;

import io.alpha.exception.util.ExceptionUtils;

public class ExceptionUtil {

    public static boolean isDuplicateKey(final Throwable e) {
        return ExceptionUtils.causedBy(e, DuplicateKeyException.class);
    }

}
