package pers.under2hump.springboot.configure.annotation;

import org.springframework.context.annotation.Import;
import pers.under2hump.springboot.configure.EnableUnderLineToHumpAutoConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description  启用自动装配配
 * @author AmVilCresx
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(EnableUnderLineToHumpAutoConfiguration.class)
public @interface EnableUnderLineToHump {

}
