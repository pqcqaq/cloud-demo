package online.zust.services.chainservice.utils;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Objects;
import java.util.Properties;

/**
 * @author qcqcqc
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {

    /**
     * @param name            名称
     * @param encodedResource 资源
     * @return PropertySource
     * @throws IOException 异常
     */
    @Override
    public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource encodedResource) throws IOException {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        Resource resource = encodedResource.getResource();
        URL url = resource.getURL();
        String path = URLDecoder.decode(url.getPath(), "utf-8");
        String s = FileUtils.getRealPathByPath(path);
        PathResource pathResource = new PathResource(s);
        factory.setResources(pathResource);
        Properties properties = factory.getObject();

        if (properties != null) {
            return new PropertiesPropertySource(Objects.requireNonNull(pathResource.getFilename()), properties);
        }
        throw new IllegalArgumentException("Resource " + pathResource.getFilename() + " not found");
    }
}

