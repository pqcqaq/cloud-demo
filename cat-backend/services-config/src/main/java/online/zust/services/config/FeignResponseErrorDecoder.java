package online.zust.services.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import online.zust.common.entity.ResultData;
import online.zust.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author qcqcqc
 */
@Configuration
public class FeignResponseErrorDecoder extends ErrorDecoder.Default {

    @Autowired
    private ObjectMapper objectMapper;

    private final Logger log = LoggerFactory.getLogger(FeignResponseErrorDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException exception = FeignException.errorStatus(methodKey, response);
        log.error("FeignErrorDecoder: {}", exception.getMessage());
        AtomicReference<Exception> err = new AtomicReference<>();
        exception.responseBody().ifPresentOrElse((body) -> {
            try {
                Charset charset = StandardCharsets.UTF_8;
                ResultData resultData = objectMapper.readValue(charset.decode(body).toString(), ResultData.class);
                log.error("FeignErrorDecoder: {}", resultData);
                err.set(new ServiceException(resultData.getMsg()));
            } catch (Exception e) {
                err.set(new RuntimeException(e));
            }
        }, () -> {
            throw exception;
        });
        return err.get();
    }
}
