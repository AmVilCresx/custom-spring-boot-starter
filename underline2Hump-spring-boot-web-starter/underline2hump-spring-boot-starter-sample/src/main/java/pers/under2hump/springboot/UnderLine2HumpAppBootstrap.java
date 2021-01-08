package pers.under2hump.springboot;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import pers.under2hump.springboot.configure.annotation.EnableUnderLineToHump;
import pers.under2hump.springboot.configure.core.Underline2HumpConstant;

/**
 * @description 启动类
 * @author AmVilCresx
 */
@SpringBootApplication(scanBasePackages = {"pers", Underline2HumpConstant.SCAN_COMPONENT_PATH})
@EnableUnderLineToHump
public class UnderLine2HumpAppBootstrap {

    public static void main(String[] args) {
        SpringApplicationBuilder applicationBuilder = new SpringApplicationBuilder(UnderLine2HumpAppBootstrap.class);
        applicationBuilder.web(WebApplicationType.SERVLET)
                .run(args);
    }
}
