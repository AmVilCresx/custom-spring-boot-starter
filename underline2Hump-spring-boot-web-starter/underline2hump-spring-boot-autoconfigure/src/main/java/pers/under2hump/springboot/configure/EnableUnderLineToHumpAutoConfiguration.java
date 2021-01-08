package pers.under2hump.springboot.configure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.under2hump.springboot.configure.core.ParameterFilter;

/**
 * @description 自动装配类
 * @author AmVilCresx
 */
@Configuration
public class EnableUnderLineToHumpAutoConfiguration {

    @Bean
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    public ParameterFilter parameterFilter(){
        return new ParameterFilter();
    }
}
