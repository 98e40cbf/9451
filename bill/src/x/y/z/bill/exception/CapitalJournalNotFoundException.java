package x.y.z.bill.exception;

import io.alpha.exception.FastRuntimeException;

public class CapitalJournalNotFoundException extends FastRuntimeException {

    private static final long serialVersionUID = -6097808167462773847L;

    public CapitalJournalNotFoundException(final String msg) {
        super(msg);
    }

    public CapitalJournalNotFoundException(final String code, final String msg) {
        super(code, msg);
    }

    public CapitalJournalNotFoundException(final String msg, final Throwable cause) {
        super(msg, cause);
    }

    public CapitalJournalNotFoundException(final String code, final String msg, final Throwable cause) {
        super(code, msg, cause);
    }

}
