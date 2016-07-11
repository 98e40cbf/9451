package x.y.z.bill.exception;

import io.alpha.exception.FastRuntimeException;

public class UserExistsException extends FastRuntimeException {

    private static final long serialVersionUID = -5474350616761580603L;

    public UserExistsException(final String msg) {
        super(msg);
    }

}
