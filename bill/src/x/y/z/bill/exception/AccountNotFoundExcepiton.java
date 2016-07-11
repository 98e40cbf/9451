package x.y.z.bill.exception;

import io.alpha.exception.FastRuntimeException;

public class AccountNotFoundExcepiton extends FastRuntimeException {

    private static final long serialVersionUID = 3907801557868451170L;

    public AccountNotFoundExcepiton(final String msg) {
        super(msg);
    }

    public AccountNotFoundExcepiton(final String code, final String msg) {
        super(code, msg);
    }

    public AccountNotFoundExcepiton(final String msg, final Throwable cause) {
        super(msg, cause);
    }

    public AccountNotFoundExcepiton(final String code, final String msg, final Throwable cause) {
        super(code, msg, cause);
    }

}
