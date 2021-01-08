package pers.under2hump.springboot.configure.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pers.under2hump.springboot.configure.core.AvcHandlerMethodArgumentResolver;

import java.util.List;

/**
 * @description Web配置类
 * @author AmVilCresx
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class AvcWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AvcHandlerMethodArgumentResolver());
    }
}
