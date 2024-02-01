package online.zust.services.config;

import online.zust.services.utils.SpringContextUtil;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

/**
 * @author qcqcqc
 */
@Configuration
public class ContextRefreshListener implements ApplicationListener<EnvironmentChangeEvent> {

    @Override
    public void onApplicationEvent(EnvironmentChangeEvent event) {
        Object source = event.getSource();
        if (source instanceof ApplicationContext) {
            SpringContextUtil.setApplicationContextStatic((ApplicationContext) source);
        }
    }

}
