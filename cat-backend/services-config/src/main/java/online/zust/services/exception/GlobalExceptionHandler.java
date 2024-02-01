package online.zust.services.exception;

import online.zust.common.entity.ResultData;
import online.zust.common.exception.ServiceException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qcqcqc
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常处理
     *
     * @param e 异常
     * @return 错误信息
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultData<String> handleServiceException(ServiceException e) {
        return ResultData.error(500, e.getMessage());
    }

    /**
     * 全局异常处理
     *
     * @param e 异常
     * @return 错误信息
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultData<String> exceptionHandler(Exception e) {
        return ResultData.error(500, e.getMessage());
    }

    /**
     * 参数校验异常
     *
     * @param e 异常
     * @return 错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultData<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        String message = allErrors.stream().map(
                DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; ")
                );
        return ResultData.error(403, message);
    }

    /**
     * 参数校验异常
     * BeanPropertyBindingResult
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultData<String> handleBindException(org.springframework.validation.BindException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        String message = allErrors.stream().map(
                DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; ")
                );
        return ResultData.error(403, message);
    }
}
