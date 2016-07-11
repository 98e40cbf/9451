package x.y.z.bill.exception;

import io.alpha.exception.FastRuntimeException;

public class BalanceNotEnoughException extends FastRuntimeException {

    private static final long serialVersionUID = -1108058653941132347L;

    public BalanceNotEnoughException(final String msg) {
        super(msg);
    }

    public BalanceNotEnoughException(final String code, final String msg) {
        super(code, msg);
    }

    public BalanceNotEnoughException(final String msg, final Throwable cause) {
        super(msg, cause);
    }

    public BalanceNotEnoughException(final String code, final String msg, final Throwable cause) {
        super(code, msg, cause);
    }

}
