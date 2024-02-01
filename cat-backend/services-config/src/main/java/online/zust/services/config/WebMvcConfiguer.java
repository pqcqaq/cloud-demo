package online.zust.services.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import online.zust.services.interceptor.AuthInterceptor;
import online.zust.services.serializer.LocalDateTimeSerializer;
import online.zust.services.serializer.LocalDatetimeDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author qcqcqc
 */
@Configuration
public class WebMvcConfiguer implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfiguer.class);

    @Bean
    public AuthInterceptor getAuthInterceptor() {
        return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("add interceptors");
        registry.addInterceptor(getAuthInterceptor()).addPathPatterns("/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //Jackson的全局序列化方式
        configureJacksonHttpMessageConverter(converters);
    }

    /**
     * Jackson的全局序列化方式
     *
     * @param converters 消息转换器列表
     */
    private void configureJacksonHttpMessageConverter(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        //序列换成json时,将所有的long变成string.因为js中得数字类型不能包含所有的java long值，超过16位后会出现精度丢失
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        // local-datetime序列化
        simpleModule.addSerializer(LocalDateTime.class, LocalDateTimeSerializer.getInstance());
        // local-datetime序列化
        simpleModule.addDeserializer(LocalDateTime.class, LocalDatetimeDeserializer.getInstance());
        objectMapper.registerModule(simpleModule);
        //反序列化的时候如果多了其他属性,不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        converter.setObjectMapper(objectMapper);
        converters.add(converter);
        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }
}

