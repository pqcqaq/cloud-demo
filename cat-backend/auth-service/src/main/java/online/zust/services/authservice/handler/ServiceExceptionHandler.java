package online.zust.services.authservice.handler;

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
public class ServiceExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResultData<String> serviceExceptionHandler(ServiceException e) {
        return ResultData.error(404, e.getMessage());
    }

}
