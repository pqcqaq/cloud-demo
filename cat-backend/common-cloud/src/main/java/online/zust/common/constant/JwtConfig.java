package online.zust.common.constant;

import lombok.Data;
import online.zust.common.utils.YamlPropertySourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author qcqcqc
 */
@Configuration
@Data
@PropertySource(value = "classpath:jwt-config.yml", encoding = "utf-8", factory = YamlPropertySourceFactory.class)
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expire}")
    private Long expire;
}
