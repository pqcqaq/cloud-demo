package online.zust.common.exception;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author qcqcqc
 */
public class ServiceException extends RuntimeException implements Serializable {
    @Serial
    private static final long serialVersionUID = -4665864140234905876L;

    public ServiceException(String message) {
        super(message);
    }
}
