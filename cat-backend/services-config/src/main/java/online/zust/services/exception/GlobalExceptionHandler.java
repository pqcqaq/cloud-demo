package online.zust.services.exception;

import online.zust.common.entity.ResultData;
import online.zust.common.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author qcqcqc
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultData<String> handleServiceException(ServiceException e) {
        return ResultData.error(500, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultData<String> exceptionHandler(Exception e) {
        return ResultData.error(500, e.getMessage());
    }
}
