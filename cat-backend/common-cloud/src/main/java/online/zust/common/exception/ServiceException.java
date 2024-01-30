package online.zust.common.exception;

import java.io.Serial;

/**
 * @author qcqcqc
 */
public class ServiceException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -4665864140234905876L;

    public ServiceException(String message) {
        super(message);
    }
}
