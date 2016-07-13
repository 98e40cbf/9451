package x.y.z.bill.util;

import io.alpha.exception.util.ExceptionUtils;

import org.springframework.dao.DuplicateKeyException;

public class ExceptionUtil {

    public static boolean isDuplicateKey(final Throwable e) {
        return ExceptionUtils.causedBy(e, DuplicateKeyException.class);
    }

}
