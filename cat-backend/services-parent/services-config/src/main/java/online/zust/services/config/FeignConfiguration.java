package online.zust.services.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import online.zust.services.utils.RequestHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author qcqcqc
 */
@Component
public class FeignConfiguration implements RequestInterceptor {

    @Value("${spring.application.name}")
    private String serviceName;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String jwt = RequestHolder.getJwt();
        if (jwt != null) {
            requestTemplate.header("jwt", jwt);
        }
        requestTemplate.header("service-name", serviceName);
    }
}
